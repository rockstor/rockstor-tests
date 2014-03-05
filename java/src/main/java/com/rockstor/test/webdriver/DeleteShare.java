package com.rockstor.test.webdriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.io.FileUtils; // Screenshots
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select; // Dropdown menu
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.List;
import com.rockstor.test.util.RSProps;


public class DeleteShare {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}

	@Test
	public void testDeleteShare() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			// Login 
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();

			// Select Storage from Navigation bar
			WebElement storageNav = driver.findElement(By.id("storage_nav"));
			storageNav.click();

			// Select shares from storage side bar
			WebElement sharesNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]"));
			sharesNav.click();

			//share1
			WebElement shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'share1')]]"));
			WebElement deleteShare = shareRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
			deleteShare.click();

			//Browser Popup asking confirmation to delete 
			Alert alertDeleteShare = driver.switchTo().alert();
			alertDeleteShare.accept();

			//share2
			shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'share2')]]"));
			deleteShare = shareRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
			deleteShare.click();

			//Browser Popup asking confirmation to delete 
			alertDeleteShare = driver.switchTo().alert();
			alertDeleteShare.accept();

			//share3
			shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'share3')]]"));
			deleteShare = shareRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
			deleteShare.click();

			//Browser Popup asking confirmation to delete 
			alertDeleteShare = driver.switchTo().alert();
			alertDeleteShare.accept();


			// verify if all shares are deleted
			WebElement verifyTextPresent = driver.findElement(
					By.xpath("//div/h4[text()='No shares have been created']"));
			assertTrue(verifyTextPresent.getText(), true);

			// Logout 
			WebElement logoutSubmit = driver.findElement(
					By.id("logout_user"));

			logoutSubmit.click();

		}
		catch(Exception e){
			File screenshotFile = ((TakesScreenshot)driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile,
					new File(RSProps.getProperty("screenshotDir") 
							+ "/" + this.getClass().getName()+".png"));
			throw e;

		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}
}


