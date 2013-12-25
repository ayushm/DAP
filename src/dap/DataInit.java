/**
 * @(#)DataInit.java
 *
 *
 * @author
 * @version 1.00 2012/1/16
 */

package dap;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class used to initialize all data structures, read and write CSV text files.
 * @author Ayush Mehra
 */
public class DataInit
{


    /**
     * Reads from the inputed text file and saves to the appropriate HashMap data structure
     * @param fileName The name of the file to read.
     * @param readType The type of file to be read (patrons,library,types,itemsOut).
     */
    public static void read(String fileName, String readType,String restoreClassPath)
    {
    	BufferedReader CSVFile = null;
        boolean needToClose = true;       
        
    	try
    	{

       
         String fullName = "data/"+fileName;
         
         if(!restoreClassPath.equals(""))
         {
             CSVFile = new BufferedReader(new InputStreamReader(new FileInputStream(restoreClassPath+"/"+fileName)));
             
             if(readType.equals("patrons"))
             {
                 DAPMain.patronsMap.clear();
             }
             else if(readType.equals("types"))
             {
                 DAPMain.typesMap.clear();
             }
             else if(readType.equals("itemsout"))
             {
                 DAPMain.itemsOutMap.clear();
             }
             else if(readType.equals("library"))
             {
                 DAPMain.libraryMap.clear();
             }
             
            
//         DAPMain.itemsOutMap = new HashMap<String,ItemsOut>();
//         DAPMain.typesMap = new HashMap<String,Types>();
//         DAPMain.patronsMap = new HashMap<String,Patrons>();
//         DAPMain.libraryMap = new HashMap<String,Library>();
         }
         else
         {
             CSVFile = new BufferedReader(new InputStreamReader(new FileInputStream(fullName))); //inputStreamReader is much more efficient than Scanner
         }
		String dataRow = CSVFile.readLine();
                
               // if(!restoreClassPath.equals(""))
                   // System.out.println(dataRow);

		while (dataRow != null){
		String[] dataArray = dataRow.split(","); //puts all the data read in the line into an array
                                                        //then assigns it correctly to an object of the appropriate class

		if(readType.equals("patrons"))
		{
                    DAPMain.patronsMap.put(dataArray[0],new Patrons(dataArray[0],dataArray[1],
				dataArray[2],dataArray[3],dataArray[4],dataArray[5],
				dataArray[6],dataArray[7],dataArray[8]));                   
		}
		else if(readType.equals("library"))
		{
                    DAPMain.libraryMap.put(dataArray[0],new Library(dataArray[0],dataArray[1],
				dataArray[2],dataArray[3],dataArray[4]));
		}
		else if(readType.equals("itemsOut"))
		{
                    DAPMain.itemsOutMap.put(dataArray[0],new ItemsOut(dataArray[0],dataArray[1],
				dataArray[2],dataArray[3]));
		}
		else if(readType.equals("types"))
		{
		    DAPMain.typesMap.put(dataArray[0],new Types(dataArray[0],dataArray[1]));
		}
		else if(readType.equals("itemsout"))
		{
		    DAPMain.itemsOutMap.put(dataArray[0],new ItemsOut(dataArray[0],dataArray[1],dataArray[2],dataArray[3]));
		}

                dataRow = CSVFile.readLine();
                
            }

    	}      catch (IOException e)
          {
             
             if(restoreClassPath.equals(""))
             {
                DAP.displayErrorMessage("One or more data files were not found \n\n"+e);
                System.exit(0);                                  
                 
             }
             else if(!restoreClassPath.equals("") ) 
             { 
                 if (readType.equals("types"))
                 {    
                    DAP.displayErrorMessage("One or more data files were not found \n\n"+e);
                 } 
                 needToClose = false;
  
             }
          }
          finally
          {
              try
              {
			
                  if (needToClose)
                  CSVFile.close();

              }
               catch (IOException e)
              {
                DAP.displayErrorMessage("Exception encountered while closing the CSV file : "+e);
                System.exit(0);

              }

           }

}
    
    
    /**
     * Writes to the inputed text file, according to the inputed type
     * @param fileName The file name under which the file is written.
     * @param writeType The type of file to write (patrons,library,types,itemsOut).
     */
    public static void write(String fileName,String writeType,String backupClassPath)
    {

      BufferedWriter out = null;
      BufferedWriter backupWriter = null;
      String temp;

          try
          {
                
                String fullName = "data/"+fileName;
              
                if(!backupClassPath.equals(""))
                {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(backupClassPath+"/"+fileName), "UTF-8"));
                }
                else
                {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fullName), "UTF-8"));
                }

                if(writeType.equals("patrons"))
                {
                    

                    for(Patrons currentPatron:DAPMain.patronsMap.values())
                    {
                        

                        temp = currentPatron.getPatronNumber()+","+currentPatron.getFirstName()+","+currentPatron.getLastName()
                                +","+currentPatron.getEmail()+","+currentPatron.getPhoneNumber()+","+currentPatron.getAddress()+","+
                                        currentPatron.getCity()+","+currentPatron.getState()+","+currentPatron.getZipCode();

                        out.write(temp);
                        out.newLine();
                    }
                }
                else if(writeType.equals("library"))
                {                    
                    for(Library currentLibrary:DAPMain.libraryMap.values())
                    {
                        temp = currentLibrary.getItemNumber()+","+currentLibrary.getItemType()+","+currentLibrary.getTitle()
                                        +","+currentLibrary.getAuthor()+","+currentLibrary.getPublishDate();

                        out.write(temp);
                        out.newLine();
                    }
                }
                else if(writeType.equals("types"))
                {                        
                    for(Types currentType:DAPMain.typesMap.values())
                    {
                        temp = currentType.getType()+","+currentType.getDescription();

                        out.write(temp);
                        out.newLine();
                    }
                }
                else if(writeType.equals("itemsout"))
                {
                    for(ItemsOut currentItemOut:DAPMain.itemsOutMap.values())
                    {
                        temp = currentItemOut.getItemNumber()+","+currentItemOut.getPatronNumber()+","+
                                currentItemOut.getCheckoutDate()+","+currentItemOut.getDueDate();
                        out.write(temp);
                        out.newLine();

                    }
                }
                

          }
          catch (IOException e)
          {
              if(!DAPMain.filesNotFoundError)
              {
                  DAP.displayErrorMessage("Exception encountered while writing to file : "+e);
              }
              else
              {
                  DAP.displayErrorMessage("Exception encountered while writing to file : "+e+"\nThe program will now close.");
                  System.exit(0);
              }
              
          }
          finally
          {
              try
              {
                  out.flush();
                  out.close();
              }
               catch (IOException e)
              {
                DAP.displayErrorMessage("Exception encountered while closing the write file : "+e);
              }

          }

    }
    
    
    
    
    
    /**
     * Initializes the patronsData with which the patronsTable is populated.
     * Uses the information from patronsMap.
     */
    public static void hashmapToArrayPatrons()
    {

            //Copy Types hashmap to array for grid display on Types Screen


            int aLength = DAPMain.patronsMap.size();

            //DAP.displayErrorMessage("PatronsMapSize ");

            //Patrons[] patronValues = DAPMain.patronsMap.values();

            DAPMain.patronsData = new String[aLength][9];

            int i = -1;

            for(Patrons patron:DAPMain.patronsMap.values())
            {
                i++;


                DAPMain.patronsData[i][0] = patron.getPatronNumber();
                DAPMain.patronsData[i][1] = patron.getFirstName();
                DAPMain.patronsData[i][2] = patron.getLastName();
                DAPMain.patronsData[i][3] = patron.getEmail();
                DAPMain.patronsData[i][4] = patron.getPhoneNumber();
                DAPMain.patronsData[i][5] = patron.getAddress();
                DAPMain.patronsData[i][6] = patron.getCity();
                DAPMain.patronsData[i][7] = patron.getState();
                DAPMain.patronsData[i][8] = patron.getZipCode();

            }



    }
    
    
    
    /**
     * Initializes the typesData with which the typesTable is populated.
     * Uses the information from typesMap.
     */
    public static void hashmapToArrayTypes()
    {
    
	//Copy Types hashmap to array for grid display on Types Screen

	int aLength = DAPMain.typesMap.size();
                
        DAPMain.typesData = new String[aLength][2];
        
        int i = -1;

	for(Types type:DAPMain.typesMap.values())
        {
            i++;
            DAPMain.typesData[i][0] = type.getType();
            DAPMain.typesData[i][1] = type.getDescription();
            
        }

    }
    
    
    /**
     * Initializes the itemsData with which the catalogTable is populated.
     * Uses the information from itemsMap.
     */
    public static void hashmapToArrayLibrary()
    {
    
	//Copy Library hashmap to array for grid display on Items Screen

	int aLength = DAPMain.libraryMap.size();
                
        DAPMain.itemsData = new String[aLength][5];
        
        int i = -1;

	for(Library item:DAPMain.libraryMap.values())
        {
            i++;
            DAPMain.itemsData[i][0] = item.getItemNumber();
            DAPMain.itemsData[i][1] = item.getTitle();
            DAPMain.itemsData[i][2] = item.getAuthor();
            DAPMain.itemsData[i][3] = item.getPublishDate();
            DAPMain.itemsData[i][4] = item.getItemType();
            
        }

    }
    
    
    
    
    
}
    
