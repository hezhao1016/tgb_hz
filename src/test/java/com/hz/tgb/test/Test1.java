package com.hz.tgb.test;

import com.hz.tgb.common.ArrayUtil;
import com.hz.tgb.datetime.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hezhao on 2017/12/19 17:30.
 */
public class Test1 {

//    public static void main(String[] args) {
//        while (true) {
//            System.out.println(">>>欢迎使用邮件自动发送系统");
//            System.out.println(">>>菜单");
//            System.out.println(">>>1、发送考勤邮件");
//            System.out.println(">>>2、发送考核邮件");
//            System.out.println(">>>3、退出");
//
//            Scanner input = new Scanner(System.in);
//            String change = input.next();
//            String msg = "";
//
//            switch (change) {
//                case "1":
//                    msg = "请输入考勤日期：";
//                case "2":
//                    msg = "请输入考核日期：";
//                    System.out.println(msg);
//
//                    String now = input.next();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    try {
//                        Date parse = sdf.parse(now);
//                        boolean check = DateUtil.check(now);
//                        if (check){
//                            System.out.println(sdf.format(parse));
//
//                            System.out.println("xxx");
//
//                        }else{
//                            throw new Exception("日期格式错误");
//                        }
//
//                    } catch (Exception e) {
//                        System.out.println("日期格式错误");
//                    }
//
//                    break;
//                case "3":
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println("已退出本系统。");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.exit(1);
//                    break;
//                default:
//                    System.out.println("输入错误~~~");
//                    break;
//            }
//        }
//    }

//    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
//
//        System.out.println("周黑鸭万达广场店");
//        System.out.println("流水号:0421");
//        System.out.println("打印时间:"+sdf.format(new Date()));
//        System.out.println("================================");
//        System.out.println("品");
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//    }

//    public static void main(String[] args) {
//        String data = "[\n" +
//                "    {\n" +
//                "        \"channelOperatorWeightList\": [\n" +
//                "            {\n" +
//                "                \"channelType\": \"GUODU\",\n" +
//                "                \"mobileWeight\": 0,\n" +
//                "                \"telecomWeight\": 0,\n" +
//                "                \"unicomWeight\": 0\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"countryTypeEnum\": \"CN\",\n" +
//                "        \"sendTypeEnum\": \"BATCH\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"channelOperatorWeightList\": [\n" +
//                "            {\n" +
//                "                \"channelType\": \"GUODU\",\n" +
//                "                \"mobileWeight\": 50,\n" +
//                "                \"telecomWeight\": 50,\n" +
//                "                \"unicomWeight\": 50\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"channelType\": \"XIEHE\",\n" +
//                "                \"mobileWeight\": 50,\n" +
//                "                \"telecomWeight\": 50,\n" +
//                "                \"unicomWeight\": 50\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"countryTypeEnum\": \"CN\",\n" +
//                "        \"sendTypeEnum\": \"SINGLE\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"channelOperatorWeightList\": [\n" +
//                "            {\n" +
//                "                \"channelType\": \"GUODU\",\n" +
//                "                \"mobileWeight\": 0,\n" +
//                "                \"telecomWeight\": 0,\n" +
//                "                \"unicomWeight\": 0\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"countryTypeEnum\": \"OTHER\",\n" +
//                "        \"sendTypeEnum\": \"SINGLE\"\n" +
//                "    }\n" +
//                "]";
//
//        JSONArray array = JSON.parseArray(data);
//        for (int i = 0; i < array.size(); i++) {
//            JSONObject json =  array.getJSONObject(i);
//            JSONArray channelOperatorWeightList = json.getJSONArray("channelOperatorWeightList");
//            for (int j = 0; j < channelOperatorWeightList.size(); j++) {
//                JSONObject o = channelOperatorWeightList.getJSONObject(j);
//                System.out.println(o.getString("channelType"));
//                System.out.println(o.getString("mobileWeight"));
//                System.out.println(o.getString("telecomWeight"));
//                System.out.println(o.getString("unicomWeight"));
//
//            }
//            System.out.println(json.getString("countryTypeEnum"));
//            System.out.println(json.getString("sendTypeEnum"));
//        }
//
//        System.out.println(array);
//    }

//    public static void main(String[] args) {
//        String str = "#+#13212345678#+# ";
//        String[] array = str.trim().split("\\#\\+\\#");
//        System.out.println(ArrayUtil.join(array));
//
//        String str1 = "#+#13212345678#+#短信内容1#+#";
//        String[] array1 = str1.trim().split("\\#\\+\\#");
//        System.out.println(ArrayUtil.join(array1));
//    }


