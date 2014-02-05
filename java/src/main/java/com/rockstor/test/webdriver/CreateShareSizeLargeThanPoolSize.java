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

public class CreateShareSizeLargeThanPoolSize {
	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}
	
	@Test
    public void  testCreateShareSizeLargeThanPoolSize() throws IOException {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));
			
		
			// User Login Input Forms
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();
			
			WebElement storageNav = driver.findElement(By.id("storage_nav"));
			storageNav.click();

			// Select Pools from Navigation bar
			WebElement poolsNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
			poolsNav.click();

			//Explicit Wait for Pools page to load
			//WebElement myWaitElement1 = (new WebDriverWait(driver, 150))
			//		.until(ExpectedConditions.elementToBeClickable(By.id("add_pool")));


			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			//Explicit Wait for CreatePools page. 
			//WebElement myWaitElement2 = (new WebDriverWait(driver, 150))
			//		.until(ExpectedConditions.elementToBeClickable(By.id("create_pool")));


			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			//Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(driver.findElement(By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(0);

			//Select Disks CheckBox
			WebElement diskCheckBox1 = driver.findElement(By.id("sdb"));
			diskCheckBox1.click();
			WebElement diskCheckBox2 = driver.findElement(By.id("sdc"));
			diskCheckBox2.click();


			//Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();

			//WebElement myWaitElement3 = (new WebDriverWait(driver, 150))
			//		.until(ExpectedConditions.elementToBeClickable(By.id("delete_pool_pool1")));

			WebElement poolLink = driver.findElement(By.linkText("pool1"));
			poolLink.click();


			//WebElement myWaitElement4 = (new WebDriverWait(driver, 150))
			//		.until(ExpectedConditions.elementToBeClickable(By.id("resize-pool-popup")));

			//Add share
			WebElement addShareButton = driver.findElement(By.id("add_share"));
			addShareButton.click();


			WebElement myWaitElement5 = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(By.id("create_share")));


			WebElement shareName = driver.findElement(By.id("share_name"));
			shareName.sendKeys("share1");
			

			Select selectPoolDroplist = new Select(driver.findElement(By.id("pool_name")));   
			selectPoolDroplist.selectByIndex(0); 
			

			WebElement shareSize = driver.findElement(By.id("share_size"));
			shareSize.sendKeys("100"); 
			

			Select selectSizeDroplist = new Select(driver.findElement(By.id("size_format")));   
			selectSizeDroplist.selectByIndex(2);//Index 0 is KB
			

            //Submit button to create share
			WebElement shareSubmitButton = driver.findElement(By.id("create_share"));
			shareSubmitButton.click();
			

			//check for the test
			WebElement shareRowToCheckSize = driver.findElement(
					By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'100.00 Gb')]]"));
			assertTrue(shareRowToCheckSize.getText(),true);
			
		    // Wait for shares page to load up
			//WebElement myWaitElement6 = (new WebDriverWait(driver, 150))
				//	.until(ExpectedConditions.elementToBeClickable(By.id("add_share")));
			
			//Delete share
			
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
		//catch any exceptions by taking screenshots
		catch(Exception e){

			System.out.println(e.toString());

			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile,new File("/home/priya/rockstor-tests/webdriver/java/ScreenShots/SharefromPoolsPage.png"));

		}
		
	

		//click on logout
		WebElement logoutSubmit = driver.findElement(By.id("logout_user"));

		logoutSubmit.click();
		driver.close();

	}
}












