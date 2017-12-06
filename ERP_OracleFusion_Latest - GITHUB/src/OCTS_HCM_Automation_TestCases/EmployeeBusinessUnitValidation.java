/*===============================================================================================================================
        CLASS Name:    EmployeeBusinessUnitValidation
        CREATED BY:    Navya Mallajosyula
        DATE CREATED:  Dec 2017
        DESCRIPTION:   Employee Business Unit Validation                  
        PARAMETERS:                                                                  
        RETURNS:      
        COMMENTS:                                     
        Modification Log:
        Date                             Initials                                                Modification
        
-------------         ------------    ------------------------------------------------------------------------------------------------------------------------------*/
package OCTS_HCM_Automation_TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Common_Utility.*;
import OCTS_Automation_Main_Modules.*;

@SuppressWarnings("unused")
public class EmployeeBusinessUnitValidation extends ReporterBaseTest{
	
	static String workerTestDataPath = "C:\\Automation_OCTS\\Data\\WorkerTestData.xlsx";
	static HashMap<Integer, Cell> personNumber = new HashMap<Integer, Cell>();
	static HashMap<Integer, Cell> assignmentStatusType = new HashMap<Integer, Cell>();
	static String workerPersonNumberCol = "PersonNumber";
	static String workerBusinessUnitCol = "BusinessUnitShortCode";
	static String actualPersonNumber = "STUDENT1_PERSON117";
	static String actualBUName = "CTS BU001";
	static String expectedPersonNumber ="";
	static String expectedBUName ="";

	static HashMap<Object,Cell> businessUnitValidation = new HashMap<Object, Cell>();
	int flag = 0;

	public void getBusinessUnit() {
		ReadHCMWorker_InputDataSheet workerFile = new ReadHCMWorker_InputDataSheet();

		try {
			personNumber = workerFile.parseWorkerExcel(workerTestDataPath,workerPersonNumberCol);
			assignmentStatusType = workerFile.parseWorkerExcel(workerTestDataPath,workerBusinessUnitCol);
			for (Map.Entry<Integer, Cell> pNumber : personNumber.entrySet()) {
				for (Map.Entry<Integer, Cell> aNumber : assignmentStatusType.entrySet()) {
					if (pNumber.getKey().equals(aNumber.getKey())) {
						businessUnitValidation.put(pNumber.getValue(),aNumber.getValue());
					}
				}
			}
			for (Map.Entry<Object, Cell> editPersonNumber : businessUnitValidation.entrySet()){
				expectedPersonNumber = editPersonNumber.getKey().toString();
				if(expectedPersonNumber.equals(actualPersonNumber) ) 
				{
					expectedBUName = editPersonNumber.getValue().toString();
					if(expectedBUName.equals(actualBUName)){
					flag=1;
					break;
					}
				}
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag == 1)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has been assigned to BU #"+ ""+expectedBUName);
			Common_Utility.ReporterBaseTest.test.log(Status.PASS, "Employee First and Last Name matched with Worker Test Data Sheet  - " +expectedPersonNumber +"\n");
		}
		if(flag == 0)
		{
			System.out.println("Employee #"+ expectedPersonNumber + "" + " has not been assigned to BU #"+ ""+expectedBUName);
			Common_Utility.ReporterBaseTest.test.log(Status.FAIL, "Employee First and Last Name did not match with Worker Test Data Sheet  - " +expectedPersonNumber +"\n");
		}
		
	}
	
	@Test
	public void empBusinessUnitValidation() {
		EmployeeBusinessUnitValidation empBU = new EmployeeBusinessUnitValidation();
		System.out.println(workerTestDataPath);
		test=extent.createTest("Employee Business Unit  Validation");
		System.out.println("\n Employee Business Unit  Validation \n");
		empBU.getBusinessUnit();
	}
	
	
}
