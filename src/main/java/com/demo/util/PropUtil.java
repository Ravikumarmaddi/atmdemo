package com.demo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtil {
    public static Properties appProps;
    static {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String pnm = appProps.getProperty("project.name");
        if("billpayments".equals(pnm)){
            System.out.println(true);
        }
        else
            System.out.println(false);
    }
}

