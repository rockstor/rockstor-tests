package com.rockstor.test.webdriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.io.File;
import java.io.FileInputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select; // Dropdown menu
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;// Explicit Waits
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.apache.commons.io.FileUtils; // Screenshots
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot; 

import com.rockstor.test.util.RSProps;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class CreateSnapshotFromShares {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void testUserNoWebUI() throws Exception {
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
			WebElement systemNav = driver.findElement(By.id("storage_nav"));
			systemNav.click();

			
			// Select shares from storage side bar
			WebElement sharesNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]"));
			sharesNav.click();

			WebElement myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(By.id("add_share")));

			
			// Share link
			WebElement shareLink = driver.findElement(By.linkText("share3"));
			shareLink.click();

			// wait for newshare page to load
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(By.id("js-delete")));

			//Select Snapshot from navigation

			WebElement snapNav = driver.findElement(By.xpath("//div/ul/li/a[contains(text(),'Snapshots')]"));
			snapNav.click();

			// Wait for snapshot page to load
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(By.id("js-snapshot-add")));

			// Create snapshot			
			WebElement snapshotButton = driver.findElement(By.id("js-snapshot-add"));
			snapshotButton.click();

			///Fill up Snapshot form

			//Snapshot Name
			WebElement snapshotName = driver.findElement(By.id("snapshot-name"));
			snapshotName.sendKeys("newsnapshot");

			// Check box to make visible for users
			WebElement makeVisible = driver.findElement(By.cssSelector("input[id='snapshot-visible']"));
			makeVisible.click();

			// Submit button
			WebElement createSnapshot = driver.findElement(By.id("js-snapshot-save"));
			createSnapshot.click();

			// Wait for snapshot page to load
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(By.id("js-snapshot-add")));

			//verify that snapshot is created
			List <WebElement> verifySnapCreated = driver.findElements(
					By.xpath("//*[@id='snapshots-table']/tbody/tr[td[contains(text(),'newsnapshot')]]"));
			assertEquals(verifySnapCreated.size(), 1);


			//Logout
			WebElement logoutSubmit = driver.findElement(
					By.id("logout_user"));

			logoutSubmit.click();


		}
		//catch any exceptions by taking screenshots
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


















