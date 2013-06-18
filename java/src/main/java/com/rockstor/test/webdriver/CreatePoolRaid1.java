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


public class CreatePoolRaid1 {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}

	@Test
	public void testPoolRaid1() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			// Login 
			WebElement username = driver.findElement(By.id("inputUsername"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("inputPassword"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();
			
		
			// Add Pool with Raid 1
			WebElement poolsNav = driver.findElement(By.id("pools_nav"));
			poolsNav.click();

			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			// Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(driver.findElement(
					By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(1);

			//Select Disks CheckBox
			WebElement diskCheckBox1 = driver.findElement(By.id("sdd"));
			diskCheckBox1.click();
			WebElement diskCheckBox2 = driver.findElement(By.id("sde"));
			diskCheckBox2.click();

			// Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();
			
			WebElement poolRowToCheckSize = driver.findElement(
					By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool1')]]"));
			assertTrue(poolRowToCheckSize.getText(),true);
			
				
			//Delete Pool
			
			WebElement poolRow = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool1')]]"));
			WebElement deletePool = poolRow.findElement(By.xpath("td/button[contains(@data-name,'pool1') and contains(@data-action,'delete')]"));
			deletePool.click();

			
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



