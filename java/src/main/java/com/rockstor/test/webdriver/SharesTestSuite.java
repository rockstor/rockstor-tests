package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({LinkToSharePage.class,DeleteShareWhenExported.class,ListSharesWithSizes.class,
	MultipleSharesinSamePool.class,})

public class SharesTestSuite {
	
}

