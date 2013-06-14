package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({PoolRaid0ShareNFS.class,PoolRaid0ShareSamba.class,
	PoolRaid0ShareSnapshot.class, CheckDisplayPoolnameofShare.class, CheckShareUsedandFreeSpace.class,
	DeleteShareFromSharesDetailPage.class,DeleteShareandPool.class,ShareResize.class, 
	DeleteShareandPool.class})

public class ShareDetailsSuite {
	
}

