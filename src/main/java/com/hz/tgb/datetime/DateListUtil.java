package com.hz.tgb.datetime;

import com.hz.tgb.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期列表工具类
 *
 * Created by hezhao on 2018-07-12 15:00
 */
public class DateListUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateListUtil.class);

    private DateListUtil() {
        // 私有类构造方法
    }

    /**
     * 填充日期列表
     *
     * @param dateType 日期类型， 支持年、月、周、日、时、分
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return 日期列表
     */
    public static List<String> getDateList(String dateType, String startTime, String endTime) {
        if (StringUtil.isBlank(dateType)){
            return new ArrayList<>();
        }

        DateEnums.DateType type = DateEnums.DateType.valueOf(dateType.trim().toUpperCase());

        return getDateList(type, startTime, endTime);
    }

    /**
     * 填充日期列表
     *
     * @param dateType 日期类型， 支持年、月、周、日、时、分
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return 日期列表
     */
    public static List<String> getDateList(DateEnums.DateType dateType, String startTime, String endTime) {
        if (dateType == null || StringUtil.isBlank(startTime) || StringUtil.isBlank(endTime)) {
            return new ArrayList<>();
        }

        switch (dateType) {
            case YEAR: // 年
                return getYearList(startTime, endTime);
            case MONTH: // 月
                return getMonthList(startTime, endTime);
            case WEEK: // 周
                return getWeekList(startTime, endTime);
            case DAY: // 日
                return getDayList(startTime, endTime);
            case HOUR: // 时
                return getHourList(startTime, endTime);
            case MINUTE: // 分
                return getMinuteList(startTime, endTime);
            default: // 默认：日
                return getDayList(startTime, endTime);
        }
    }

    /**
     * 获取年份之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getYearList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        int yearsBetween = 0;

        try {
            yearsBetween = Math.abs(Integer.parseInt(endTime) - Integer.parseInt(startTime));
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        for (int i = 0; i <= yearsBetween; i++) {
            dateList.add(DateUtil.addYear(startTime, i));
        }

        return dateList;
    }

    /**
     * 获取月份之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getMonthList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        DateEnums.DateStyle dateStyle = DateUtil.getDateStyle(startTime);
        Date beginDate = DateUtil.parse(startTime, dateStyle);
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        while (!endTime.equals(startTime)) {
            dateList.add(startTime);
            c.add(Calendar.MONTH, +1);
            startTime = DateUtil.format(c.getTime(), dateStyle);
        }
        dateList.add(endTime);
        return dateList;
    }

    /**
     * 获取星期之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getWeekList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        DateEnums.DateStyle dateStyle = DateUtil.getDateStyle(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.parse(startTime, dateStyle));

        if(c.get(Calendar.DAY_OF_WEEK) == 2){
            dateList.add(startTime);
        }

        for(int i = 0; i<365; i++){
            c.add(Calendar.DATE,1);
            // 周一作为一周开始
            String endDate = DateUtil.format(c.getTime(), dateStyle);
            if(c.get(Calendar.DAY_OF_WEEK) == 2){
                dateList.add(endDate);
            }
            if(endDate.equals(endTime)){
                break;
            }
        }

        return dateList;
    }

    /**
     * 获取天之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDayList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        DateEnums.DateStyle dateStyle = DateUtil.getDateStyle(startTime);
        Date beginDate = DateUtil.parse(startTime, dateStyle);
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        while (!endTime.equals(startTime)) {
            dateList.add(startTime);
            c.add(Calendar.DATE, +1);
            startTime = DateUtil.format(c.getTime(), dateStyle);
        }
        dateList.add(endTime);
        return dateList;
    }

    /**
     * 获取小时之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getHourList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        DateEnums.DateStyle dateStyle = DateUtil.getDateStyle(startTime);
        Date beginDate = DateUtil.parse(startTime, dateStyle);
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        while(!endTime.equals(startTime)){
            dateList.add(startTime);
            c.add(Calendar.HOUR_OF_DAY,+1);
            startTime = DateUtil.format(c.getTime(), dateStyle);
        }
        dateList.add(endTime);
        return dateList;
    }

    /**
     * 获取分钟之间的列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getMinuteList(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        DateEnums.DateStyle dateStyle = DateUtil.getDateStyle(startTime);
        Date beginDate = DateUtil.parse(startTime, dateStyle);
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        while(!endTime.equals(startTime)){
            dateList.add(startTime);
            c.add(Calendar.MINUTE,+1);
            startTime = DateUtil.format(c.getTime(), dateStyle);
        }
        dateList.add(endTime);

        return dateList;
    }

    public static void main(String[] args) {
        List<String> dateList = DateListUtil.getDateList(DateEnums.DateType.YEAR, "2013", "2018");
        System.out.println(dateList);

        dateList = DateListUtil.getDateList(DateEnums.DateType.MONTH, "2017-11", "2018-03");
        System.out.println(dateList);

        dateList = DateListUtil.getDateList(DateEnums.DateType.WEEK, "2018-05-29", "2018-06-15");
        System.out.println(dateList);

        dateList = DateListUtil.getDateList(DateEnums.DateType.DAY, "2018/05/29", "2018/06/05");
        System.out.println(dateList);

        dateList = DateListUtil.getDateList(DateEnums.DateType.HOUR, "2018053121", "2018060102");
        System.out.println(dateList);

        dateList = DateListUtil.getDateList(DateEnums.DateType.MINUTE, "2018-05-31 23:56", "2018-06-01 00:02");
        System.out.println(dateList);

    }

}
