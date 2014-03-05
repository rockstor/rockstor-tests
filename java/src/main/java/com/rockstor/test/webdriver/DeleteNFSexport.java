package com.rockstor.test.webdriver;

import static org.junit.Assert.assertTrue;

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
import org.openqa.selenium.support.ui.Select;

import com.rockstor.test.util.RSProps;

public class DeleteNFSexport {

	 private static WebDriver driver;

	    @BeforeClass
	    public static void setUpBeforeClass() throws Exception {
	        driver = new FirefoxDriver();
	        driver.manage().timeouts().implicitlyWait(
	                Integer.parseInt(RSProps.getProperty("waitTimeout")), 
	                TimeUnit.SECONDS);	
	    
	    }
	        @Test
	        public void testDeleteNFS() throws Exception {
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

	    			///Export NFS
	    			
	    			// Select NFS from storage side bar
	    			WebElement nfsNav = driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(text(),'NFS')]"));
	    			nfsNav.click();
	    			
	    			WebElement nfsRow = driver.findElement(By.xpath("//*[@id='nfs-exports-table']/tbody/tr[td[contains(.,'192.')]]"));
	    			WebElement deleteNfs = nfsRow.findElement(By.xpath("td/a/i[contains(@class,'icon-trash')]"));
	    			deleteNfs.click();
	    			
	    			
	    			Alert alertDeleteNfs = driver.switchTo().alert();
	    			alertDeleteNfs.accept();
	    			
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
