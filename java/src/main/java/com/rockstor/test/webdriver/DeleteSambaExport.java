package com.rockstor.test.webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.rockstor.test.util.RSProps;

public class DeleteSambaExport {
	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	

	}
	@Test
	public void testDeleteSamba() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			//User Login Input Forms
			WebElement username = driver.findElement(
					By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(
					By.id("password"));
			password.sendKeys("admin");

			WebElement submitButton = driver.findElement(
					By.id("sign_in"));
			submitButton.click();

			// Select Storage from Navigation bar
			WebElement storageNav = driver.findElement(By.id("storage_nav"));
			storageNav.click();

			///Export Samba

			// Select Samba from storage side bar
			WebElement sambaNav = driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(text(),'Samba')]"));
			sambaNav.click();

			WebElement sambaRow = driver.findElement(By.xpath("//*[@id='samba-table']/tbody/tr[td[contains(.,'share1')]]"));
			WebElement deleteSamba = sambaRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
			deleteSamba.click();


			Alert alertDeleteSamba = driver.switchTo().alert();
			alertDeleteSamba.accept();

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

