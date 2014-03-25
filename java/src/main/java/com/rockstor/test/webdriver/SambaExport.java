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

public class SambaExport extends RsTestBase {

	@Test
	public void sambaExport() throws Exception {
		try{
            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));

            String shareName = "share1";
            RsWebUtil.createSambaExport(driver, shareName,
                    "admin", true, false, false, "");

            RsWebUtil.logout(driver);
			
		}
		catch(Exception e){
            e.printStackTrace();
            handleException(e);
		}
	}
}
