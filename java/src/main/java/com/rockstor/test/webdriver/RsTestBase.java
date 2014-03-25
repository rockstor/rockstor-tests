package com.rockstor.test.webdriver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

public class RsTestBase {
	protected static WebDriver driver;
	
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = RsWebUtil.setupDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

    public void handleException(Exception e) throws Exception {
        String filename = this.getClass().getName()+".png";
        RsWebUtil.takeScreenshot(driver, filename);
        throw e;
    }

}
