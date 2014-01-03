package com.rockstor.test.webdriver;
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
import java.util.Properties;


public class AddPoolRaid0with5Disks {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void testPool() throws Exception {
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


			// Select pools from storage side bar
			WebElement poolNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
			poolNav.click();

			//Explicit Wait for Create Pool button to load
			WebElement myWaitElement1 = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("add_pool")));

			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			//Explicit Wait for Create Pool page. 
			WebElement myWaitElement2 = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("create_pool")));


			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			//Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(
					driver.findElement(By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(1);
			
			//Select Disks CheckBox
            WebElement diskCheckBox1 = driver.findElement(By.id("sdb"));
            diskCheckBox1.click();
            WebElement diskCheckBox2 = driver.findElement(By.id("sdc"));
            diskCheckBox2.click();
            WebElement diskCheckBox3 = driver.findElement(By.id("sdd"));
            diskCheckBox3.click();
            WebElement diskCheckBox4 = driver.findElement(By.id("sde"));
            diskCheckBox4.click();
            WebElement diskCheckBox5 = driver.findElement(By.id("sdf"));
            diskCheckBox5.click();
            
            

			//Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();
			

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








