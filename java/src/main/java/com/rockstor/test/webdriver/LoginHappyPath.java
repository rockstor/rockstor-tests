package com.rockstor.test.webdriver;

import static org.junit.Assert.assertEquals;

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
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import com.rockstor.test.util.RSProps;

public class LoginHappyPath {
    private static WebDriver driver;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void happyPath() throws Exception {
        try{

            driver.get(RSProps.getProperty("RockstorVm"));

            //User Login Input Forms
            WebElement username = driver.findElement(By.id("inputUsername"));
            username.sendKeys("admin");

            WebElement password = driver.findElement(By.id("inputPassword"));
            password.sendKeys("admin");

            WebElement submit = driver.findElement(By.id("sign_in"));
            submit.click();

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


