import java.io.File;
import java.io.FileInputStream;
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
import java.io.IOException;
import java.util.Properties;

public class GenericDeleteShare{

	public static void main(String[] args) throws IOException {
		// Create a new instance of the Firefox driver
	
		WebDriver driver = new FirefoxDriver();

		try{

			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			driver.get(prop.getProperty("RockstorVm"));


			// User Login Input Forms
			WebElement username = driver.findElement(By.id("inputUsername"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("inputPassword"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();
			
			// Select Shares from Navigation bar
			WebElement poolsNav = driver.findElement(By.id("shares_nav"));
			poolsNav.click();

			//Explicit Wait for Pools page to load
			WebElement myWaitElement1 = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id("delete_pool_pool1"))); // change id
			
			//Delete Pool
			WebElement deletePool = driver.findElement(By.id("delete_pool_pool1")); // change id for delete share
			deletePool.click();
			
			//Wait until the normal pool page loads
			WebElement myWaitElement2 = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id("create_pool")));
		}

		
		//catch any exceptions by taking screenshots
		catch(Exception e){

			System.out.println(e.toString());

			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile,new File("/home/priya/DelPool_Raid02Disks.png"));

		}

		//click on logout
		WebElement logoutSubmit = driver.findElement(By.id("logout_user"));

		logoutSubmit.click();

	}
}


