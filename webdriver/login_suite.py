from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait 
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
import unittest, time, re
import yaml
import sys, getopt
from login import Login
from login_invalid_username import LoginInvalidUsername
from login_invalid_password import LoginInvalidPassword
from rockstor_testcase import RockStorTestCase
from util import read_conf
import argparse

if __name__ == "__main__":
    suite = unittest.TestSuite([\
            unittest.TestLoader().loadTestsFromTestCase(Login),\
            unittest.TestLoader().loadTestsFromTestCase(LoginInvalidUsername),
            unittest.TestLoader().loadTestsFromTestCase(LoginInvalidPassword),
            ])

    unittest.TextTestRunner(verbosity=2).run(suite)

