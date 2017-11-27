/*===============================================================================================================================
        CLASS Name:    NumberOfLines
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Validation of Number of Lines from GI interface file                    
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package OCTS_Automation_Main_Modules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class NumberOfLines {

	// Required Variables to pull out the matching string

		/*static String zipFilePath = "C:\\Automation_OCTS\\Output\\70594.zip";  
		static String destDir = "C:\\Automation_OCTS\\Output\\";*/
		static String fileObj = ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
		static int numberOfLines;
		static String[] splitNewLine = {};
		static String finalOutputString = "";
		static List<String> finalOutputValues = new ArrayList<String>();
		int flag=0;
		@Test
		public void numberOfLines() throws IOException {
			
			//Get Number of Lines from GlInterface file
			ReadERPInputDataSheet inputGIInterface = new ReadERPInputDataSheet();
			numberOfLines = inputGIInterface.parseGIInterfaceFile();
			String noOfRows = Integer.toString(numberOfLines);
			getTotalNumberOfLines();
			
			for (int i = 0; i < finalOutputValues.size() - 1; i++) {
				if (finalOutputValues.get(3).equals(noOfRows)) {
				finalOutputString = finalOutputValues.get(3);
				flag=1;
				}
			}
			if(flag==1)
			System.out
					.println("Total number of Lines from the Output File is \n"
						  + finalOutputString);
			if(flag==0)
				System.out.println("Please check if the Total Number of Lines entered is correct");
		}

		// Method to parse the text file and pull out the Matching pattern to
		// validate the excel

		public static List<String> getTotalNumberOfLines() throws IOException {
			try {
				String line = null;
				String newLine = "";
				@SuppressWarnings("resource")
				BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileObj), "UTF-8"));

				line = line + bufRdr.readLine();
				
				while ((line = bufRdr.readLine()) != null) {
					line = bufRdr.readLine();
					if (line.contains("TOTALS")) {
						//System.out.println("Before trimming spaces\n" + line);
						newLine = line.trim();
						//System.out.println("After trimming spaces\n" + newLine);
						splitNewLine = newLine.split("\\s");
						for (String matchString : splitNewLine) {
							if ( (matchString.length() != 0)
									&& (!matchString.matches("[^\\w.*]")))

							{

								finalOutputValues.add(matchString);
							}

						}
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return finalOutputValues;

		}


}
