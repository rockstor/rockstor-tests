package com.rockstor.test.webdriver;

import java.io.File;
import java.util.List;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rockstor.test.util.RSProps;

public class SambaExport {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void sambaExport() throws Exception {
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

			// Select System from Navigation bar
			WebElement systemNav = driver.findElement(By.id("storage_nav"));
			systemNav.click();

			// Select Samba from system side bar
			WebElement usersNav = driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(@href,'samba-exports')]"));
			usersNav.click();

			// Wait for add samba-export button to appear
			WebElement myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("add-samba-export")));

			WebElement addSambaBtn = driver.findElement(By.id("add-samba-export"));
			addSambaBtn.click();

			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("create-samba-export")));

			// Create Samba Export 

			// Fillup form

			//Select Share from combo box
			Select shareCombobox = new Select(driver.findElement(By.id("share")));
			shareCombobox.selectByValue("share1");
			
			//Admin Users
			WebElement users = driver.findElement(By.id("admin_users"));
			users.sendKeys("rockstor");
			
			// Browsable
			Select browsable = new Select (driver.findElement(By.id("browsable")));
			browsable.selectByIndex(0);

			// Guest Ok
			Select guestOk = new Select(driver.findElement(By.id("guest_ok")));   
			guestOk.selectByIndex(1);

			// Read only
			Select readOnly = new Select(driver.findElement(By.id("read_only")));   
			readOnly.selectByIndex(1);

			//Comment
			WebElement comment = driver.findElement(By.id("comment"));
			comment.sendKeys("");

			WebElement saveButton = driver.findElement(By.id("create-samba-export"));
			saveButton.click();
			
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
