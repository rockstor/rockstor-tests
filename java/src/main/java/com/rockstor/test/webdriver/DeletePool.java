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

public class DeletePool {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}

	@Test
	public void testdeletePool() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			// Login 
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();

			// Delete Pool

			// Select Storage from Navigation bar
			WebElement storageNav = driver.findElement(By.id("storage_nav"));
			storageNav.click();

			// Select pools from storage side bar
			WebElement poolsNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
			poolsNav.click();
			
			
			WebElement poolRow = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool1')]]"));
			WebElement deletePool = poolRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
			deletePool.click();

			//Browser Popup asking confirmation to delete 
			Alert alertDeletePool = driver.switchTo().alert();
			alertDeletePool.accept();

			WebElement textVerify = driver.findElement(
					By.xpath("//h4[text()='No pools have been created.']"));
			assertTrue(textVerify.getText(),true);


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

