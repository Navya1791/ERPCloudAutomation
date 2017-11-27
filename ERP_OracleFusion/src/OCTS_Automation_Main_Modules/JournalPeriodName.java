/*===============================================================================================================================
        CLASS Name:    JournalPeriodName
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Nov 2017
        DESCRIPTION:   Journal Entry Period Name Validation                    
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

public class JournalPeriodName extends ReadERPInputDataSheet{
	
	/*static String zipFilePath = "C:\\Automation_OCTS\\Output\\70594.zip";  
    static String destDir = "C:\\Automation_OCTS\\Output\\";*/
    static String fileObj = ERP_Financial_Webservice_MainClass.zipFilePath.replace("zip", "txt");
	static String periodName = "";
	static String[] splitNewLine = {};
	static String finalOutputString = "";
	static String getPeriodNameValue = "";
	static int flag =0;
	static String ColumnWanted = "SEJRRequestImport_PL2_JournalPeriodName";
	static ArrayList<Cell> outputValue = new ArrayList<Cell>();
	static String excelPath = "C:\\Automation_OCTS\\Data\\ERP_InputDatasheet.xlsx";
	
	public static String getPeriodName() {
		try {
			String line = null;
			String newLine = "";
			Pattern pattern;
			Matcher matcher;
			@SuppressWarnings("resource")
			BufferedReader bufRdr = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileObj), "UTF-8"));

			line = line + bufRdr.readLine();

			while ((line = bufRdr.readLine()) != null) {

				if (line.contains("Feb-14")) {
					//System.out.println("Before trimming spaces\n" + line);
					newLine = line.trim();
					//System.out.println("After trimming spaces\n" + newLine);
					splitNewLine = newLine.split("\\s");
					for (String matchString : splitNewLine) {
						pattern = Pattern.compile(periodName);
						matcher = pattern.matcher(matchString);
						if (matcher.matches()){
							getPeriodNameValue = matchString;
							flag =1;
							}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPeriodNameValue;
	}
	@Test
	public void journalPeriodname() throws IOException {
		ReadERPInputDataSheet re = new ReadERPInputDataSheet();
		outputValue = re.parseInputExcelFile(excelPath,ColumnWanted);
		periodName = outputValue.get(0).toString();
		
		getPeriodName();
		finalOutputString = getPeriodNameValue;
		if(flag==1)
		System.out.println("Period Name extracted from the output file is \n" +finalOutputString);
		if(flag==0)
			System.out.println("Please check if the Period Name keyed is correct");
	}

}
