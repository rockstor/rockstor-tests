package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({AddPoolRaid0with2Disks.class,CreateShareinGb.class,
	SambaExport.class,NFSExport.class,CreateSnapshotFromShares.class
	})

public class CreateSeriesTestSuite {

}

//CreateUserWebUI.class