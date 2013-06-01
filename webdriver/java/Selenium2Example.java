
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium2Example  {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get("https://rockstor-iso:8443");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement username = driver.findElement(By.id("inputUsername"));

        // Enter something to search for
        username.sendKeys("admin");

        WebElement password = driver.findElement(By.id("inputPassword"));
        password.sendKeys("admin");

        WebElement submit = driver.findElement(By.id("sign_in"));

        // Now submit the form. WebDriver will find the form for us from the element
        submit.click();
        
        WebElement logoutSubmit = driver.findElement(By.id("logout_user"));
        
        logoutSubmit.click();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
                
        //Close the browser	
        //driver.quit();
    }
}

