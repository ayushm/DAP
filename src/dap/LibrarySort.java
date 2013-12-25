package dap;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
/**
 * A Class for sorting a two-dimensional array's data on any two columns and displaying the reports.
 * @author Ayush Mehra
 */
public class LibrarySort{

    /**
     * Sorts a tables data (two-dimensional array) on any two columns
     * Used to display the reports.
     * @param <T>
     * @param toSort
     * @param onColumn1
     * @param onColumn2
     */
    public static < T extends Comparable> void sort(final T [][] toSort,final int onColumn1, final int onColumn2) {
        List list = Arrays.asList(toSort);
        Collections.sort(
            list, new Comparator(){
                public int compare(Object x, Object y) {
                    String[] a = (String[])x;
                    String[] b = (String[])y;
                    
                    if (a[onColumn1].compareTo(b[onColumn1]) == 0) {
                        return a[onColumn2].compareTo(b[onColumn2]);
                    } else {
                        return a[onColumn1].compareTo(b[onColumn1]);
                    }
                }

            }
        );
    }
    
  
    
        
    /**
     * 
     * @param s The original string.
     * @param n The number of spaces to be added 
     * @return The passed in string(s), padded with n spaces
     */
    public static String pad(String s, int n) {
    return String.format("%1$-" + n + "s", s);
  }
    
    
    /**
     * This method prints the report of all items in the library
     * @return The formatted report output as a String
     */
    public static String printItemsList(String itemType)
    {
                
        StringBuffer buffAll = new StringBuffer();

        LibrarySort.sort(DAPMain.itemsData,4,1);
        
        
        
        
        // writing header 
        buffAll.append("\n");
                buffAll.append("Type    Item#          Title                        Author            Published Date"+"\n"); 
                buffAll.append("----    -----          -----                        ------            --------------"+"\n");
        
        int lineCount  = 4;
        
        for(int i=0; i<DAPMain.itemsData.length; i++)        
        {
            if (lineCount >= 58)
            {
                // writing header 
                
                buffAll.append("\f");
                buffAll.append("\n");
                buffAll.append("Type    Item#          Title                        Author            Published Date"+"\n"); 
                buffAll.append("----    -----          -----                        ------            --------------"+"\n");
                buffAll.append("\n");
                
                lineCount = 4;                
                
            }    
            if(itemType.equals("ALL") || itemType.equals(DAPMain.itemsData[i][4]))
            {
                        buffAll.append( pad(DAPMain.itemsData[i][4],10).substring(0,6)+"  "+
                            pad(DAPMain.itemsData[i][0],10).substring(0,6)+"         "+
                            pad(DAPMain.itemsData[i][1],30).substring(0,20)+"         "+
                            pad(DAPMain.itemsData[i][2],15).substring(0,10)+"         "+
                            pad(DAPMain.itemsData[i][3],25).substring(0,20)
                           );
                      buffAll.append("\n");

                lineCount++;
            }
        }
        
            if(lineCount == 4)
            {
                buffAll.append("\nThere are no items categorized by the type - "+itemType);
            }    


      return buffAll.toString();        
        
        
    }
    
    
    
        

    /**
     * This method reverses the date for sorting
     * @param inDate The date to be reversed in the formate mm/dd/yyyy
     * @return The reversed date in the format yyyy/mm/dd
     */
    public static String getReverseDate(String inDate)
   {
      if (inDate.length() != 11)
      {
		  return inDate;
      }

	  return inDate.substring(6)+ "/"+inDate.substring(0,2)+"/"+inDate.substring(1,2);

   }
	

