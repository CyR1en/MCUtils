package com.cyr1en.mcutils.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionUtil {
    public static void generateErrorLog(Exception exception) {
        File dir = new File("exceptions");
        boolean mkdir;
        if(!dir.exists())
            mkdir = dir.mkdir();
        else
            mkdir = true;
        if(mkdir) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            File log = new File("exceptions/" + dateFormat.format(date) + ".log");
            try {
                PrintStream ps = new PrintStream(log);
                exception.printStackTrace(ps);
                ps.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
