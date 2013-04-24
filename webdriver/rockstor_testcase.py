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
    def __init__(self, methodName='runTest', conf=None):
        super(RockStorTestCase, self).__init__(methodName)
        if conf == None:
            self.conf = read_conf()
            self.conf['screenshot_dir'] = 'output'
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
        self.driver.save_screenshot('%s/%s.png' % (self.conf['screenshot_dir'], 
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
    
    def login(self):
        # Login
        driver = self.driver
        driver.get(self.base_url + "/login_page")
        driver.find_element_by_id("inputUsername").clear()
        driver.find_element_by_id("inputUsername").send_keys("admin")
        driver.find_element_by_id("inputPassword").clear()
        driver.find_element_by_id("inputPassword").send_keys("admin")
        driver.find_element_by_id("sign_in").click()

        # Check that the dashboard title is displayed
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "widgets-container")))
        dashboard_title = driver.find_element_by_id("title")
        self.assertRegexpMatches(dashboard_title.text, r"^[\s\S]*System Dashboard[\s\S]*$")