    /**
     * 
     *  This method prints the report of all the checked out items
     * @return The formatted report output as a String
     */
    public static String printItemsOutList(String reportType)
    {


       //define and initialize all variables

        StringBuffer buffAll = new StringBuffer();

        String[][] printData = new String[DAPMain.itemsOutMap.size()][9];

        
        int lineCount = 0 ;
        String prvPatron = "x";
        String prvDueDate = "x";
        boolean firstPage = true;
        int i = 0 ;

        String patronNumber      = " ";
        String patronName        = " ";
        String itemNumber        = " ";
        String title             = " ";
        String type              = " ";
        String author            = " ";
        String dueDate           = " ";
        String overDue           = " "; 
        Date   dDueDate          = new Date();
        Date   currentDate       = new Date();
        // get the print Data 
		
	for(ItemsOut item:DAPMain.itemsOutMap.values())//iterates through all the values of itemsOutMap
        {

                printData[i][0] =  item.getItemNumber(); 
                printData[i][1] =  item.getPatronNumber();
                printData[i][2] =  item.getDueDate();
                printData[i][3] =  getReverseDate(item.getDueDate());

                Patrons p = DAPMain.patronsMap.get(item.getPatronNumber());
                Library x = DAPMain.libraryMap.get(item.getItemNumber());


                printData[i][4] =  p.getFirstName()+" "+p.getLastName();
                printData[i][5] =  x.getTitle();
                printData[i][6] =  x.getItemType();
                printData[i][7] =  x.getAuthor();

                i++;

        }     
		
        if (reportType == "patrons")
        {
                    LibrarySort.sort(printData,1,3);         //Sort on Patrons & Due Date

                    lineCount = 70; 


                    // Loop through all records 

                    for(int z=0; z<printData.length; z++)        
                    {

                        // copy values to temp variables

                        patronNumber      = pad(printData[z][1],20).substring(0,6);
                        patronName        = pad(printData[z][4],35).substring(0,30);
                        itemNumber        = pad(printData[z][0],20).substring(0,6);
                        title             = pad(printData[z][5],35).substring(0,30);
                        type              = pad(printData[z][6],10).substring(0,6);
                        author            = pad(printData[z][7],30).substring(0,20);
                        dueDate           = pad(printData[z][2],15).substring(0,11);
                        
                        dDueDate          = new Date(printData[z][2]);
                        
                        
                        if (currentDate.after(dDueDate))
                        {
                            overDue = "*";
                        }else
                        {
                            overDue = " ";
                        }    
                                

                        if ((lineCount > 58) ||  ( !prvPatron.equals(patronNumber)))
                        {
                            //print header
                            if (!firstPage)
                            {    
                                buffAll.append("\n");
                                buffAll.append("\f");
                                buffAll.append("\n");
                            }
 
                            buffAll.append("Patron Name :  "+patronName.trim()+" ("+patronNumber.trim()+")"+"\n");
                            buffAll.append("\n");
                            buffAll.append("Type   Item#    Title                           Author                Due Date  OverDue "+"\n"); 
                            buffAll.append("----   -----    -----                           ------                --------  ------- "+"\n");

                            lineCount  = 6;
                            prvPatron = patronNumber;
                            firstPage = false;

                        }
                        buffAll.append( type+" "+itemNumber+"   "+title+"  "+author+"  "+dueDate+"  "+overDue+"\n");

                        lineCount++;	
                }   		


        }else
        {
            LibrarySort.sort(printData,3,1);         //Sort on Due date & Patrons 

		
            lineCount = 70; 


            // Loop through all records 

            for(int z=0; z<printData.length; z++)        
            {

                // copy values to temp variables

                patronNumber      = pad(printData[z][1],20).substring(0,6);
                patronName        = pad(printData[z][4],35).substring(0,30);
                itemNumber        = pad(printData[z][0],20).substring(0,6);
                title             = pad(printData[z][5],35).substring(0,30);
                type              = pad(printData[z][6],10).substring(0,6);
                author            = pad(printData[z][7],30).substring(0,20);
                dueDate           = pad(printData[z][2],15).substring(0,11);


                if ((lineCount > 58) ||  ( !prvDueDate.equals(dueDate)))
                {
                        //print header
                        if (!firstPage)
                        {
                            buffAll.append("\f");
                            buffAll.append("\n");
                            buffAll.append("\n");
                        }
                        buffAll.append("Due Date :  "+dueDate+"\n");
                        buffAll.append("\n");
                        buffAll.append("Type   Item#    Title                           Author                Patron "+"\n"); 
                        buffAll.append("----   -----    -----                           ------                ------ "+"\n");
                        buffAll.append("\n");

                        lineCount  = 6;
                        prvDueDate     = dueDate;
                        firstPage = false;
                }

                buffAll.append( type+" "+itemNumber+"   "+title+"  "+author+"  "+patronName+"\n");

             lineCount++;	
	}
    }
         return buffAll.toString();        

    }        


    
    
}   