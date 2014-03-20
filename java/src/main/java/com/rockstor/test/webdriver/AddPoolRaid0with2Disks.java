package com.rockstor.test.webdriver;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

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

import com.rockstor.test.webdriver.RsWebUtil;
import com.rockstor.test.util.RSProps;
import com.rockstor.test.util.RsUtil;

import java.io.IOException;
import java.util.Properties;


public class AddPoolRaid0with2Disks extends RsTestBase {

	@Test
	public void testPool() throws Exception {
		try{
        
            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));
        
            String poolName = "pool_" + RsUtil.generateRandomString(10);
            System.out.println("Pool name is " + poolName);

		    RsWebUtil.createPool(driver, poolName, 0);	
            RsWebUtil.logout(driver);

		}
		catch(Exception e){
            handleException(e);
		}

	}

}

