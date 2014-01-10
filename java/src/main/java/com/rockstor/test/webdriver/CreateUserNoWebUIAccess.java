package com.rockstor.test.webdriver;

import static org.junit.Assert.assertTrue;

import java.io.File;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rockstor.test.util.RSProps;

public class CreateUserNoWebUIAccess {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void testUserNoWebUI() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			//User Login Input Forms
			WebElement username = driver.findElement(
					By.id("username"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(
					By.id("password"));
			password.sendKeys("admin");

			WebElement submitButton = driver.findElement(
					By.id("sign_in"));
			submitButton.click();

			// Select System from Navigation bar
			WebElement systemNav = driver.findElement(By.id("system_nav"));
			systemNav.click();

			// Select Users  from system side bar
			WebElement usersNav = driver.findElement(By.xpath("//div[@id='sidebar-inner']/ul/li/a[contains(@href,'users')]"));
			usersNav.click();

			// Wait for Add user button to appear
			WebElement myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("add-user")));
			//Add User
			WebElement addUser = driver.findElement(By.id("add-user"));
			addUser.click();


			// Wait for Add user button to appear
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("create-user")));

			/// Create User Form Fillup

			//User Name
			WebElement usrName = driver.findElement(By.id("username"));
			usrName.sendKeys("newrockstor");

			// Don't check the box which enables web ui access to user.

			//Password
			WebElement pswd = driver.findElement(By.id("password"));
			pswd.sendKeys("rock");

			//Confirm Password
			WebElement cnfrmPswd = driver.findElement(By.id("password_confirmation"));
			cnfrmPswd.sendKeys("rock");

			//Submit Button
			WebElement usrSubmitBtn = driver.findElement(By.id("create-user"));
			usrSubmitBtn.click();

			//Logout
			WebElement logoutSubmit = driver.findElement(
					By.id("logout_user"));

			logoutSubmit.click();

			/// Login with Newly created user

			//User Login Input Forms
			
			myWaitElement = (new WebDriverWait(driver, 150))
					.until(ExpectedConditions.elementToBeClickable(
							By.id("sign_in")));
			//Username
			username = driver.findElement(
					By.id("username"));
			username.sendKeys("newrockstor");

			//Password
			password = driver.findElement(
					By.id("password"));
			password.sendKeys("rock");

			//Sign-In
			submitButton = driver.findElement(
					By.id("sign_in"));
			submitButton.click();

			//Verify Incorrect Login
			WebElement verifyErrorMsg = driver.findElement(
					By.xpath("//div/ul/li[text()='Login incorrect!']"));
			assertTrue(verifyErrorMsg.getText(),true);
			
		}

		//catch any exceptions by taking screenshots
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
		//driver.quit();
	}

}

