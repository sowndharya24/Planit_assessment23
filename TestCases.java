package assessment;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;

public class TestCases extends BaseClass {
	@Test
	public void VerifyMsgError() throws InterruptedException {
		
		test=extent.createTest("Test Case 1","Navigate to contact page and verify error messages with positive and negative scenarios");
		
		driver.findElement(By.xpath("//a[@href='#/contact']")).click();
		driver.findElement(By.linkText("Submit")).click();
		String alertXpath = "//div[contains(@class,'alert')]";
		WebElement alertEle=driver.findElement(By.xpath(alertXpath));

		//getting error messages 
		String alertMsg=alertEle.getText();
		System.out.println("Error msg is "+alertMsg);
		String foreNameErrMsg=driver.findElement(By.id("forename-err")).getText();
		String emailErrMsg=driver.findElement(By.id("email-err")).getText();
		String messageErrMsg=driver.findElement(By.id("message-err")).getText();

		//verifying error messages
		AssertJUnit.assertEquals(foreNameErrMsg, "Forename is required");
		AssertJUnit.assertEquals(emailErrMsg, "Email is required");
		AssertJUnit.assertEquals(messageErrMsg, "Message is required");


		//populate mandate fields
		driver.findElement(By.id("forename")).sendKeys("Sowndharya");
		driver.findElement(By.id("email")).sendKeys("sowndharya@abc."); //invalid email id
		driver.findElement(By.id("message")).sendKeys("hi test message1");

		emailErrMsg=driver.findElement(By.id("email-err")).getText();

		AssertJUnit.assertEquals(emailErrMsg, "Please enter a valid email");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("sowndharya@abc.com");

		Boolean isPresent = isElementPresent(driver, alertXpath);

		String alertMsg2="";
		if (isPresent) {
			WebElement alertElement=driver.findElement(By.xpath(alertXpath));

			alertMsg2=alertElement.getText();
		}

		//System.out.println(errorMsg2+" is the message after filling mandate fields.");		
		AssertJUnit.assertEquals(alertMsg2,"We welcome your feedback - tell it how it is.");
		Assert.assertTrue(true);
		driver.close();

	}
	@Test(invocationCount=5)
	public void VerifySuccessMsg() throws InterruptedException{
		
		test=extent.createTest("Test Case 2","Navigate to contact page and verify success message upon filling mandatory details");
		
		driver.findElement(By.xpath("//a[@href='#/contact']")).click();
		// TODO Auto-generated method stub

		String foreName="Sowndharya";
		String emailId="sowndharya@abc.com";
		String message="hi test message1";
		//populate mandate fields
		driver.findElement(By.id("forename")).sendKeys(foreName);
		driver.findElement(By.id("email")).sendKeys(emailId);
		driver.findElement(By.id("message")).sendKeys(message);

		//click submit
		driver.findElement(By.linkText("Submit")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h1[text()='Sending Feedback']")));

		//fetch alert message
		String alertXpath = "//div[contains(@class,'alert')]";
		WebElement alertEle=driver.findElement(By.xpath(alertXpath));
		String alertMsg=alertEle.getText();
		System.out.println(alertMsg);

		AssertJUnit.assertEquals(alertMsg,"Thanks "+foreName+", we appreciate your feedback.");
		Assert.assertTrue(true);
		driver.close();

	}

	@Test
	public void AddToCart() throws InterruptedException{
		
		test=extent.createTest("Test Case 3","Buy toys by adding to cart and verifying their prices");
		
		// TODO Auto-generated method stub
		driver.findElement(By.xpath("//a[contains(@class,'btn btn-success')]")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'products')]")));

		String stuffedFrogCost = driver.findElement(By.xpath("//h4[text()='Stuffed Frog']/parent::div/p/span")).getText();
		Integer stuffedFrogQty=2;
		Double stuffedFrogTotal=convertCostString(stuffedFrogCost,stuffedFrogQty);
		String totalStuffedFrog=convertDoubleToString(stuffedFrogTotal);
		//adding to cart 
		addToCart("Stuffed Frog",stuffedFrogQty);

		String fluffyBunnyCost=driver.findElement(By.xpath("//h4[text()='Fluffy Bunny']/parent::div/p/span")).getText();
		Integer fluffyBunnyQty=5;
		Double fluffyBunnyTotal=convertCostString(fluffyBunnyCost, fluffyBunnyQty);
		String totalFluffyBunny=convertDoubleToString(fluffyBunnyTotal);
		addToCart("Fluffy Bunny",fluffyBunnyQty);


		String valentineBearCost=driver.findElement(By.xpath("//h4[text()='Valentine Bear']/parent::div/p/span")).getText();
		Integer valentineBearQty=3;
		Double valentineBearTotal=convertCostString(valentineBearCost, valentineBearQty);
		String totalValentineBear=convertDoubleToString(valentineBearTotal);
		addToCart("Valentine Bear", valentineBearQty);

		//go to cart page
		driver.findElement(By.xpath("//a[@href='#/cart']")).click();
		//get subtotal values
		String stuffedFrogSubTotal= driver.findElement(By.xpath("//tr[1]/td[4]")).getText();
		String fluffyBunnySubTotal= driver.findElement(By.xpath("//tr[2]/td[4]")).getText();
		String valentineBearSubTotal= driver.findElement(By.xpath("//tr[3]/td[4]")).getText();

		AssertJUnit.assertEquals("$"+totalStuffedFrog, stuffedFrogSubTotal);
		AssertJUnit.assertEquals("$"+totalFluffyBunny, fluffyBunnySubTotal);
		AssertJUnit.assertEquals("$"+totalValentineBear, valentineBearSubTotal);

		System.out.println("Subtotal for each product verified");

		//get toy prices from cart page
		String stuffedFrogPrice= driver.findElement(By.xpath("//tr[1]/td[2]")).getText();
		String fluffyBunnyPrice= driver.findElement(By.xpath("//tr[2]/td[2]")).getText();
		String valentineBearPrice= driver.findElement(By.xpath("//tr[3]/td[2]")).getText();

		AssertJUnit.assertEquals(stuffedFrogCost, stuffedFrogPrice);
		AssertJUnit.assertEquals(fluffyBunnyCost, fluffyBunnyPrice);
		AssertJUnit.assertEquals(valentineBearCost, valentineBearPrice);

		System.out.println("Product price verified");
		//get total value from cart page
		String total=driver.findElement(By.xpath("//strong[@class='total ng-binding']")).getText().substring(7);
		AssertJUnit.assertEquals(Double.toString(stuffedFrogTotal+fluffyBunnyTotal+valentineBearTotal), total);
		System.out.println("Total verified");
		Assert.assertTrue(true);
		driver.close();

	}

	public void addToCart(String toyName, Integer toyQty) {
		// TODO Auto-generated method stub
		for (int i=0; i <toyQty; i ++) {
			driver.findElement(By.xpath("//h4[text()='"+toyName+"']/parent::div/p/a")).click();
		}
	}

	public Double convertCostString(String toyCost,Integer toyQty) {
		// TODO Auto-generated method stub
		String splitDollar=toyCost.substring(1);
		Double costToy=Double.parseDouble(splitDollar);
		Double toyTotal=costToy*toyQty;
		return toyTotal;
	}
	public String convertDoubleToString(Double doubleToy) {
		// TODO Auto-generated method stub
		String totalToy=Double.toString(doubleToy);
		return totalToy;
	}

	public static Boolean isElementPresent(ChromeDriver driver, String xpath) {
		Boolean isPresent = false;
		try {
			driver.findElement(By.xpath(xpath));
			//reportStep("Error message verified","pass");
			return true;
		} catch (NoSuchElementException e) {
			//reportStep("No element found","fail");

			return isPresent;
		}
	}


}
