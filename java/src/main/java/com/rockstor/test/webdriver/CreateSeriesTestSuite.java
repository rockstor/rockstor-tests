package com.rockstor.test.webdriver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.rockstor.test.util.RsUtil;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

//@RunWith(Suite.class)
//@SuiteClasses({AddPoolRaid0with2Disks.class,CreateShareinGb.class,
	//SambaExport.class,NFSExport.class,CreateSnapshotFromShares.class
	//})

public class CreateSeriesTestSuite extends RsTestBase {
	
    @Test
	public void testCreate() throws Exception {
        // Pool
        String poolName = "pool_" + RsUtil.generateRandomString(10);
        int raidLevel = 0;
        // Share
        String shareName = "share_" + RsUtil.generateRandomString(10);
        int shareSizeGb = 1;
        // Samba
        String adminUser = "admin";
        boolean browsable = true;
        boolean guestOk = false;
        boolean readOnly = false;
        String comment = "Samba share";
        // NFS
        // Snapshot 
        String snapName = "snap_" + RsUtil.generateRandomString(10);
        boolean snapVisible = true;

		try {
            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));
            
            // Create Pool
		    RsWebUtil.createPool(driver, poolName, raidLevel);	

            // Create Share
            RsWebUtil.createShare(driver, poolName, shareName, shareSizeGb); 

            // Create Samba export 
            RsWebUtil.createSambaExport(driver, shareName, adminUser,
                    browsable, guestOk, readOnly, comment);

            // Create NFS export
            //RsWebUtil.createNfsExport(driver, shares, hostStr, adminHost, 
                    //writable, sync) {
                    
            // Create snapshot 
            RsWebUtil.createSnapshot(driver, shareName, snapName, snapVisible);

            // Logout
            RsWebUtil.logout(driver);

        } catch(Exception e){
            handleException(e);
		}

    }

}

//CreateUserWebUI.class
