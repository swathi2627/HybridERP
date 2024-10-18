package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtilities;

public class DriverScript {
	WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./Fileoutput/Hybridresults.xlsx";
	String TCSheet = "MasterTestCases";

	public void startTest() throws Throwable {

		String Module_Stsus = "";
		String Module_New = "";

		ExcelFileUtilities excel = new ExcelFileUtilities(inputpath);

		int row = excel.rowCount(TCSheet);
		for (int i = 1; i <= row; i++) {

			if (excel.getCellData(TCSheet, i, 2).equalsIgnoreCase("y")) {
				excel.setCellData(TCSheet, i, 3, "", outputpath);
				String TCModule = excel.getCellData(TCSheet, i, 1);

				// irerating rows from TCModule sheet

				for (int j = 1; j <= excel.rowCount(TCModule); j++) {

					// read all cells

					String Description = excel.getCellData(TCModule, j, 0);
					String ObjType = excel.getCellData(TCModule, j, 1);
					String LocatorType = excel.getCellData(TCModule, j, 2);
					String LocatorValue = excel.getCellData(TCModule, j, 3);
					String TestData = excel.getCellData(TCModule, j, 4);

					try {
						if (ObjType.equalsIgnoreCase("startBrowser")) {
							driver = FunctionLibrary.startBrowser();
						}
						if (ObjType.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl();
						}
						if (ObjType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(LocatorType, LocatorValue, TestData);
						}
						if (ObjType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(LocatorType, LocatorValue, TestData);
						}
						if (ObjType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(LocatorType, LocatorValue);
						}
						if (ObjType.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(TestData);
						}
						if (ObjType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
						}

						excel.setCellData(TCModule, j, 5, "pass", outputpath);
						Module_Stsus = "true";
					} catch (Exception e) {
						System.out.println(e.getMessage());
						excel.setCellData(TCModule, j, 5, "fail", outputpath);
						Module_New = "false";
					}
					if (Module_Stsus.equalsIgnoreCase("true")) {
						excel.setCellData(TCSheet, i, 3, "pass", outputpath);
					}
				}
					if (Module_New.equalsIgnoreCase("false")) {
                          excel.setCellData(TCSheet, i, 3, "fail", outputpath);
					}
				
			}

			else {
				excel.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
		}
	}

}
