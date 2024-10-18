package commonFunctions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

}
