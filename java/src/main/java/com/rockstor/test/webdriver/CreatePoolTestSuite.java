package com.rockstor.test.webdriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CreateAPool.class,CreatePoolRaid0.class,CreatePoolRaid1.class,
	CreatePoolRaid10.class, CreatePoolRaid0with0Disks.class, CreatePoolRaid1with0Disks.class,
	CreatePoolRaid1with1Disk.class,CreatePoolRaid1with1Disk.class,CreatePoolRaid10with1Disk.class,
	CreatePoolRaid10with0Disks.class,CreatePoolRaid10with3Disks.class,
	CreatePoolRaid0with5Disks.class,CreatePoolRaid1with5Disks.class,CreatePoolRaid10with10Disks.class,
	NoDisplayofDisksAlreadyinUse.class,TwoPoolsWithSameName.class})

public class CreatePoolTestSuite {
	
}
