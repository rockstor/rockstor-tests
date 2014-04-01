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

public class NFSExport extends RsTestBase {

	@Test
	public void nfsExport() throws Exception {
        String shareName = "share1";
        String[] shares = new String[] {"share1"};
        String hostStr = "192.168.56.101";
        String adminHost = "192.168.56.101";
        boolean writable = true;
        boolean sync = true;
		try{

            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));

            RsWebUtil.createNfsExport(driver, shares, hostStr, adminHost,
                    writable, sync);

            RsWebUtil.logout(driver);
			
		}
		catch(Exception e){
            handleException(e);
		}
	}

}


