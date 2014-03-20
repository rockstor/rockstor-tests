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
import com.rockstor.test.util.RsUtil;
import com.rockstor.test.webdriver.RsWebUtil;

public class CreateShareinGb extends RsTestBase {

    @Test
    public void testShareSizeGb() throws Exception {
        try{

            RsWebUtil.login(driver, RSProps.getProperty("username"), 
                    RSProps.getProperty("password"));
            
            String poolName = "pool1";

            // Create 3 shares
            for (int i=0; i < 3; i++) {
                String shareName = "share_" + RsUtil.generateRandomString(10);
                RsWebUtil.createShare(driver, poolName, shareName, 1); 
            }

            // Logout
            RsWebUtil.logout(driver);

        }
        catch(Exception e){
            handleException(e);
        }

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.quit();
    }

}
