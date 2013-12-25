package dap;

import java.io.*;
import java.util.*;
import java.awt.Rectangle;
/**
 * A class to create new Patron objects, get their data, remove them from patronsMap, and update their values.
 * @author Ayush Mehra
 */
public class Patrons
{

    //unique number for each patron (can be an auto number or a user name type entry)
    //first name, last name, e-mail address, phone number, street address, city, state, and zip code

    private String patronNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    
    public static String originalPatronId;

    /**
        * Constructor for Patrons class which
        * initializes a Patron object with the required data.
        * @param patronNumber The patron's number.
        * @param firstName The patron's firstName
        * @param lastName The patron's last name.
        * @param email The patron's email address.
        * @param phoneNumber The patron's phone number.
        * @param address The patron's address.
        * @param city The patron's city.       *
        * @param state The patron's state.
        * @param zipCode The patron's zip code.
        */
    public Patrons(String patronNumber,String firstName,String lastName,String email,String phoneNumber,String address,String city,String state,String zipCode)
    {
        this.patronNumber = patronNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

   


    /**
     * 
     * @return The patron's patron number.
     */
    public String getPatronNumber()
    {
    	return patronNumber;
    }

    /**
     * 
     * @return The patron's first name.
     */
    public String getFirstName()
    {
    	return firstName;
    }

    /**
     * 
     * @return The patron's last name.
     */
    public String getLastName()
    {
    	return lastName;
    }

    /**
     * 
     * @return The patron's email address.
     */
    public String getEmail()
    {
    	return email;
    }

    /**
     * 
     * @return The patron's phone number.
     */
    public String getPhoneNumber()
    {
    	return phoneNumber;
    }

    /**
     * 
     * @return The patron's address(not including the city,state,or zipcode).
     */
    public String getAddress()
    {
    	return address;
    }

    /**
     * 
     * @return The patron's city.
     */
    public String getCity()
    {
    	return city;
    }

    /**
     * 
     * @return The patron's state.
     */
    public String getState()
    {
    	return state;
    }

    /**
     * 
     * @return The patron's zip code.
     */
    public String getZipCode()
    {
    	return zipCode;
    }

    /**
     * 
     * @return The patron's original id.
     */    
    public static String getOriginalPatronId()
    {
        return originalPatronId;
    }

    /**
     * 
     * Sets patron's original id.
     */    
    public static void setOriginalPatronId(String id)
    {
        originalPatronId = id;
    }
    
    
    
    /**
     * Refreshes DAP.patronsMap with a new or edited patron and
     * re-initializes patronTable with updated values
     * 
     */
    public static void savePatron()
    {
        //get all the data from the textFields on the patrons panel

        String patronNumber = DAP.patronNumberTextField.getText().toUpperCase();
        String firstName = DAP.firstNameTextField.getText();
        String lastName = DAP.lastNameTextField.getText();
        String email = DAP.emailTextField.getText();
        String phone = DAP.phoneTextField.getText();
        String address = DAP.addressTextField.getText();
        String city = DAP.cityTextField.getText();
        String state = DAP.stateTextField.getText();
        String zipCode = DAP.zipCodeTextField.getText();
        
        
        String oldPatronId = Patrons.getOriginalPatronId();
        
        int index = 0;
        boolean keyExist = false; 
        DAPMain.patronError = false;
        
        if (patronNumber.equals("") ||
            firstName.equals("")    ||
            lastName.equals("")     ||
            email.equals("")        ||
            phone.equals("")        ||
            address.equals("")      ||
            city.equals("")         ||
            state.equals("")        ||
            zipCode.equals("")  ) //blank
        {
            DAPMain.patronError = true;
            DAP.patronNumberTextField.setEditable(true);
            DAP.firstNameTextField.setEditable(true);
            DAP.lastNameTextField.setEditable(true);
            DAP.emailTextField.setEditable(true);
            DAP.phoneTextField.setEditable(true);
            DAP.addressTextField.setEditable(true);
            DAP.cityTextField.setEditable(true); 
            DAP.stateTextField.setEditable(true);
            DAP.zipCodeTextField.setEditable(true);
            
            DAP.patronNumberTextField.setText(patronNumber);
            DAP.firstNameTextField.setText(firstName);
            DAP.lastNameTextField.setText(lastName);
            DAP.emailTextField.setText(email);
            DAP.phoneTextField.setText(phone);
            DAP.addressTextField.setText(address);
            DAP.cityTextField.setText(city);
            DAP.stateTextField.setText(state);
            DAP.zipCodeTextField.setText(zipCode);
            
            DAP.patronsNewButton.setEnabled(false);
            DAP.patronsEditButton.setEnabled(false);
            DAP.patronsRemoveButton.setEnabled(false);
            DAP.patronsSaveButton.setEnabled(true);
            DAP.patronsCancelButton.setEnabled(true);                        
            
            DAP.patronsTable.setRowSelectionAllowed(false);
            
            DAP.patronNumberTextField.requestFocus();
            
            DAP.displayErrorMessage("All fields are required! Please enter data and submit again");                        
            
            return;
        
        }
        
        
        DAP.patronsTable.setRowSelectionAllowed(true);//allows selection in the patrons table if none of the fields are empty
        
        //checks if this is a new patron

        if(DAPMain.editOrNew.equals("new"))
        {
            
            
            if(DAPMain.patronsMap.containsKey(patronNumber))
            {
                DAPMain.patronError = true;
                
                DAP.patronNumberTextField.setEditable(true);
                DAP.firstNameTextField.setEditable(true);
                DAP.lastNameTextField.setEditable(true);
                DAP.emailTextField.setEditable(true);
                DAP.phoneTextField.setEditable(true);
                DAP.addressTextField.setEditable(true);
                DAP.cityTextField.setEditable(true); 
                DAP.stateTextField.setEditable(true);
                DAP.zipCodeTextField.setEditable(true);                       
                
                DAP.patronNumberTextField.setText(patronNumber);
                DAP.firstNameTextField.setText(firstName);
                DAP.lastNameTextField.setText(lastName);
                DAP.emailTextField.setText(email);
                DAP.phoneTextField.setText(phone);
                DAP.addressTextField.setText(address);
                DAP.cityTextField.setText(city);
                DAP.stateTextField.setText(state);
                DAP.zipCodeTextField.setText(zipCode);
                
                DAP.patronsNewButton.setEnabled(false);
                DAP.patronsEditButton.setEnabled(false);
                DAP.patronsRemoveButton.setEnabled(false);
                DAP.patronsSaveButton.setEnabled(true);
                DAP.patronsCancelButton.setEnabled(true);                        

                DAP.patronsTable.setRowSelectionAllowed(false);

                DAP.patronNumberTextField.requestFocus();

                DAP.displayErrorMessage("There is already a patron registered with this patron number! please enter a different patron number");
                keyExist = true;
               
            }else
            {
            DAPMain.patronsMap.put(patronNumber,new Patrons(patronNumber,firstName,lastName,email,phone,address,city,state,zipCode));
            index = DAPMain.patronsMap.size()-1;                
            }
        }
        
        //checks if a patron was edited
        else if(DAPMain.editOrNew.equals("edit"))
        {
            index = DAP.patronsTable.getSelectedRow();
            String key = DAPMain.patronsData[index][0];

            if(!key.equals(patronNumber))
            {

                if(DAPMain.patronsMap.containsKey(patronNumber))
                {
                    DAPMain.patronError = true;

                    DAP.patronNumberTextField.setEditable(true);
                    DAP.firstNameTextField.setEditable(true);
                    DAP.lastNameTextField.setEditable(true);
                    DAP.emailTextField.setEditable(true);
                    DAP.phoneTextField.setEditable(true);
                    DAP.addressTextField.setEditable(true);
                    DAP.cityTextField.setEditable(true); 
                    DAP.stateTextField.setEditable(true);
                    DAP.zipCodeTextField.setEditable(true);                       

                    DAP.patronNumberTextField.setText(patronNumber);
                    DAP.firstNameTextField.setText(firstName);
                    DAP.lastNameTextField.setText(lastName);
                    DAP.emailTextField.setText(email);
                    DAP.phoneTextField.setText(phone);
                    DAP.addressTextField.setText(address);
                    DAP.cityTextField.setText(city);
                    DAP.stateTextField.setText(state);
                    DAP.zipCodeTextField.setText(zipCode);

                    DAP.patronsNewButton.setEnabled(false);
                    DAP.patronsEditButton.setEnabled(false);
                    DAP.patronsRemoveButton.setEnabled(false);
                    DAP.patronsSaveButton.setEnabled(true);
                    DAP.patronsCancelButton.setEnabled(true);                        

                    DAP.patronsTable.setRowSelectionAllowed(false);

                    DAP.patronNumberTextField.requestFocus();

                    DAP.displayErrorMessage("There is already a patron registered with this patron number! please enter a different patron number");
                    keyExist = true;
                } 
                else if(Patrons.isPatronInUse(oldPatronId))
                {
                    DAPMain.patronError = true;

                    DAP.patronNumberTextField.setEditable(true);
                    DAP.firstNameTextField.setEditable(true);
                    DAP.lastNameTextField.setEditable(true);
                    DAP.emailTextField.setEditable(true);
                    DAP.phoneTextField.setEditable(true);
                    DAP.addressTextField.setEditable(true);
                    DAP.cityTextField.setEditable(true); 
                    DAP.stateTextField.setEditable(true);
                    DAP.zipCodeTextField.setEditable(true);  
                    
                    DAP.patronNumberTextField.setText(patronNumber);
                    DAP.firstNameTextField.setText(firstName);
                    DAP.lastNameTextField.setText(lastName);
                    DAP.emailTextField.setText(email);
                    DAP.phoneTextField.setText(phone);
                    DAP.addressTextField.setText(address);
                    DAP.cityTextField.setText(city);
                    DAP.stateTextField.setText(state);
                    DAP.zipCodeTextField.setText(zipCode);

                    DAP.patronsNewButton.setEnabled(false);
                    DAP.patronsEditButton.setEnabled(false);
                    DAP.patronsRemoveButton.setEnabled(false);
                    DAP.patronsSaveButton.setEnabled(true);
                    DAP.patronsCancelButton.setEnabled(true);                        

                    DAP.patronsTable.setRowSelectionAllowed(false);

                    DAP.patronNumberTextField.requestFocus();

                    DAP.displayErrorMessage("This patron has items checked out. \nYou cannot change the Patron ID for this patron.");
                    DAP.patronNumberTextField.setText(oldPatronId);
                    DAP.patronNumberTextField.requestFocus();
                    keyExist = true;
                }
                else
                { 
                    //Patrons tempPatron = DAPMain.patronsMap.get(key);
                    //DAPMain.patronsMap.put(patronNumber,tempPatron);
                    DAPMain.patronsMap.remove(key);
                }

            }
            
            if (!DAPMain.patronError)
            {    
                DAPMain.patronsMap.put(patronNumber,new Patrons(patronNumber,firstName,lastName,email,phone,address,city,state,zipCode));
            }
           
        }
        // Re-initialize Types Data Array
        
        if(keyExist)
        {
         index = 0;
        }else    
        {    

            DataInit.hashmapToArrayPatrons();

            DataInit.write("patrons.txt", "patrons","");
            
            
            //loop through the patrons array to find the correct index of the added/edited patron
            index = 0;        
            for(int i=0; i<DAPMain.patronsData.length; i++)
            {
                if (patronNumber.equals(DAPMain.patronsData[i][0]))
                    {
                    index = i; 
                    }     
            }    
        
        }   


        if(!DAPMain.patronError)
        {
            DAP.patronSearchButton.setEnabled(true);
        
            DAP.patronsTable.setRowSelectionAllowed(true);
            DAP.patronsTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.patronsData, DAPMain.patronsHeader));
            DAP.patronsTable.setRowSelectionAllowed(true);
            DAP.patronsTable.setRowSelectionInterval(index,index);

            DAP.patronsTable.requestFocus();

            DAP.patronNumberTextField.setText(DAPMain.patronsData[index][0]);
            DAP.firstNameTextField.setText(DAPMain.patronsData[index][1]);
            DAP.lastNameTextField.setText(DAPMain.patronsData[index][2]);
            DAP.emailTextField.setText(DAPMain.patronsData[index][3]);
            DAP.phoneTextField.setText(DAPMain.patronsData[index][4]);
            DAP.addressTextField.setText(DAPMain.patronsData[index][5]);
            DAP.cityTextField.setText(DAPMain.patronsData[index][6]);   
            DAP.stateTextField.setText(DAPMain.patronsData[index][7]);
            DAP.zipCodeTextField.setText(DAPMain.patronsData[index][8]);

            DAP.patronsAllPatronsButton.setVisible(false);

            DAP.patronsTable.scrollRectToVisible(
                new Rectangle(0, index * DAP.patronsTable.getRowHeight(),DAP.patronsTable.getWidth(), DAP.patronsTable.getRowHeight()));

            writePatron();
        }
        
    }
    
    /**
     * Removes the patron from patronsMap 
     * and the patronTable
     * 
     */
    public static void removePatron()
    {
        int index = DAP.patronsTable.getSelectedRow();
        
        String removeKey = DAPMain.patronsData[index][0];
        
        DAPMain.patronsMap.remove(removeKey);
        
        DataInit.hashmapToArrayPatrons();
        
        DAP.patronsTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.patronsData, DAPMain.patronsHeader));
        
        DAP.patronsTable.setRowSelectionInterval(index, index);

        writePatron();
    }
    
    
    
    /**
     * Writes the patrons.txt file
     */
    public static void writePatron()
    {
        DataInit.write("patrons.txt","patrons","");
    }
    
    
    
    
    
    
    /**
     * Checks if the passed in patron has any items checked out.
     * @param patronNumber The patron number of the patron being checked for having items checked out
     * @return true if the passed in patron has checked out items, else returns false
     */
    public static boolean isPatronInUse(String patronNumber)
    {
        for(ItemsOut itemOut:DAPMain.itemsOutMap.values())
        {
            if(itemOut.getPatronNumber().equals(patronNumber))
            {
                return true;
            }
        }
        
        return false;
    }
    
    

}
