package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({ListPoolDetails.class,DeletePoolSharesPresent.class,
	DeleteShareandPool.class,
	LinktoPoolDetailsPage.class,DeletePoolSharenotPresent.class})

public class PoolsTestSuite {

}
