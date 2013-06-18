package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ShareSelectPool.class,CreateShareinKb.class,CreateShareinMb.class,
	CreateShareinGb.class, CreateShareinTb.class, SharewithSameNameinSamePool.class,
	SharewithSameNameinDiffPools.class})

public class CreateShareTestSuite {
	
}

