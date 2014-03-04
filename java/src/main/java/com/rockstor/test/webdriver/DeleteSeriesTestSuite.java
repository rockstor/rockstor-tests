package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({DeleteSambaExport.class,DeleteNFSexport.class,DeleteSnapshot.class,DeleteShare.class,DeletePool.class
	})

public class DeleteSeriesTestSuite {

}
