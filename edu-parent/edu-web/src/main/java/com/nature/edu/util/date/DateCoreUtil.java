package com.nature.edu.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * 
 * @Title: DateCoreUtil.java
 * @Description: corn 与date之间相互转换类
 * @author lilun
 * @date 2020-04-21 06:29:55 
 * @version 1.0
 */
public class DateCoreUtil {
 
    private static final String DATEFORMAT = "ss mm HH dd MM ?";
 
    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
 
    /**
     * @param cron
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String cron, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        if (cron != null) {
            date = sdf.parse(cron);
        }
        return date;
    }
 
    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date, DATEFORMAT);
    }
 
    /***
     * convert cron to Date
     * @param cron  : cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDate(String cron) throws ParseException {
        return parseStringToDate(cron, DATEFORMAT);
    }
}