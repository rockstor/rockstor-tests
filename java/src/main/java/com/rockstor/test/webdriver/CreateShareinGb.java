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

public class CreateShareinGb {
	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}

	@Test
	public void testShareSizeGb() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			// Login 
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();

			
			WebElement storageNav = driver.findElement(By.id("storage_nav"));
			storageNav.click();
			
			// Add Pool with Raid 0
			WebElement poolsNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
			poolsNav.click();
			
			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			// Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(driver.findElement(
					By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(0);

			//Select Disks CheckBox
			WebElement diskCheckBox1 = driver.findElement(By.id("sdb"));
			diskCheckBox1.click();
			WebElement diskCheckBox2 = driver.findElement(By.id("sdc"));
			diskCheckBox2.click();

			// Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();

			//wait for pool1 to appear
			WebElement poolLink = driver.findElement(By.linkText("pool1"));
			poolLink.click();

			///// Create a share

			//Shares navigation bar
			WebElement sharesNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]"));
			sharesNav.click();

			//Add share
			WebElement addShareButton = driver.findElement(By.id("add_share"));
			addShareButton.click();

			WebElement shareName = driver.findElement(By.id("share_name"));
			shareName.sendKeys("share1");

			Select selectPoolDroplist = new Select(driver.findElement(By.id("pool_name")));   
			selectPoolDroplist.selectByIndex(0); 


			WebElement shareSize = driver.findElement(By.id("share_size"));
			shareSize.sendKeys("100");


			Select selectSizeDroplist = new Select(driver.findElement(By.id("size_format")));   
			selectSizeDroplist.selectByIndex(2); // 2 is GB


			//Submit button to create share
			WebElement shareSubmitButton = driver.findElement(By.id("create_share"));
			shareSubmitButton.click();
			
			//check for the text
			WebElement shareRowToCheckSize = driver.findElement(
					By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'100.00 Gb')]]"));
			assertTrue(shareRowToCheckSize.getText(),true);
			

			// Delete Share

			WebElement sharesNav1= driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]"));
			sharesNav1.click();

			WebElement shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'share1')]]"));
			WebElement deleteShare = shareRow.findElement(By.className("icon-trash"));
			deleteShare.click();

			//Browser Popup asking confirmation to delete 
			Alert alertDeleteShare = driver.switchTo().alert();
			alertDeleteShare.accept();
			
			// Delete Pool
			WebElement poolsNav1 = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
			poolsNav1.click();
			
			WebElement poolRow = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool1')]]"));
			WebElement deletePool = poolRow.findElement(By.className("icon-trash"));
			deletePool.click();

			//Browser Popup asking confirmation to delete 
			Alert alertDeletePool = driver.switchTo().alert();
			alertDeletePool.accept();
			
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