    public static void main(String[] args) {
//        String timeType = "day";
//        String startTime = "2018/02/22";
//        String endTime = "2018/02/25";

//        String timeType = "month";
//        String startTime = "2018/02";
//        String endTime = "2018/03";

        String timeType = "year";
        String startTime = "2018";
        String endTime = "2019";


        String searchStartTime = "";
        String searchEndTime = "";
        Date now = new Date();

        if (!StringUtils.isBlank(startTime) && DateUtil.check(startTime)) {
            if("day".equals(timeType)){
                startTime = DateUtil.changeFormat(startTime,"yyyy/MM/dd","yyyy-MM-dd");
                searchStartTime = startTime + " 00:00:00";
            }else if("month".equals(timeType)){
                startTime = DateUtil.changeFormat(startTime,"yyyy/MM","yyyy-MM");
                Date firstDateByMonth = DateUtil.getFirstDateByMonth(DateUtil.parse(startTime, "yyyy-MM"));
                searchStartTime = DateUtil.format(firstDateByMonth,"yyyy-MM-dd") + " 00:00:00";
            }else if("year".equals(timeType)){
                Date firstDateByYear = DateUtil.getFirstDateByYear(DateUtil.parse(startTime, "yyyy"));
                searchStartTime = DateUtil.format(firstDateByYear,"yyyy-MM-dd") + " 00:00:00";
            }
        }else{
            if("day".equals(timeType)){
                //如果时间为空 那么默认展示最近7天。
                Date dateBefore = DateUtil.getDateBefore(now, 6);
                startTime = DateUtil.format(dateBefore,"yyyy-MM-dd");
                searchStartTime = startTime + " 00:00:00";
            }else if("month".equals(timeType)){
                //如果时间为空 那么默认展示最近一月。
                startTime = DateUtil.format(now,"yyyy-MM");
                Date firstDateByMonth = DateUtil.getFirstDateByMonth(DateUtil.parse(startTime, "yyyy-MM"));
                searchStartTime = DateUtil.format(firstDateByMonth,"yyyy-MM-dd") + " 00:00:00";
            }else if("year".equals(timeType)){
                //如果时间为空 那么默认展示最近一年。
                startTime = DateUtil.format(now,"yyyy");
                Date firstDateByYear = DateUtil.getFirstDateByYear(DateUtil.parse(startTime, "yyyy"));
                searchStartTime = DateUtil.format(firstDateByYear,"yyyy-MM-dd") + " 00:00:00";
            }
        }

        if (!StringUtils.isBlank(endTime) && DateUtil.check(endTime)) {
            if("day".equals(timeType)){
                endTime = DateUtil.changeFormat(endTime,"yyyy/MM/dd","yyyy-MM-dd");
                searchEndTime = endTime + " 23:59:59";
            }else if("month".equals(timeType)){
                endTime = DateUtil.changeFormat(endTime,"yyyy/MM","yyyy-MM");
                Date lastDateByMonth = DateUtil.getLastDateByMonth(DateUtil.parse(endTime, "yyyy-MM"));
                searchEndTime = DateUtil.format(lastDateByMonth,"yyyy-MM-dd") + " 23:59:59";
            }else if("year".equals(timeType)){
                Date lastDateByYear = DateUtil.getLastDateByYear(DateUtil.parse(endTime, "yyyy"));
                searchEndTime = DateUtil.format(lastDateByYear,"yyyy-MM-dd") + " 23:59:59";
            }
        }else{
            if("day".equals(timeType)){
                endTime = DateUtil.format(now,"yyyy-MM-dd");
                searchEndTime = endTime + " 23:59:59";
            }else if("month".equals(timeType)){
                endTime = DateUtil.format(now,"yyyy-MM");
                Date lastDateByMonth = DateUtil.getLastDateByMonth(DateUtil.parse(endTime, "yyyy-MM"));
                searchEndTime = DateUtil.format(lastDateByMonth,"yyyy-MM-dd") + " 23:59:59";
            }else if("year".equals(timeType)){
                endTime = DateUtil.format(now,"yyyy");
                Date lastDateByYear = DateUtil.getLastDateByYear(DateUtil.parse(endTime, "yyyy"));
                searchEndTime = DateUtil.format(lastDateByYear,"yyyy-MM-dd") + " 23:59:59";
            }
        }

        System.out.println(searchStartTime);
        System.out.println(searchEndTime);

        List<String> dateList = new ArrayList<>();

        if("day".equals(timeType)) {
            int daysBetween = DateUtil.daysBetween(startTime, endTime);
            //填充日期列表
            for (int i = 0; i <= daysBetween; i++) {
                dateList.add(DateUtil.addDay(startTime, i));
            }
        }else if("month".equals(timeType)){
            int monthsBetween = DateUtil.monthsBetween(startTime, endTime);
            //填充日期列表
            for (int i = 0; i <= monthsBetween; i++) {
                dateList.add(DateUtil.addMonth(startTime, i));
            }

        }else if("year".equals(timeType)){
            int yearsBetween = 0;

            try {
                yearsBetween = Math.abs(Integer.parseInt(endTime) - Integer.parseInt(startTime));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //填充日期列表
            for (int i = 0; i <= yearsBetween; i++) {
                dateList.add(DateUtil.addYear(startTime, i));
            }
        }

        System.out.println(ArrayUtil.joinList(dateList,", "));
    }

}
