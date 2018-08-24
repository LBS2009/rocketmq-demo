package com.lbs.rocketmqdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description: 时间工具类
 *
 * @author libosheng
 * @date 2018-8-24
 */
public class TimeUtil {

    /**
     * 日期格式 HH:mm:SS
     */
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:SS");

    /**
     * 返回时间 HH:mm:SS
     * @return String HH:mm:SS
     */
    public static String getTime() {
        return format.format(new Date());
    }
}
