/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dap;


import java.util.ArrayList;
import java.util.Date;  
import java.util.Calendar;
import java.util.HashMap;
import java.awt.Color;


/**
 * A class to create new ItemsOut objects, get their data, check them out to a patron, and return them.
 * @author Ayush Mehra
 */
public class ItemsOut
{

    private String itemNumber;
    private String patronNumber;
    private String checkoutDate;
    private String dueDate;

    /**
    * Constructor for ItemsOut class which
    * initializes an ItemsOut object with the required data.
    * @param itemNumber The checked out item's item number.
    * @param patronNumber The patron number of the patron who checked out the item.
    * @param checkoutDate The date the item was checked out.
    * @param dueDate The date the item is due(2 weeks from today).
    */
    public ItemsOut(String itemNumber,String patronNumber,String checkoutDate,String dueDate)
    {
        this.itemNumber = itemNumber;
        this.patronNumber = patronNumber; 
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    /**
    * 
    * @return The checked out item's item number.
    */
    public String getItemNumber()
    {
        return itemNumber;
    }

    /**
     * 
     * @return The patron number of the patron who checked the item out.
     */
    public String getPatronNumber()
    {
    	return patronNumber;
    }

    /**
     * 
     * @return The date the item is due.
     */
    public String getDueDate()
    {
    	return dueDate;
    }
    
    /**
     * 
     * @return The date the item was checked out.
     */
    public String getCheckoutDate()
    {
        return checkoutDate;
    }                
    
    /**
     * Uses the Date class to get today's date
     * @return Today's date in the format mm/dd/yyyy.
     */
    public static String getTodayDate()
    {
        Date today = new Date(); 
               
        return (today.getMonth()+1)+"/"+today.getDate()+"/"+(1900+today.getYear());                
    }
    
    /**
     * Uses the Calendar class to add 2 weeks(14 days) to today's date
     * @return Returns the date two weeks from today in the format mm/dd/yyyy.
     */
    public static String getDateDue()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);//adds fourteen days(2 weeks)to today's date
        return (cal.get(Calendar.MONTH)+1+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));//returns the due date in a String format
    }


    
    
    /**
     * Checks out an item to a patron.
     * @param itemNumber The item number of the item that is being checked out.
     * @param patronNumber The patron number of the patron checking out the item.
     */
    public static void checkoutItem(String itemNumber,String patronNumber)
    {
        if(DAPMain.itemsOutMap.get(itemNumber) == null)
        {
            DAPMain.itemsOutMap.put(itemNumber,new ItemsOut(itemNumber,patronNumber,ItemsOut.getTodayDate(),ItemsOut.getDateDue()));
            //DataInit.write("itemsout.txt", "itemsout");
        
        }
        else
        {
            DAP.displayErrorMessage("This item is already checked out to a patron. "
                    + '\n'+ "This item must be returned before it can be checked out");
            
            DAP.checkoutCheckoutButton.setEnabled(false);
            
            return;
        }
        
        writeItemsOut();
        
    }
                
    
    
    /**
     * Returns all the items which are selected in checkoutTable.
     */
    public static void returnItem()
    {
        
        int[] indices = DAP.checkoutTable.getSelectedRows();

        String[] itemNumbers = new String[indices.length];

        for(int i = 0;i<indices.length;i++)
        {
            itemNumbers[i] = DAPMain.checkoutData[indices[i]][0];
        }

        for(int x = 0;x<itemNumbers.length;x++)
        {
            DAPMain.itemsOutMap.remove(itemNumbers[x]);
        }
        
        
        writeItemsOut();//writes the text file with the updated data
        
        
    }                    
    
    
    
    /**
     * Populates the checkoutTable with all the items checked out to the patron.
     */
    public static void checkoutTableInit()
    {
        ArrayList<String> checkedOutByPatron = new ArrayList<String>();
        
        Patrons checkoutPatron = DAPMain.patronsMap.get(DAP.checkoutPatronNumberTextField.getText()); //patron checking out the item
        
        boolean hasOverDueItem = false;
        
            
        String patronNumber = checkoutPatron.getPatronNumber(); //getting the patron's patronNumber

        for(ItemsOut item:DAPMain.itemsOutMap.values())//iterates through all the values of itemsOutMap
        {
            if(item.getPatronNumber().equals(patronNumber))//checks to see if the ItemOut object has the same patronNumber as the patron checking out the item now
            {
                checkedOutByPatron.add(item.getItemNumber()); //if true add it to the ArrayList checkedOutByPatron
            }
        }

        DAPMain.checkoutData = new String[checkedOutByPatron.size()][6]; //initializes DAPMain.checkoutData with size of the
                                                                    //size of the arraylist of all items checked out by this patron
        int x = -1; //a ticker for the for each loop below

        for(String itemNumber:checkedOutByPatron) //iterates through all the values in the arraylist checkedOutByPatron
        {
            x++; //increment ticker

            ItemsOut itemOut = DAPMain.itemsOutMap.get(itemNumber); //find the object of type ItemsOut by searching the hashmap with the key(itemNumber)

            DAPMain.checkoutData[x][0] = itemOut.getItemNumber();
            DAPMain.checkoutData[x][1] = DAPMain.libraryMap.get(itemNumber).getTitle();
            DAPMain.checkoutData[x][2] = DAPMain.libraryMap.get(itemNumber).getAuthor();
            DAPMain.checkoutData[x][3] = DAPMain.libraryMap.get(itemNumber).getItemType();
            DAPMain.checkoutData[x][4] = itemOut.getCheckoutDate();
            DAPMain.checkoutData[x][5] = itemOut.getDueDate();

            if(itemOut.isOverDue(itemOut.getDueDate()))
            {
                hasOverDueItem = true;
            }
        }

        DAP.checkoutTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.checkoutData, DAPMain.checkoutHeader));
        
        if(checkedOutByPatron.size()==0)
        {
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setForeground(Color.BLACK);
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setText("No Items Checked Out");
        }
        else if(hasOverDueItem)
        {
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setForeground(Color.RED);
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setText("Overdue Items!");
        }
        else
        {
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setForeground(Color.BLACK);
            DAP.checkoutItemsCurrentlyCheckedOutLabel.setText("");
        }
        
    }
    

    /**
     * Method to check if the checked out item is overdue by checking given due date against the current date
     * @param dateDue The date when the checked out item item is due back
     * @return The true if the current date is greater then passed due date
     */    
    public boolean isOverDue(String dateDue)
    {
        
        String[]currentDateStringArr = getTodayDate().split("/");
        String[]dueDateStringArr = dateDue.split("/");
        
        int[]currentDate = new int[3];
        int[]dueDate = new int[3];
        
        for(int x = 0;x<3;x++)
        {
            currentDate[x] = Integer.parseInt(currentDateStringArr[x]);
            dueDate[x] = Integer.parseInt(dueDateStringArr[x]);
        }
        
        if(currentDate[2]>dueDate[2])
            return true;
        else if(currentDate[2]<dueDate[2])
            return false;
        else
        {
            if(currentDate[0]>dueDate[0])
                return true;
            else if(currentDate[0]<dueDate[0])
                return false;
            else
            {
                if(currentDate[1]>dueDate[1])
                    return true;
                else if(currentDate[1]<dueDate[1])
                    return false;
            }
        }
        return false;
        
    }
    
    
    /**
     * Writes the itemsout.txt file.
     */
    public static void writeItemsOut()
    {
        DataInit.write("itemsout.txt", "itemsout",""); //re-writes itemsOut.txt with updated data
    }
    
    
    


}
