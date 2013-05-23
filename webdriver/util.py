import yaml
import argparse

def read_conf():
    parser = argparse.ArgumentParser(description='Run RockStor webdriver tests.')
    parser.add_argument('-e', '--env_name', default='development') 
    parser.add_argument('testname', nargs='?', default=None) 
    args = parser.parse_args()
    conf = None
    with open('%s.yaml' % args.env_name,'r') as f:
        conf = yaml.load(f)
    return conf

