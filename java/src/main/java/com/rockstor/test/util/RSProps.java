package com.rockstor.test.util;

import java.util.Properties;

public class RSProps {

    public static Properties rsProps = null;
    static {
        try {
            init();
        } catch (Exception e) {
            System.out.println("Error while initializing properties");
            e.printStackTrace();
        }
    }
    public static final String PROP_FILENAME = "config.properties";

    public static void init() throws Exception {
        try {
            rsProps = new Properties(); 
            rsProps.load(RSProps.class.getClassLoader().
                    getResourceAsStream(PROP_FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String getProperty(String name) throws Exception {
        if (rsProps == null) {
            throw new Exception("Properties was not initialized properly.");
        }
        return rsProps.getProperty(name);
    }
}


