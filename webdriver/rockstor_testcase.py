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

class RockStorTestCase(unittest.TestCase):
    def __init__(self, methodName='runTest', screenshot_dir=None):
        super(RockStorTestCase, self).__init__(methodName)
        if screenshot_dir == None:
            self.screenshot_dir = 'output'
        else:
            self.screenshot_dir = screenshot_dir

    def setUp(self):
        self.driver = webdriver.Firefox()
        self.driver.implicitly_wait(30)
        self.conf = read_conf()
        self.base_url = self.conf['base_url']
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()

    def save_screenshot(self, testname):
        self.driver.save_screenshot('%s/%s.png' % (self.screenshot_dir, 
            testname))

    @staticmethod
    def parametrize(testcase_klass, screenshot_dir = None):
        """ Create a suite containing all tests taken from the given
            subclass, passing them the parameter 'param'.
        """
        testloader = unittest.TestLoader()
        testnames = testloader.getTestCaseNames(testcase_klass)
        suite = unittest.TestSuite()
        for name in testnames:
            suite.addTest(testcase_klass(name, screenshot_dir=screenshot_dir))
        return suite
