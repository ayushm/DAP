/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dap;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.awt.*;
import java.io.*;

/**
 * The main class which runs the DAP class, and declares all major data structures.
 * @author Ayush Mehra
 */
public class DAPMain {

    
    
    /**
     * The main JFrame on which everything is displayed
     */
    public static DAP mainFrame; //creates a new object of DAP, which extends JFrame.
    
    
    
    
    /**
     * patronsMap data structure of type HashMap<String,Patrons>.
     * Stores all Patron objects.
     */
    public static HashMap<String,Patrons> patronsMap = new HashMap<String,Patrons>();
    
    /**
     * typesMap data structure of type HashMap<String,Types>.
     * Stores all Types objects.
     */
    public static HashMap<String,Types> typesMap = new HashMap<String,Types>();
    
    /**
     * libraryMap data structure of type HashMap<String,Library>.
     * Stores all Library objects.
     */
    public static HashMap<String,Library> libraryMap = new HashMap<String,Library>();
    
    /**
     * itemsOutMap data structure of type HashMap<String,ItemsOut>.
     * Stores all ItemsOut objects.
     */
    public static HashMap<String,ItemsOut> itemsOutMap = new HashMap<String,ItemsOut>();
    
    //end of data structures declarations
    
    
    /**
     * Two dimensional array of String objects.
     * Stores data to populate the typesTable.
     */
    public static String [][] typesData;   
    
    /**
     * The column headers of the typesTable
     */
    public  static String [] typesHeader =  new String [] {
                "Type", "Description"};        

    

        
    /**
     * The column headers of the patronsTable and the patronSearchTable
     */
    public static  String [] patronsHeader =  new String [] {
                "Patron #", "First Name", "Last Name", "Email", "Phone", "Address", "City", "State", "Zip Code"};     
    
    /**
     * Two dimensional array of String objects.
     * Stores data to populate the patronsTable and patronsSearchTable.
     */
    public static String[][] patronsData;
    
    
    
    
    /**
     * Two dimensional array of String objects.
     * Stores data to populate the catalogTable,searchResultsTable, and catalogSearchTable.
     */
    public static String [][] itemsData;

    /**
     * The column headers of catalogTable,searchResultsTable, and catalogSearchTable.
     */
    public static  String [] itemsHeader =  new String [] {
                "Item #", "Title", "Author","Published Date","Type"};        

    /**
     * The variable to track if any errors were found while saving catalog.
     */
    public static boolean catalogError = false;

    /**
     * The variable to track if any errors were found while saving patron.
     */
    public static boolean patronError = false;

    /**
     * The variable to track if any errors were found while saving type.
     */
    public static boolean typeError = false;

    /**
     * The variable to track if any errors were found while saving itemsOut.
     */
    public static boolean itemsOutError = false;
    
    public static boolean filesNotFoundError = false;
    
    /**
     * Two dimensional array of String objects.
     * Stores data to populate the checkoutTable.
     */
    public static String [][] checkoutData;
    
    /**
     * The column headers of the checkoutTable
     */
    public static  String [] checkoutHeader =  new String [] {
                "Item #", "Title", "Author","Type","Date Out","Date Due"}; 
    
    
    
    
    /**
     * Two dimensional array of String objects.
     * Stores data to populate the searchResultsTable.
     */
    //public static String [][] searchResultsData;
    
    /**
     * 
     */
    //public static String [] searchResultsHeader = new String [] {
               // "Item #", "Title", "Author","Type","Available"};             
    
    
    
    
    
    
    
    /**
     * Public static String variable to determine whether a save button is being called on a new or edited item.
     */
    public static String editOrNew; //to track whether the save is for new or edit        
    
    /**
     * Public static String variable of the current date using the DateFormat class.
     */
    public static String currentDateString = DateFormat.getDateInstance().format(new Date());
    
    
    
    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        /*
         * Initialize all varaibles here
         * 
         * 
         */
        
        //Library.initializeLibrary();        
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                int errorInRead = 0;
               
                try
                {
                    DataInit.read("types.txt", "types","");
                    DataInit.hashmapToArrayTypes();


                    DataInit.read("library.txt", "library","");
                    DataInit.hashmapToArrayLibrary();

                    DataInit.read("patrons.txt", "patrons","");
                    DataInit.hashmapToArrayPatrons();

                    DataInit.read("itemsout.txt", "itemsout","");
                    
                    
                }
                catch(Exception e)
                {
                    errorInRead = 1;
                }
                

                  
                  
              mainFrame = new DAP();
              
              DAP.displayDate.setText(DAPMain.currentDateString);
              
              mainFrame.setVisible(true);
              
              mainFrame.setBounds(0,0,610,330); //sets size of the frame when first opened
              
              mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/frameIcon.png"));
              DAP.mainHelpFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/helpIcon.png"));
                                         
                
              if(errorInRead == 1)
              {
//                  DAP.displayErrorMessage("Unable to read one or more data files");
//                  System.exit(0);
                  DAP.displayErrorMessage("Unable to read one or more data files. \n\n You must restore data from a previous backup");
                  DAPMain.filesNotFoundError = true;
                  DAP.restoreDialog.setVisible(true);
              }
                
            }
        });
    }
}
