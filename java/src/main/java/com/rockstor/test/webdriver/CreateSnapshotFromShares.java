package com.rockstor.test.webdriver;
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


public class CreateSnapshotFromShares extends RsTestBase {

	@Test
	public void testUserNoWebUI() throws Exception {
        String shareName = "share1";
        String snapName = "snap_" + RsUtil.generateRandomString(10);
        boolean visible = true;
		try{

            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));
            RsWebUtil.createSnapshot(driver, shareName, snapName, visible);
            RsWebUtil.logout(driver);
		}
		catch(Exception e){
            handleException(e);
		}

	}
}


















