package com.rockstor.test.webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

        // wait till pools page is loaded
        addPool = driver.findElement(By.id("add_pool"));
        // verify that row with poolName exists
        List <WebElement> poolRow = driver.findElements(
                By.xpath("//*[@id='pools-table']/tbody/tr[td[a[contains(text(),'" 
                    + poolName + "')]]]"));
        assertEquals(poolRow.size(),1);
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
                By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'" 
                    + shareName + "')]]"));
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
        readOnlyEl.selectByIndex(0);

        //Comment
        WebElement commentEl = driver.findElement(By.id("comment"));
        commentEl.sendKeys("");

        WebElement saveButton = driver.findElement(By.id("create-samba-export"));
        saveButton.click();

        // wait till samba page is loaded
        driver.findElement(By.id("add-samba-export"));

        // verify that row with poolName exists
        List <WebElement> sambaRows = driver.findElements(
                By.xpath("//*[@id='samba-table']/tbody/tr[td[text() = '" 
                    + shareName + "']]"));
        assertEquals(sambaRows.size(),1);
    }
    
    public static void createNfsExport(WebDriver driver, String[] shares, 
            String hostStr, String adminHost, boolean writable, boolean sync) {

        // Select System from Navigation bar
        driver.findElement(By.id("storage_nav")).click();

        // Select NFS from sidebar
        driver.findElement(By.xpath("//div[@class='subnav']/ul/li/a[contains(@href,'nfs-exports')]")).click();
        
        // Go to add nfs export form
        driver.findElement(By.id("add-nfs-export")).click();
        // Create NFS Export 

        //Select Share from combo box
        Select shareCombobox = new Select(driver.findElement(By.id("shares")));
        for (int i=0; i < shares.length; i++) {
            shareCombobox.selectByValue(shares[i]);
        }

        // Host String
        WebElement hostStrEl = driver.findElement(By.id("host_str"));
        hostStrEl.sendKeys(hostStr);

        // Admin Host 
        WebElement adminHostEl = driver.findElement(By.id("admin_host"));
        adminHostEl.sendKeys(adminHost);
        
        // Writable
        Select writableEl = new Select(driver.findElement(By.id("mod_choice")));   
        if (writable) {
            writableEl.selectByValue("rw");
        } else {
            writableEl.selectByValue("ro");
        }

        // Sync
        Select syncEl = new Select(driver.findElement(By.id("sync_choice")));   
        if (sync) {
            syncEl.selectByValue("sync");
        } else {
            syncEl.selectByValue("async");
        }

        WebElement submitButton = driver.findElement(By.id("create-nfs-export"));
        submitButton.click();

    }
    
    public static void createSnapshot(WebDriver driver, String shareName, 
            String snapName, boolean visible) {
			
        // Select Storage from Navigation bar
        driver.findElement(By.id("storage_nav")).click();

        // Select shares from storage side bar
        driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'shares')]")).click();

        // Share link
        driver.findElement(By.linkText(shareName)).click();

        //Select Snapshot from navigation
        driver.findElement(By.xpath("//div/ul/li/a[contains(text(),'Snapshots')]")).click();

        // Create snapshot			
        WebElement snapshotButton = driver.findElement(By.id("js-snapshot-add"));
        snapshotButton.click();

        //Snapshot Name
        WebElement snapshotName = driver.findElement(By.id("snapshot-name"));
        snapshotName.sendKeys(snapName);

        // Check box to make visible for users
        if (visible) {
            WebElement makeVisible = driver.findElement(By.cssSelector("input[id='snapshot-visible']"));
            makeVisible.click();
        }

        // Submit button
        WebElement createSnapshot = driver.findElement(By.id("js-snapshot-save"));
        createSnapshot.click();

        //verify that snapshot is created
        List <WebElement> verifySnapCreated = driver.findElements(
                By.xpath("//*[@id='snapshots-table']/tbody/tr[td[contains(text(),'" + snapName + "')]]"));
        assertEquals(verifySnapCreated.size(), 1);

    }


}


