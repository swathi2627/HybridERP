package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties property;

	public static WebDriver startBrowser() throws Throwable {
		property = new Properties();
		property.load(new FileInputStream("./PropertyFiles/Environment.properties"));

		if (property.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();

		} else if (property.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else {
			Reporter.log("browser value not found ....", true);
		}

		return driver;
	}

	public static void openUrl() {
		driver.get(property.getProperty("Url"));
	}

	public static void waitForElement(String LocatorType, String LocatorValue, String TestData) {

		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));

		if (LocatorType.equalsIgnoreCase("xpath")) {
			explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(LocatorValue)));
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}

	public static void typeAction(String LocatorType, String LocatorValue, String TestData) {
		if (LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}

	public static void clickAction(String LocatorType, String LocatorValue) {
		if (LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).click();
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).click();
		}
	}

	public static void validateTitle(String ExpectedTitle) {
		String actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, ExpectedTitle, "not matching");
		} catch (AssertionError e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public static void closeBrowser() {
		driver.quit();
	}

	public static void dropDownActiion(String LocatorType, String LocatorValue, String TestData) {

		int index = Integer.parseInt(TestData);
		if (LocatorType.equalsIgnoreCase("xpath")) {
			Select drpdwn = new Select(driver.findElement(By.xpath(LocatorValue)));
			drpdwn.selectByIndex(index);
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			Select drpdwn = new Select(driver.findElement(By.id(LocatorValue)));
			drpdwn.selectByIndex(index);
		}
		if (LocatorType.equalsIgnoreCase("xpath")) {
			Select drpdwn = new Select(driver.findElement(By.id(LocatorValue)));
			drpdwn.selectByIndex(index);
		}

	}

	public static void captureStock(String LocatorType, String LocatorValue) throws Throwable {
		String StockNumber = "";
		if (LocatorType.equalsIgnoreCase("xpath")) {
			StockNumber = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			StockNumber = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			StockNumber = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		// writting stock number into notepad

		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		// memory allocation
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNumber);
		bw.flush();
		bw.close();
	}

	// verify stock number in stock table
	public static void stockTable() throws Throwable {
		// read path of notepad file
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		br.close();
		if (!(driver.findElement(By.xpath(property.getProperty("search-textbox"))).isDisplayed()))
			;
		driver.findElement(By.xpath(property.getProperty("search-panel"))).click();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).clear();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).sendKeys(exp_data);
		driver.findElement(By.xpath(property.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[8]/div/span/span"))
				.getText();
		Reporter.log(act_data + "...." + exp_data);
		try {
			Assert.assertEquals(exp_data, act_data, "Stock not found");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}

	public static void capturesup(String LocatorType, String LocatorValue) throws Throwable {
		String suppliernum = "";
		if (LocatorType.equalsIgnoreCase("xpath")) {
			suppliernum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			suppliernum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			suppliernum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}

		FileWriter fw = new FileWriter("./CaptureData/supliernum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suppliernum);
		bw.flush();
		bw.close();
	}

	public static void suppliertable() throws Throwable {
		FileReader fr = new FileReader(("./CaptureData/supliernum.txt"));
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		br.close();
		if (!(driver.findElement(By.xpath(property.getProperty("search-textbox"))).isDisplayed()))
			;
		driver.findElement(By.xpath(property.getProperty("search-panel"))).click();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).clear();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).sendKeys(exp_data);
		driver.findElement(By.xpath(property.getProperty("search-button"))).click();
		Thread.sleep(2000);

		String act_data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(act_data + "...." + exp_data);
		try {
			Assert.assertEquals(exp_data, act_data, "Stock not found");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}

	}

	public static void capturecus(String LocatorType, String LocatorValue) throws Throwable {
		String customernum = "";

		if (LocatorType.equalsIgnoreCase("xpath")) {
			customernum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("id")) {
			customernum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		if (LocatorType.equalsIgnoreCase("name")) {
			customernum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}

		FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(customernum);
		bw.flush();
		bw.close();
	}
	
	public static void customertable() throws Throwable {
		FileReader fr = new FileReader(("./CaptureData/customernumber.txt"));
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		br.close();
		if (!(driver.findElement(By.xpath(property.getProperty("search-textbox"))).isDisplayed()))
			;
		driver.findElement(By.xpath(property.getProperty("search-panel"))).click();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).clear();

		driver.findElement(By.xpath(property.getProperty("search-textbox"))).sendKeys(exp_data);
		driver.findElement(By.xpath(property.getProperty("search-button"))).click();
		Thread.sleep(2000);

		String act_data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(act_data + "...." + exp_data);
		try {
			Assert.assertEquals(exp_data, act_data, "customer not found");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static String generateDate() {
		Date date = new Date();
		 DateFormat format = new SimpleDateFormat("YYYY_MM_dd");
		 return format.format(date);
	}

}
