package com.rockstor.test.webdriver;

import java.io.File;
import java.io.FileInputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.io.FileUtils; // Screenshots
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot; 
import java.io.IOException;
import java.util.Properties;
import com.rockstor.test.util.RSProps;

public class LoginHappyPath{

	public static void main(String[] args) throws IOException{
		// Create a new instance of the Firefox driver

                //Properties prop = new Properties();
                //prop.load(AddPoolRaid0with0Disks.class.getClassLoader().
                                //getResourceAsStream("config.properties"));
		WebDriver driver = new FirefoxDriver();
		
		try{
			
			driver.get(RSProps.getProperty("RockstorVm"));

			//User Login Input Forms
			WebElement username = driver.findElement(By.id("inputUsername"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("inputPassword"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();
			
		}

		//catch any exceptions by taking screenshots
		catch(Exception e){
                        e.printStackTrace();
			System.out.println(e.toString());

			//File screenshotFile = ((TakesScreenshot)driver)
                                //.getScreenshotAs(OutputType.FILE);
			//FileUtils.copyFile(screenshotFile,new File("/home/priya/rockstor-tests/webdriver/java/ScreenShots/LoginHappyPath.png"));

		}
		
		//click on logout
		WebElement logoutSubmit = driver.findElement(By.id("logout_user"));

		logoutSubmit.click();
		driver.close();

	}
}



