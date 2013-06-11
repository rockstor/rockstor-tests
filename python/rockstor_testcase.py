from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait 
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
import unittest, time, re
import yaml
import sys, getopt
from util import read_conf
from inspect import stack

class RockStorTestCase(unittest.TestCase):
    def __init__(self, methodName='runTest', conf=None):
        super(RockStorTestCase, self).__init__(methodName)
        if conf == None:
            self.conf = read_conf()
        else:
            self.conf = conf

    def setUp(self):
        self.driver = webdriver.Firefox()
        self.driver.implicitly_wait(15)
        self.base_url = self.conf['base_url']
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()

    def save_screenshot(self, testname):
        filename = "%s_%s" %(self.get_timestamp_str(), testname)
        print "Saving screenshot to %s" % filename
        self.driver.save_screenshot('%s/%s.png' % (self.conf['screenshot_dir'], 
            filename))
     
    def save_screenshot_from_stack(self):
        self.save_screenshot(stack()[1][3])

    def get_timestamp_str(self):
        return time.strftime("%Y%m%d%H%M%S")
    
    @staticmethod
    def parametrize(testcase_klass, conf = None):
        """ Create a suite containing all tests taken from the given
            subclass, passing them the parameter 'param'.
        """
        testloader = unittest.TestLoader()
        testnames = testloader.getTestCaseNames(testcase_klass)
        suite = unittest.TestSuite()
        for name in testnames:
            suite.addTest(testcase_klass(name, conf=conf))
        return suite
   

