/**
 * @(#)Types.java
 *
 *
 * @author
 * @version 1.00 2011/12/17
 */

package dap;

import java.awt.Rectangle;
import java.io.*;
import java.util.*;

/**
 * A class to create new Types objects, get their data, remove them from typesMap, and update their values.
 * @author Ayush Mehra
 */
public class Types
{

    //unique type (of each item)
    //type, description

    private String type;
    private String description;

    /**
        * Constructor for Types class which
        * initializes an object of Type with 
        * an abbreviation and description
        * @param type The type's abbreviation.
        * @param description The type's description.
        */
    public Types(String type,String description)
    {
        this.type = type;
        this.description = description;
    }

    /**
        * 
        * @return type The type's abbreviation.
        */
    public String getType()
    {
        return type;
    }

    /**
     * 
     * @return The type's description.
     */
    public String getDescription()
    {
    	return description;
    }

    
    

    /**
     * Refreshes DAP.typesMap with a new or edited type and
     * re-initializes the typesTable with the updated data. 
     * 
     */
    public static void saveType()
    {
        //get all the data from the textFields on the types panel
        String type = DAP.typesTypeTextField.getText();
        String description = DAP.typesDescriptionTextField.getText();
        
        int index = 0;
        boolean keyExist = false;
        DAPMain.typeError = false;

        if (type.equals("")           ||
            description.equals(""))        // blank
        {            
            
            DAP.typesTypeTextField.setEditable(true);
            DAP.typesDescriptionTextField.setEditable(true);
            
            DAP.typesNewButton.setEnabled(false);
            DAP.typesEditButton.setEnabled(false);
            DAP.typesRemoveButton.setEnabled(false);
            DAP.typesSaveButton.setEnabled(true);
            DAP.typesCancelButton.setEnabled(true);
            
            DAP.typesTable.setRowSelectionAllowed(false);
            
            DAP.typesTypeTextField.requestFocus();
            
            DAP.displayErrorMessage("All fields are required! please enter data and submit again");
            
            return;
        }
        
        
        DAP.typesTable.setRowSelectionAllowed(true);
        
        //checks if this is a new type
       if(DAPMain.editOrNew.equals("new"))
        {
            if(DAPMain.typesMap.containsKey(type))
            {
                
                DAPMain.typeError = true;
                
                DAP.typesTypeTextField.setEditable(true);
                DAP.typesDescriptionTextField.setEditable(true);

                DAP.typesNewButton.setEnabled(false);
                DAP.typesEditButton.setEnabled(false);
                DAP.typesRemoveButton.setEnabled(false);
                DAP.typesSaveButton.setEnabled(true);
                DAP.typesCancelButton.setEnabled(true);                        

                DAP.typesTable.setRowSelectionAllowed(false);

                DAP.typesTypeTextField.requestFocus();
                
                DAP.displayErrorMessage("There is already a type registered with this type name! \nPlease enter a different type name.");
                keyExist = true;
            }else
            {
               DAPMain.typesMap.put(type,new Types(type,description));
               index = DAPMain.typesMap.size()-1;                
            }
        }
        
        //checks if a type was edited
        else if(DAPMain.editOrNew.equals("edit"))
        {
            index = DAP.typesTable.getSelectedRow();
            String key = DAPMain.typesData[index][0];

            if(!key.equals(type))
            {
                if(DAPMain.typesMap.containsKey(type))
                {

                    DAPMain.typeError = true;

                    DAP.typesTypeTextField.setEditable(true);
                    DAP.typesDescriptionTextField.setEditable(true);

                    DAP.typesNewButton.setEnabled(false);
                    DAP.typesEditButton.setEnabled(false);
                    DAP.typesRemoveButton.setEnabled(false);
                    DAP.typesSaveButton.setEnabled(true);
                    DAP.typesCancelButton.setEnabled(true);                        

                    DAP.typesTable.setRowSelectionAllowed(false);

                    DAP.typesTypeTextField.requestFocus();

                    DAP.displayErrorMessage("There is already a type registered with this type! \nPlease enter a different type number");
                    keyExist = true;
                }else
                {                
                    DAPMain.typesMap.remove(key);
                }
            }
                if (!DAPMain.typeError)
                {    
                    DAPMain.typesMap.put(type,new Types(type,description));
                }
            
        }
        
        // Re-initialize Types Data Array
        
        if(keyExist)
        {
         index = 0;
        }else    
        {    
            DataInit.hashmapToArrayTypes();
            DataInit.write("types.txt","types",""); //re-writes the type.txt file with the updated data

            //loop through the types array to find the correct index of the added/edited patron
            index = 0;        
            for(int i=0; i<DAPMain.typesData.length; i++)
            {
                if (type.equals(DAPMain.typesData[i][0]))
                    {
                    index = i; 
                    }     
            }    
        
        }   
        if (!DAPMain.typeError)
        {    

            DAP.typesTable.setRowSelectionAllowed(true);
            DAP.typesTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.typesData, DAPMain.typesHeader));   
            DAP.typesTable.setRowSelectionAllowed(true);
            DAP.typesTable.setRowSelectionInterval(index,index);
            DAP.typesTable.requestFocus();

            DAP.typesTypeTextField.setText(DAPMain.typesData[index][0]);
            DAP.typesDescriptionTextField.setText(DAPMain.typesData[index][1]);
        

            DAP.typesTable.scrollRectToVisible(
               new Rectangle(0, index * DAP.typesTable.getRowHeight(),DAP.typesTable.getWidth(), DAP.typesTable.getRowHeight()));
        
        
            writeType();
        }    
    }
    
    /**
     * Removes the type from the typesMap
     * and the typesTable
     */
    public static void removeType()
    {
        int index = DAP.typesTable.getSelectedRow();
        
        String removeKey = DAPMain.typesData[index][0];
        
        DAPMain.typesMap.remove(removeKey);
        
        DataInit.hashmapToArrayTypes();
        
        DAP.typesTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.typesData, DAPMain.typesHeader));
        
        DAP.typesTable.setRowSelectionInterval(index, index);

        writeType(); //re-writes the type.txt file with the updated data
        
        
    }
    
    /**
     * 
     * @param type The type to be checked if it is used by any items in the library
     * @return true if the passed in type is in use by an item, else returns false
     */
    public static boolean isTypeInUse(String type)
    {
        for(Library item:DAPMain.libraryMap.values())
        {
            if(item.getItemType().equals(type))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Writes the types.txt file
     */
    public static void writeType()
    {
        DataInit.write("types.txt","types",""); //re-writes the type.txt file with the updated data
    }


}