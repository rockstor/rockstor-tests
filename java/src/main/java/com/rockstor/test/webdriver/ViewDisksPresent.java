package com.rockstor.test.webdriver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.List;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.rockstor.test.util.RSProps;

public class ViewDisksPresent {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	

	}
	@Test
	public void testDisksPresent() throws Exception {
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

			// Select Disks from storage side bar
			WebElement diskNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'disks')]"));
			diskNav.click();
	
			WebElement disksRow = driver.findElement(
					By.xpath("//*[@id='disks-table']/thead/tr/th[(contains(text(),'Disk Name')) or (contains(text(),'Capacity'))" + "" +
							"or (contains(text(),'Pool')) ]"));
			assertTrue(disksRow.getText(),true);
			
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




