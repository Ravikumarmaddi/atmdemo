package com.demo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ObjectToJSonUtil {


    public static String objectPrettyPrint(Object org) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(org);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToFiles(Object in, Object out) {
        try {
            FileUtils.writeStringToFile(new File("/Users/ravi/SRI_GD/MYWORK/billpayments/complete/input.txt"), objectPrettyPrint(in));
            FileUtils.writeStringToFile(new File("/Users/ravi/SRI_GD/MYWORK/billpayments/complete/output.txt"), objectPrettyPrint(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}