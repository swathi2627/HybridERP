package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtilities;

public class DriverScript {
	WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./Fileoutput/Hybridresults.xlsx";
	String TCSheet = "MasterTestCases";
	ExtentReports reports;
	ExtentTest logger;

	public void startTest() throws Throwable {

		String Module_Stsus = "";
		String Module_New = "";

		ExcelFileUtilities excel = new ExcelFileUtilities(inputpath);

		int row = excel.rowCount(TCSheet);
		for (int i = 1; i <= row; i++) {

			if (excel.getCellData(TCSheet, i, 2).equalsIgnoreCase("y")) {
				excel.setCellData(TCSheet, i, 3, "", outputpath);
				String TCModule = excel.getCellData(TCSheet, i, 1);

				reports = new ExtentReports(
						"./target/Reports/" + TCModule  + "_" + FunctionLibrary.generateDate()+ ".html");
				// irerating rows from TCModule sheet
				logger = reports.startTest(TCModule);
				logger.assignAuthor("Swathi");
				
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
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("dropDownActiion")) {
							FunctionLibrary.dropDownActiion(LocatorType, LocatorValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}

						if (ObjType.equalsIgnoreCase("captureStock")) {
							FunctionLibrary.captureStock(LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("stockTable")) {
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}

						if (ObjType.equalsIgnoreCase("capturesup")) {
							FunctionLibrary.capturesup(LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}

						if (ObjType.equalsIgnoreCase("suppliertable")) {
							FunctionLibrary.suppliertable();
							logger.log(LogStatus.INFO, Description);
						}

						if (ObjType.equalsIgnoreCase("capturecus")) {
							FunctionLibrary.capturecus(LocatorType, LocatorValue);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjType.equalsIgnoreCase("customertable")) {
							FunctionLibrary.customertable();
							logger.log(LogStatus.INFO, Description);
						}
						// write as pass into sheet
						excel.setCellData(TCModule, j, 5, "pass", outputpath);
						Module_Stsus = "true";
						logger.log(LogStatus.PASS, Description);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						excel.setCellData(TCModule, j, 5, "fail", outputpath);
						Module_New = "false";
						logger.log(LogStatus.FAIL, Description);
						
						File screenShot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screenShot,new File("./target/screenshot/"+Description+"_" +FunctionLibrary.generateDate() + ".png"));
					}
					if (Module_Stsus.equalsIgnoreCase("true")) {
						excel.setCellData(TCSheet, i, 3, "pass", outputpath);
					}
					reports.endTest(logger);
					reports.flush();
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
