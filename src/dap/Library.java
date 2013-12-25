
package dap;



import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A class to create new Library objects, get their data, remove them from itemsMap, and update their values.
 * @author Ayush Mehra
 */
public class Library
{

    
    private String itemNumber;
    private String itemType;
    private String title;
    private String author;
    private String publishDate;


    /**
        * Constructor for Library class which
        * initializes a Library object with the required data.
        * @param itemNumber The item's itemNumber.
        * @param itemType The item's type.
        * @param title The item's name.
        * @param author The item's author/artist/publisher.
        * @param publishDate The item's publish date.
        */
    public Library(String itemNumber,String itemType,String title,String author,String publishDate)
    {
    	this.itemNumber = itemNumber;
    	this.itemType = itemType;
    	this.title = title;
    	this.author = author;
    	this.publishDate = publishDate;
        
    }
    
 

    /**
    * 
    * @return The item's item number.
    */
    public String getItemNumber()
    {
            return itemNumber;
    }

    /**
    * 
    * @return The item's type.
    */
    public String getItemType()
    {
            return itemType;
    }

    /**
    * 
    * @return The item's name.
    */
    public String getTitle()
    {
            return title;
    }

    /**
    * 
    * @return The item's author/artist/publisher.
    */
    public String getAuthor()
    {
            return author;
    }

    /**
    * 
    * @return The item's publish date.
    */
    public String getPublishDate()
    {
            return publishDate;
    }               

    


    /**
     * Refreshes DAP.itemsMap with a new or edited item
     * Reprints the JTable 
     * Writes items.txt with new info
     */
    public static void saveCatalog()
    {
        //get all the data from the textFields on the patrons panel
        String itemNumber = DAP.catalogItemNumberTextField.getText().toUpperCase();//item numbers are alwasy stored as upper case
        String type = DAP.catalogTypeTextField.getText().toUpperCase();//types are always stored as upper case
        String title = DAP.catalogItemTextField.getText();
        String author = DAP.catalogAuthorTextField.getText();
        String publishDate = DAP.catalogPublishedDateTextField.getText();
        
        int index = 0;
        
        boolean keyExist = false;
        DAPMain.catalogError     = false;    
        
        if (itemNumber.equals("")           || //checks if a required field is empty
            type.equals("")                 ||
            title.equals("")                ||
            author.equals("")               ||
            !DAPMain.typesMap.containsKey(type)    
           )        // blank 
        {
            DAPMain.catalogError = true;
            DAP.catalogNewButton.setEnabled(false);
            DAP.catalogEditButton.setEnabled(false);
            DAP.catalogRemoveButton.setEnabled(false);
            DAP.catalogSaveButton.setEnabled(true);
            DAP.catalogCancelButton.setEnabled(true);
            
            DAP.catalogItemNumberTextField.setEditable(true);            
            DAP.catalogItemTextField.setEditable(true);
            DAP.catalogAuthorTextField.setEditable(true);
            DAP.catalogPublishedDateTextField.setEditable(true);
            DAP.catalogTypeTextField.setEditable(true);   
            
            DAP.catalogTable.setRowSelectionAllowed(false);    
            
            if(!DAPMain.typesMap.containsKey(type))
            {
                DAP.displayErrorMessage("No such Type exists! \nPlease add this type to the types list before using it.");
                 DAP.catalogTypeTextField.requestFocus();
                return;
            }
            
            DAP.displayErrorMessage("Item#, Type, Item Name & Author fields are required! \nPlease enter data and submit again");
            
            DAP.catalogItemNumberTextField.requestFocus();
            
            
            return;
        }                
        
        
        DAP.catalogTable.setRowSelectionAllowed(true); //if there is no error, allows selection in the table
        
        //checks if this is a new item

        if(DAPMain.editOrNew.equals("new"))
        {
            DAP.catalogFullCatalogButton.setVisible(false);
            
            if(DAPMain.libraryMap.containsKey(itemNumber))
            {

                DAPMain.catalogError = true;
                DAP.catalogNewButton.setEnabled(false);
                DAP.catalogEditButton.setEnabled(false);
                DAP.catalogRemoveButton.setEnabled(false);
                DAP.catalogSaveButton.setEnabled(true);
                DAP.catalogCancelButton.setEnabled(true);

                DAP.catalogItemNumberTextField.setEditable(true);            
                DAP.catalogItemTextField.setEditable(true);
                DAP.catalogAuthorTextField.setEditable(true);
                DAP.catalogPublishedDateTextField.setEditable(true);
                DAP.catalogTypeTextField.setEditable(true);   

                DAP.catalogTable.setRowSelectionAllowed(false);                        
                
                
                DAP.displayErrorMessage("There is already an item registered with this item number! \n Please enter a different item number");
                keyExist = true;
                DAPMain.catalogError = true;                
                
                DAP.catalogItemNumberTextField.requestFocus();

            }else
            {
               DAPMain.libraryMap.put(itemNumber,new Library(itemNumber,type,title,author,publishDate));
               index = DAPMain.libraryMap.size()-1;                
            }
        }
        
        //checks if item was edited
        else if(DAPMain.editOrNew.equals("edit"))
        {
            index = DAP.catalogTable.getSelectedRow();
            String key = DAPMain.itemsData[index][0];

            
            if(!key.equals(itemNumber))
            {

              if(DAPMain.libraryMap.containsKey(itemNumber))
              {
                DAPMain.catalogError = true;
                DAP.catalogNewButton.setEnabled(false);
                DAP.catalogEditButton.setEnabled(false);
                DAP.catalogRemoveButton.setEnabled(false);
                DAP.catalogSaveButton.setEnabled(true);
                DAP.catalogCancelButton.setEnabled(true);

                DAP.catalogItemNumberTextField.setEditable(true);            
                DAP.catalogItemTextField.setEditable(true);
                DAP.catalogAuthorTextField.setEditable(true);
                DAP.catalogPublishedDateTextField.setEditable(true);
                DAP.catalogTypeTextField.setEditable(true);   

                DAP.catalogTable.setRowSelectionAllowed(false);                        
                
                
                DAP.displayErrorMessage("There is already an item registered with this item number! \n Please enter a different item number");
                keyExist = true;
                DAPMain.catalogError = true;                
                
                DAP.catalogItemNumberTextField.requestFocus();
                  
              }   
              else
              {    
                //Library tempItem = DAPMain.libraryMap.get(key);
                //DAPMain.libraryMap.put(itemNumber,tempItem);
                DAPMain.libraryMap.remove(key);
              }  
            }
            
            
            
            
            if (!DAPMain.catalogError)
            {               
                DAPMain.libraryMap.put(itemNumber,new Library(itemNumber,type,title,author,publishDate));
            }
            
        }
        
        // Re-initialize Items Data Array
        
        if(keyExist)
        {
         index = 0;
        }
        else    
        {    
            
        DataInit.hashmapToArrayLibrary();

            //loop through the items array to find the correct index of the added/edited item
            index = 0;        
            for(int i=0; i<DAPMain.itemsData.length; i++)
            {
                if (itemNumber.equals(DAPMain.itemsData[i][0]))
                    {
                    index = i; 
                    }     
            }    

        
        }   
        // do only if no error is found         

        if (!DAPMain.catalogError)
        {    
            writeLibrary();

            DAP.catalogSearchButton.setEnabled(true);

            DAP.catalogTable.setRowSelectionAllowed(true);
            DAP.catalogTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.itemsData, DAPMain.itemsHeader)); 
            DAP.catalogTable.setRowSelectionAllowed(true);
            DAP.catalogTable.setRowSelectionInterval(index,index);
            DAP.catalogTable.requestFocus();

