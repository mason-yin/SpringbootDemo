package edu.cmu.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: [helper class that transform time]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/4 20:14]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/4 20:14]
 * @updateRemark: [initial commit]
 */
public class TimeUtil {

    public static Date getStandardDay(Date date) throws ParseException {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        return today;
    }

    public static Date getStandardNextDay(Date date) throws ParseException {
        Date today = getStandardDay(date);
        Date tomorrow = new SimpleDateFormat("yyyy-MM-dd").parse(
                new SimpleDateFormat("yyyy-MM-dd").format(new Date(today.getTime() + 24 * 60 * 60 * 1000)));
        return tomorrow;
    }

}
