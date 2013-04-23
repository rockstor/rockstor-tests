from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait 
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
import unittest, time, re
import yaml
import sys, getopt
import time
from rockstor_testcase import RockStorTestCase

class LoginAndSetup(RockStorTestCase):
    def test_login_and_setup(self):
        driver = self.driver
        driver.get(self.base_url + "/login_page?next=/")
        driver.find_element_by_id("inputUsername").clear()
        driver.find_element_by_id("inputUsername").send_keys("admin")
        driver.find_element_by_id("inputPassword").clear()
        driver.find_element_by_id("inputPassword").send_keys("admin")
        driver.find_element_by_id("sign_in").click()
        btn_next = driver.find_element_by_id('next-page')
        next_step_text = btn_next.text
        # Go through the setup wizard till the last page is displayed.
        while next_step_text != 'Finish':
            btn_next.click()
            # wait till current step element is rendered
            current_step = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CLASS_NAME, "current-step")))
            btn_next = driver.find_element_by_id('next-page')
            next_step_text = btn_next.text
        
        # Finish
        btn_next.click()
        
        # Check that the dashboard title is displayed
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "widgets-container")))
        dashboard_title = driver.find_element_by_id("title")
        self.assertRegexpMatches(dashboard_title.text, r"^[\s\S]*System Dashboard[\s\S]*$")
        # logout
        driver.find_element_by_id("logout_user").click()

if __name__ == '__main__':
    unittest.main()

