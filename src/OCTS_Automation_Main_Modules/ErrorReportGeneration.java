/*===============================================================================================================================
        CLASS Name:    ErrorReportGeneration
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Journal Error Reporting                  
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package OCTS_Automation_Main_Modules;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import Common_Utility.ReporterBaseTest;

import com.aventstack.extentreports.Status;

import OCTS_Automation_Main_Modules.ReadERPInputDataSheet;

import org.testng.annotations.Test;

@SuppressWarnings("unused")
public class ErrorReportGeneration extends ReporterBaseTest{
	static int errorStartLine;
	static int errorLastLine;
	//static String zipFilePath = "C:\\Automation_OCTS\\Output\\71963.zip";  
    static String destDir = "C:\\Automation_OCTS\\Output\\";
    static String fileObj;
	//static String fileObj = zipFilePath.replace("zip", "txt");
	static StringBuffer errorLog = new StringBuffer();
	//unzip the output file to enable comparision
		
		
	@Test
	public void journalErrorReportGeneration() throws IOException
	{
		ERP_Financial_Webservice_MainClass ef = new ERP_Financial_Webservice_MainClass();
		
		fileObj=ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
		System.out.println(fileObj);
		 test=extent.createTest("Error report generation");
		 System.out.println("\n Error Reporting \n");
		LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(
				new FileInputStream(fileObj), "UTF-8"));
			try{

				    String line = null;
				    line=line+lineNumberReader.readLine();
				    while((line = lineNumberReader.readLine()) != null) {
					    line=line+lineNumberReader.readLine();
					    if(line.contains("Error Lines"))	
					        errorStartLine = lineNumberReader.getLineNumber();

					    	if(line.contains("End of Report"))
					        errorLastLine = lineNumberReader.getLineNumber();
				    }
					
				}
			catch(Exception e){}
			 LineNumberReader lineNumberReader1 = new LineNumberReader(new InputStreamReader(
						new FileInputStream(fileObj), "UTF-8"));
			try{

			    String line = null;
			    line=line+lineNumberReader1.readLine();
			    while((line = lineNumberReader1.readLine()) != null) {
				
				    int getCurrentLineNumber =  lineNumberReader1.getLineNumber();
				    if(getCurrentLineNumber>=errorStartLine && getCurrentLineNumber<=errorLastLine)
				    {
				    	System.out.println(line);
				    }
			    }
			    Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Step 1: Error report generation is successful" );
			}
		catch(Exception e){
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Step 1: Error report generation is NOT successful" );
		}
			lineNumberReader.close();
			lineNumberReader1.close();
			
	}


}