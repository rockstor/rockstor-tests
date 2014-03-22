package com.rockstor.test.webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
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

public class RsWebUtil {
    public static WebDriver setupDriver() throws Exception {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(
                Integer.parseInt(RSProps.getProperty("waitTimeout")),
                TimeUnit.SECONDS);	
        return driver;
    }

    public static void login(WebDriver driver, String username, 
            String password) throws Exception {
        // load login page
        driver.get(RSProps.getProperty("RockstorVm"));
        // submit login form
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement( By.id("sign_in")).click();
    }

    public static void logout(WebDriver driver) throws Exception {
        driver.findElement(By.id("logout_user")).click();
    }

    public static void takeScreenshot(WebDriver driver,
            String filename) throws Exception {
        File screenshotFile = ((TakesScreenshot)driver)
            .getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile,
                new File(RSProps.getProperty("screenshotDir") 
                    + "/" + filename));
    }

    public static void createPool(WebDriver driver,
            String poolName, int raidLevel) throws Exception {

        // Select Storage from Navigation bar
        WebElement storageNav = driver.findElement(By.id("storage_nav"));
        storageNav.click();

        // Select pools from storage side bar
        WebElement poolNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'pools')]"));
        poolNav.click();

        WebElement addPool = driver.findElement(By.id("add_pool"));
        addPool.click();

        WebElement poolNameEl = driver.findElement(By.id("pool_name"));
        poolNameEl.sendKeys(poolName);

        //Raid Configuration Dropdown box 
        Select raidConfigDroplist = new Select(
                driver.findElement(By.id("raid_level")));   

        raidConfigDroplist.selectByIndex(1);

        // check number of free disks   
        WebElement disksTable = driver.findElement(By.id("disks-table"));
        List<WebElement> diskRows = disksTable.findElement(By.tagName("tbody"))
            .findElements(By.tagName("tr"));
        System.out.println("Number of free disks is " + diskRows.size());
        if (diskRows.size() < 2) {
            throw new Exception("Not enough free disks to create a " +
                    " Raid0 pool");
        }

        // select first two disks
        for (int i=0; i < 2; i++) {
            List<WebElement> cols = diskRows.get(i).findElements(
                    By.tagName("td"));
            WebElement cbox = cols.get(4).findElement(By.tagName("input"));
            cbox.click();
        }

        //Submit the form 
        WebElement createPool = driver.findElement(By.id("create_pool"));
        createPool.click();
    }


    public static void createShare(WebDriver driver, String poolName,
            String shareName, int sizeInGb) throws Exception {

        WebElement storageNav = driver.findElement(By.id("storage_nav"));
        storageNav.click();

        //Shares navigation bar
        WebElement sharesNav = driver.findElement(
                By.xpath(
                    "//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]"));
        sharesNav.click();

        // Add share
        WebElement addShareButton = driver.findElement(By.id("add_share"));
        addShareButton.click();

        // Enter share name
        WebElement shareNameEl = driver.findElement(By.id("share_name"));
        shareNameEl.sendKeys(shareName);

        // Select pool
        Select selectPoolDroplist = new Select(driver.findElement(
                    By.id("pool_name")));
        selectPoolDroplist.selectByValue(poolName);

        WebElement shareSize = driver.findElement(By.id("share_size"));
        shareSize.sendKeys(Integer.toString(sizeInGb));

        Select selectSizeDroplist = new Select(driver.findElement(
                    By.id("size_format")));
        selectSizeDroplist.selectByIndex(2); // 2 is GB

        //Submit button to create share
        WebElement shareSubmitButton = driver.findElement(By.id("create_share"));
        shareSubmitButton.click();

        WebElement shareRowToCheckSize = driver.findElement(
                By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.," 
                    + shareName + ")]]"));
        assertTrue(shareRowToCheckSize.getText(),true);
    }
    
    public static void createSambaExport(WebDriver driver, String shareName, 
            String adminUser, boolean browsable, boolean guestOk, 
            boolean readOnly, String comment) throws Exception {
			
        // Select Storage from Navigation bar
        driver.findElement(By.id("storage_nav")).click();

        // Select Samba from sidebar
        driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(@href,'samba-exports')]")).click();
        
        // Go to Samba export form
        driver.findElement(By.id("add-samba-export")).click();

        //Select Share from combo box
        Select shareCombobox = new Select(driver.findElement(By.id("share")));
        shareCombobox.selectByValue(shareName);

        //Admin Users
        WebElement adminUserEl = driver.findElement(By.id("admin_users"));
        adminUserEl.sendKeys(adminUser);

        // Browsable
        Select browsableEl = new Select (driver.findElement(By.id("browsable")));
        browsableEl.selectByIndex(0);

        // Guest Ok
        Select guestOkEl = new Select(driver.findElement(By.id("guest_ok")));   
        guestOkEl.selectByIndex(1);

        // Read only
        Select readOnlyEl = new Select(driver.findElement(By.id("read_only")));   
        readOnlyEl.selectByIndex(1);

        //Comment
        WebElement commentEl = driver.findElement(By.id("comment"));
        commentEl.sendKeys("");

        WebElement saveButton = driver.findElement(By.id("create-samba-export"));
        saveButton.click();

    }

}


