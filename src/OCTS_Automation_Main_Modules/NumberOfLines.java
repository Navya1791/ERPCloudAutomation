/*===============================================================================================================================
        CLASS Name:    NumberOfLines
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Number of Lines  Validation                    
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
import com.aventstack.extentreports.Status;
import OCTS_Automation_Main_Modules.ReadERPInputDataSheet;
import Common_Utility.ReporterBaseTest;
import org.testng.annotations.Test;

public class NumberOfLines extends ReporterBaseTest{

	// Required Variables to pull out the matching string

	//	static String zipFilePath = "C:\\Automation_OCTS\\Output\\71963.zip";  
		static String fileObj;
		static String destDir = "C:\\Automation_OCTS\\Output\\";
	//	static String fileObj = zipFilePath.replace("zip", "txt");
		static int numberOfLines;
		static String[] splitNewLine = {};
		static String finalOutputString = "";
		static List<String> finalOutputValues = new ArrayList<String>();
		int flag=0;
		@Test
		public void numberOfLines() throws IOException {
			fileObj=ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
			System.out.println(fileObj);
			 test=extent.createTest("Number of Lines");
			//unzip the output file to enable comparision
		//	UnzipOutputFileGenerated unZip = new UnzipOutputFileGenerated();
			//unZip.unzip(zipFilePath, destDir);
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
			if(flag==1){
			System.out
					.println("Total number of Lines from the Output File is \n"
						  + finalOutputString);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Step 1: Total number of Lines from the Output File is " +finalOutputString);
			}
			
			else
			{
		
				System.out.println("Please check if the Total Number of Lines entered is correct");
				Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Step 1: Total number of Lines from the Output File is not matching with input file");
			}
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