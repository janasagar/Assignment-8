package testsuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class heroku {
	WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) {
    	System.out.println("Browser name is: "+browser);
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        }
        ExtentSparkReporter report = new ExtentSparkReporter("ExtentReports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(report);
        test = extent.createTest("Login Test on " + browser, "Test login functionality on " + browser);
        
        
    }
    @Test
    public void login() throws InterruptedException {
    	try {
    		driver.get("https://the-internet.herokuapp.com/login");
        	driver.manage().window().maximize();
        	Thread.sleep(4000);
        	driver.findElement(By.id("username")).sendKeys("tomsmith");
        	driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        	driver.findElement(By.xpath("//button[@type='submit']")).click();
        	Thread.sleep(2000);
        	String currenturl = driver.getCurrentUrl();
        	boolean verify = currenturl.contains("secure");
        	Assert.assertTrue(verify);
        	test.pass("Login successful. Current URL contains 'secure'.");
			
		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
            throw e;
			
		}
    }
    @AfterTest
    public void close() {
    	driver.quit();
    	test.info("Browser closed.");
    	extent.flush();
    }

}
