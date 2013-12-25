/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dap;

import java.util.ArrayList;

/**
 *A class for utilities like resizing the mainFrame and a common search method
 * @author Ayush Mehra
 */
public class DAPUtil 
{
    
    /**
     * Resizes the frame to fit the passed in screen
     * @param screenName The screen for the frame to be resized to
     */
    public static void setScreenSize(String screenName)
    {
        if(screenName.equals("home"))
        {
            DAP.mainSearchTextField.setText("Search Catalog...");
            DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),610,330); //sets size of the frame to fit the main panel        }
        }
        else if(screenName.equals("catalog"))
        {
            DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),840,590);//sets size of the frame to fit the catalog panel
        }
        else if(screenName.equals("patrons"))
        {
            DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),890,630);//sets size of the frame to fit the patrons panel
        }
        else if(screenName.equals("types"))
        {
            DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),700,540);//sets size of the frame to fit the types panel
        }
        else if(screenName.equals("reports"))
        {
            DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),920,590); //sets size of the frame to fit the reports panel
        }
    }
    
    
    
    
    /**
     * A common search method which takes in the search location, the search type, and the search text
     * @param searchLocation The panel from which the method was accessed(so that it updates the correct table with search results)
     * @param searchType The search criteria
     * @param searchText The text to be searched
     */
    public static void search(String searchLocation,String searchType,String searchText)
    {
        if(searchLocation.equals("catalogMainPanel") || searchLocation.equals("mainPanel") || searchLocation.equals("catalogSearchDialog"))
        {
            ArrayList<String> catalogSearchResults = new ArrayList<String>(); //temporarily stores all search matches
            
            if(searchType.equals("Title"))
            {
                for(Library item:DAPMain.libraryMap.values())//loop through all values in library hashmap and find 
                                                            //all similar matches and add to the arraylist
                {
                    if(item.getTitle().toLowerCase().indexOf(searchText)!=-1)
                    {
                        catalogSearchResults.add(item.getItemNumber());
                    }
                }
            }
            else if(searchType.equals("Author"))
            {
                for(Library item:DAPMain.libraryMap.values())
                {
                    if(item.getAuthor().toLowerCase().indexOf(searchText)!=-1)
                    {
                        catalogSearchResults.add(item.getItemNumber()); //loop through all values in library hashmap and find 
                                                                        //all similar matches and add to the arraylist
                    }
                }
            }
            else if(searchType.equals("Item Number"))
            {
                if(DAPMain.libraryMap.get(searchText.toUpperCase())!=null)//searches for the item
                {
                    catalogSearchResults.add(searchText.toUpperCase());
                }
            }



            if(catalogSearchResults.isEmpty()) //if no results found
            {
                DAP.displayErrorMessage("No results found for this entry! ");
                return;
            }
            
            
            
            DAPMain.itemsData = new String[catalogSearchResults.size()][5];
        
        
            int x = -1; //ticker for loop below
            for(String itemNumber:catalogSearchResults) //loop through all the itemNumbers found from the search
            {
                x++;

                Library item = DAPMain.libraryMap.get(itemNumber); //initialize a new Library object with a value from the arraylist

                DAPMain.itemsData[x][0] = item.getItemNumber();
                DAPMain.itemsData[x][1] = item.getTitle();
                DAPMain.itemsData[x][2] = item.getAuthor();
                DAPMain.itemsData[x][3] = item.getPublishDate();
                DAPMain.itemsData[x][4] = item.getItemType();

            }

            catalogSearchResults.clear();

            
            
            if(searchLocation.equals("mainPanel"))//redraws the search results table
            {
                DAP.searchResultsPanel.setVisible(true);
                DAP.mainPanel.setVisible(false);
                
                DAP.searchResultsTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.itemsData, DAPMain.itemsHeader));

                DAP.searchResultsTable.setRowSelectionInterval(0,0);
                
                DAPMain.mainFrame.setBounds(DAPMain.mainFrame.getX(),DAPMain.mainFrame.getY(),800,400);
            }
            else if(searchLocation.equals("catalogMainPanel"))//redraws the catalogTable
            {
                DAP.catalogTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.itemsData, DAPMain.itemsHeader));
                
                DAP.catalogFullCatalogButton.setVisible(true);
            }
            else if(searchLocation.equals("catalogSearchDialog"))//redraws the catalogSearchTable
            {
                DAP.catalogSearchTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.itemsData, DAPMain.itemsHeader));
            }
            
            
            
            
        }//end of first search location condition
        
        else if(searchLocation.equals("patronsMainPanel") || searchLocation.equals("patronSearchDialog"))
        {
            ArrayList<String> patronSearchResults = new ArrayList<String>();
        
            if(searchType.equals("First Name"))
            {
                for(Patrons patron:DAPMain.patronsMap.values())//loop through all values in Patrons hashmap and find 
                                                            //all similar matches and add to the arraylist
                {
                    if(patron.getFirstName().toLowerCase().indexOf(searchText)!=-1)
                    {
                        patronSearchResults.add(patron.getPatronNumber());
                    }
                }
            }
            else if(searchType.equals("Last Name"))
            {
                for(Patrons patron:DAPMain.patronsMap.values())
                {
                    if(patron.getLastName().toLowerCase().indexOf(searchText)!=-1)
                    {
                        patronSearchResults.add(patron.getPatronNumber()); //loop through all values in Patrons hashmap and find 
                                                                        //all similar matches and add to the arraylist
                    }
                }
            }
            else if(searchType.equals("Patron Number"))
            {
                if(DAPMain.patronsMap.get(searchText.toUpperCase())!=null)//searches for the patron
                {
                    patronSearchResults.add(searchText.toUpperCase());
                }
            }



            if(patronSearchResults.isEmpty()) //if no results found
            {
                DAP.displayErrorMessage("No results found for this entry!");
                return;
            }



            DAPMain.patronsData = new String[patronSearchResults.size()][9];


            int x = -1; //ticker for loop below
            for(String patronNumber:patronSearchResults) //loop through all the patronNumbers found from the search
            {
                x++;

                Patrons patron = DAPMain.patronsMap.get(patronNumber); //initialize a new Patrons object with a value from the arraylist

                DAPMain.patronsData[x][0] = patron.getPatronNumber();
                DAPMain.patronsData[x][1] = patron.getFirstName();
                DAPMain.patronsData[x][2] = patron.getLastName();
                DAPMain.patronsData[x][3] = patron.getEmail();
                DAPMain.patronsData[x][4] = patron.getPhoneNumber();
                DAPMain.patronsData[x][5] = patron.getAddress();
                DAPMain.patronsData[x][6] = patron.getCity();
                DAPMain.patronsData[x][7] = patron.getState();
                DAPMain.patronsData[x][8] = patron.getZipCode();

            }

            patronSearchResults.clear();
            
            
            
            if(searchLocation.equals("patronsMainPanel"))//redraws the patrons table
            {
                DAP.patronsTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.patronsData, DAPMain.patronsHeader));
                
                DAP.patronsAllPatronsButton.setVisible(true);
            }
            else if(searchLocation.equals("patronSearchDialog"))//redraws the patronSearchTable
            {
                DAP.patronSearchTable.setModel(new javax.swing.table.DefaultTableModel(DAPMain.patronsData, DAPMain.patronsHeader));
            }
            
            
            
        }
        
        
    }
    

    /**
     * Method to get current screen context to display appropriate help screen
     * @return The current screen context
     */   
    
    public static String getCurrentScreen()
    {
        if(DAP.mainPanel.isVisible())
            return "general";
        else if(DAP.catalogMainPanel.isVisible())
            return "catalog";
        else if(DAP.patronsMainPanel.isVisible())
            return "patrons";
        else if(DAP.typesMainPanel.isVisible())
            return "types";
        else if(DAP.reportMainPanel.isVisible())
            return "reports";
        else if(DAP.checkoutMainPanel.isVisible())
            return "checkout";
        else if(DAP.searchResultsPanel.isVisible())
            return "searchResultsPanel";
        else return "";
    }
    
    
    
    
    
    
}