            DAP.catalogItemNumberTextField.setText(DAPMain.itemsData[index][0]);
            DAP.catalogItemTextField.setText(DAPMain.itemsData[index][1]);
            DAP.catalogAuthorTextField.setText(DAPMain.itemsData[index][2]);
            DAP.catalogPublishedDateTextField.setText(DAPMain.itemsData[index][3]);
            DAP.catalogTypeTextField.setText(DAPMain.itemsData[index][4]);

            DAP.catalogFullCatalogButton.setVisible(false);



            //scrolls to the right location in the table to show the saved item
            DAP.catalogTable.scrollRectToVisible(
                new Rectangle(0, index * DAP.catalogTable.getRowHeight(),DAP.catalogTable.getWidth(), DAP.catalogTable.getRowHeight()));
        }
        
     }


    /**
    * Removes the item from itemsMap
    * and the catalogTable
    */
    public static void removeLibrary()
    {
        int index = DAP.catalogTable.getSelectedRow();

        String removeKey = DAPMain.itemsData[index][0];

        DAPMain.libraryMap.remove(removeKey);
        
        DataInit.hashmapToArrayLibrary();
        
        DAP.patronsAllPatronsButton.setVisible(false);
        
        DAP.catalogTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.itemsData, DAPMain.itemsHeader)); 
        
        writeLibrary();

    }

    
    /**
     * Method to see if the given item is checked out or not.
     * @param itemNumber The item's number.
     * @return true If the passed in item is checked out, else returns false
     */
    public static boolean isItemInUse(String itemNumber)
    {
        for(ItemsOut itemOut:DAPMain.itemsOutMap.values())
        {
            if(itemOut.getItemNumber().equals(itemNumber))
            {
                return true;
            }
        }
        
        return false;
    }


    /**
    * Writes the library.txt file
    */
    public static void writeLibrary()
    {
        DataInit.write("library.txt","library",""); //re-writes the library.txt with updated data
    }
    
    

}
