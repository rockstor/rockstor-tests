package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({CheckDisplayDisksinPoolDetails.class,CheckSizeinPoolDetails.class,
	CreateSharefromPoolDetails.class,DeletePoolfromPoolDetails.class})

public class PoolDetailsTestSuite {
	
}
