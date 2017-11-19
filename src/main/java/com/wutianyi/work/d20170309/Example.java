package com.wutianyi.work.d20170309;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by hanjiewu on 2017/3/9.
 */
public class Example {

    public static void main(String[] args) throws IOException {
//        List<String> contents = FileUtils.readLines(new File(""));
        Date date = new Date(1489385636000l);

        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
    }
}
