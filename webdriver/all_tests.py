from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait 
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
import unittest, time, re
import yaml
import sys, getopt
from rockstor_testcase import RockStorTestCase
from inspect import stack
from util import read_conf
from login import Login
from pools_create_raid0_2disks import PoolsCreateRaid02Disks

if __name__ == '__main__':
    conf = read_conf()
    # TODO generate screenshot dir name from timestamp and create dir
    conf['screenshot_dir'] = 'output2' 
    suite = unittest.TestSuite()
    suite.addTest(RockStorTestCase.parametrize(Login, conf=conf))
    suite.addTest(RockStorTestCase.parametrize(PoolsCreateRaid02Disks, conf=conf))
    unittest.TextTestRunner(verbosity=2).run(suite)


