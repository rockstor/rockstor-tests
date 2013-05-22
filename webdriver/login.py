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
import sys, traceback

class Login(RockStorTestCase):
    def test_login(self):
        try:
            # Login
            driver = self.driver
            driver.get(self.base_url + "/login_page")
            driver.find_element_by_id("inputUsername").clear()
            driver.find_element_by_id("inputUsername").send_keys("admin")
            driver.find_element_by_id("inputPassword").clear()
            driver.find_element_by_id("inputPassword").send_keys("admin")
            driver.find_element_by_id("sign_in").click()

            # Check that the dashboard title is displayed
            WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CLASS_NAME, "widgets-container")))
            dashboard_title = driver.find_element_by_id("title")
            self.assertRegexpMatches(dashboard_title.text, r"^[\s\S]*Dashboard[\s\S]*$")
            raise Exception()
            # logout
            driver.find_element_by_id("logout_user").click()
        except Exception, e:
            exc_type, exc_value, exc_traceback = sys.exc_info()
            traceback.print_exception(exc_type, exc_value, exc_traceback,
                    limit=2, file=sys.stdout)
            self.save_screenshot(stack()[0][3]) 

if __name__ == '__main__':
    unittest.main()

