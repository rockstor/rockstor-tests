package com.rockstor.test.webdriver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
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
import java.io.IOException;
import java.util.Properties;

import com.rockstor.test.util.RSProps;

public class AddPoolRaid0with0Disks {
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
                    By.id("inputUsername"));
			username.sendKeys("admin");

            WebElement password = driver.findElement(
                    By.id("inputPassword"));
			password.sendKeys("admin");

			WebElement submitButton = driver.findElement(
                                        By.id("sign_in"));
			submitButton.click();
			
			// Select Pools from Navigation bar
			WebElement poolsNav = driver.findElement(By.id("pools_nav"));
			poolsNav.click();

			//Explicit Wait for Pools page to load
            WebElement myWaitElement1 = (new WebDriverWait(driver, 150))
                .until(ExpectedConditions.elementToBeClickable(
                            By.id("add_pool")));

			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			//Explicit Wait for CreatePools page. 
			WebElement myWaitElement2 = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
                                By.id("create_pool")));


			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			//Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(
                    driver.findElement(By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(0);

			//Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();
			
            WebElement myWaitElement3 = (new WebDriverWait(driver, 150))
                .until(ExpectedConditions.elementToBeClickable(
                            By.id("delete_pool_pool1")));

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








