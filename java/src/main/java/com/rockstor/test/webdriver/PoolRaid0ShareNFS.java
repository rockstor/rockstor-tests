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
import org.openqa.selenium.support.ui.Select; // Dropdown menu
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.List;
import com.rockstor.test.util.RSProps;

public class PoolRaid0ShareNFS {
    private static WebDriver driver;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(
                Integer.parseInt(RSProps.getProperty("waitTimeout")), 
                TimeUnit.SECONDS);	
    }

    @Test
    public void testShareNFS() throws Exception {
        try{

            driver.get(RSProps.getProperty("RockstorVm"));

            // Login 
            WebElement username = driver.findElement(By.id("inputUsername"));
            username.sendKeys("admin");

            WebElement password = driver.findElement(By.id("inputPassword"));
            password.sendKeys("admin");

            WebElement submit = driver.findElement(By.id("sign_in"));
            submit.click();

            // Add Pool with Raid 0
			WebElement poolsNav = driver.findElement(By.id("pools_nav"));
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
            
            ///// Create a share
			
            WebElement poolLink = driver.findElement(By.linkText("pool1"));
			poolLink.click();

			//Add share
			WebElement addShareButton = driver.findElement(By.id("add_share"));
			addShareButton.click();

			WebElement shareName = driver.findElement(By.id("share_name"));
			shareName.sendKeys("share1");
			
			Select selectPoolDroplist = new Select(driver.findElement(
                        By.id("pool_name")));   
			selectPoolDroplist.selectByIndex(0); 
			
			WebElement shareSize = driver.findElement(By.id("share_size"));
			shareSize.sendKeys("100"); 

			Select selectSizeDroplist = new Select(driver.findElement(
                        By.id("size_format")));   
			selectSizeDroplist.selectByIndex(0);//Index 0 is KB
			
            // Submit button to create share
			WebElement shareSubmitButton = driver.findElement(
                    By.id("create_share"));
			shareSubmitButton.click();
		
            ///// Export NFS
			
            // Share link
			WebElement shareLink = driver.findElement(By.linkText("share1"));
			shareLink.click();
			
			WebElement addNfsExport = driver.findElement(By.id("add-export"));
			addNfsExport.click();
			
			//Host
			WebElement host = driver.findElement(By.id("host_str"));
			host.sendKeys("host1");
			
		    // writable
			Select writable = new Select (driver.findElement(By.id("mod_choice")));
			writable.selectByIndex(0);
			
			// Sync
			Select sync = new Select(driver.findElement(By.id("sync_choice")));   
			sync.selectByIndex(0);
						
			//Actions
			WebElement saveButton = driver.findElement(By.id("save-new"));
			saveButton.click();
		
			//WebElement element1 = driver.findElement(By.id("nfs-exports-table"));
			List<WebElement> rowCollection = driver.findElements(By.xpath("//*[@id='nfs-exports-table']/tbody/tr/td[contains(.,'host1')]"));
		    
            assertTrue(rowCollection.size() > 0);

            // Delete NFS export
			WebElement nfsRow = driver.findElement(By.xpath("//*[@id='nfs-exports-table']/tbody/tr/td[contains(.,'host1')]"));
            WebElement deleteNFS = nfsRow.findElement(By.xpath("//td/button[contains(@class,'delete-row')]"));
            deleteNFS.click();
            
			//Browser Popup asking confirmation to delete 
			Alert alertDeleteNfsExport = driver.switchTo().alert();
			alertDeleteNfsExport.accept();
            
            // Delete Share
			WebElement sharesNav = driver.findElement(By.id("shares_nav"));
			sharesNav.click();
			
            WebElement shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr/td[contains(.,'share1')]"));
            WebElement deleteShare = shareRow.findElement(By.xpath("//td/button[contains(@data-name,'share1') and contains(@data-action,'delete')]"));
            deleteShare.click();
            

			//Browser Popup asking confirmation to delete 
			Alert alertDeleteShare = driver.switchTo().alert();
			alertDeleteShare.accept();


            // Delete Pool
			poolsNav = driver.findElement(By.id("pools_nav"));
			poolsNav.click();
            
            WebElement poolRow = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr/td[contains(.,'pool1')]"));
            WebElement deletePool = poolRow.findElement(By.xpath("//td/button[contains(@data-name,'pool1') and contains(@data-action,'delete')]"));
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



