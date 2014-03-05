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

public class NFSExport {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void nfsExport() throws Exception {
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

			// Select NFS from system side bar
			WebElement usersNav = driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(@href,'nfs-exports')]"));
			usersNav.click();

			// Wait for add nfs-export button to appear
			WebElement myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("add-nfs-export")));

			WebElement addSambaBtn = driver.findElement(By.id("add-nfs-export"));
			addSambaBtn.click();

			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("create-nfs-export")));

			// Create NFS Export 

			// Fillup form

			//Select Share from combo box
			Select shareCombobox = new Select(driver.findElement(By.id("shares")));
			shareCombobox.selectByValue("share2");

			// Host String
			WebElement hostString = driver.findElement(By.id("host_str"));
			hostString.sendKeys("192.168.1.20");

			// Writable
			Select guestOk = new Select(driver.findElement(By.id("mod_choice")));   
			guestOk.selectByIndex(1);

			// Sync
			Select readOnly = new Select(driver.findElement(By.id("sync_choice")));   
			readOnly.selectByIndex(0);

			submitButton = driver.findElement(By.id("create-nfs-export"));
			submitButton.click();
			
			//wait until the page loads
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("add-nfs-export")));
			
		/*	WebElement element1 = driver.findElement(By.id("nfs-exports-table"));
			List<WebElement> rowCollection = element1.findElements(By.xpath("//*[@id='nfs-exports-table']/tbody/tr/td[contains(.,"+hostString+")]"));
			
			
			if(rowCollection.size() > 0)
			{
				assert true;
			}
			
			else {
				
				assert false;
			}
			
			*/

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


