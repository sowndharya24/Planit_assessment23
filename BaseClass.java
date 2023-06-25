package assessment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseClass {
	public ChromeDriver driver;
	ExtentHtmlReporter repo;
	ExtentReports extent;
	static ExtentTest test;
	String testCase,testDescription,testAuthor,testCategory;

	@BeforeTest
	public void startReport() {
		repo=new ExtentHtmlReporter("./report/result.html");
		extent=new ExtentReports();
		extent.attachReporter(repo);
		repo.config().setDocumentTitle("Automation Test report");
		repo.config().setReportName("Report");
		repo.setAppendExisting(true);
		
	
		
	}

	@BeforeMethod
	public void precondition() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://jupiter.cloud.planittesting.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		}
	
	@AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getTestName());
        }
        else {
            test.log(Status.SKIP, result.getTestName());
        }
        
        
		
	}
	

	@AfterTest
	public void stopReport() {
		extent.flush(); 	
	}
	}
