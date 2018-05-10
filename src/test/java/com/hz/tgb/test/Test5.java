package com.hz.tgb.test;

import com.hz.tgb.common.StringUtil;
import com.hz.tgb.common.datetime.DateUtil;
import com.hz.tgb.file.FileUtil;

import java.io.File;
import java.util.Date;

/**
 * Created by hezhao on 2017/10/23 16:21.
 */
public class Test5 {
    public static void main(String[] args) {
//        System.out.println("====================");
//
//        String str = "  ";
//        str += "\n";
//        str += "";
//        str += "\n";
//        str += "  ";
//        str += "\n";
//        str += "  ";
//        String ssoid = str.replaceAll("\n", ",");
//
//        String[] split = str.split("\n");
//
//
//
//        System.out.println(ArrayUtil.join(split,","));
//
//        System.out.println("------------------------");
//        System.out.println(join(split,","));
//        System.out.println("====================");

//        String s = NumberUtil.changeY2F("0.51111999999999");
//        System.out.println(s);
//        String s1 = NumberUtil.changeY2FRound("0.51111999999999");
//        System.out.println(s1);

//        NoticeStatus status = NoticeStatus.ON;
//        String show = status.toString();
//        System.out.println(show);


//        Stu s1 = new Stu();
//        s1.setId(123);
//        s1.setName("tom");
//
//        Stu s2 = new Stu();
//        s2.setId(123);
//        s2.setName("tom");
//
//        Set<Stu> set = new HashSet();
//        set.add(s1);
//        set.add(s2);
//
//        for (Stu o : set) {
//            System.out.println(o.getId()+" , "+o.getName());
//        }

        String filePath = "/var/cata/vouApply";

        File saveFile = new File(filePath);
        if (!saveFile.isDirectory()) {
            saveFile.mkdirs();
        }

        //检查路径是否以 “/” “\” 结尾,如果不是 则加上“/”
        filePath = FileUtil.endSeparator(filePath);

        String date = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        filePath = filePath + "vouApplySSoids" + "-" + "81015212" + "-" + date;
        System.out.println(filePath);
    }

    public enum NoticeStatus {
        NORMAL("正常"),
        OFF("关闭"),
        ON("开启");

        private String desc;

        private NoticeStatus(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static String join(String[] array,String splitStr) {
        StringBuffer result = new StringBuffer("");
        if ((array == null) || (array.length == 0)) {
            return result.toString();
        }

        for (int i = 0; i < array.length; i++) {
            if(!StringUtil.isBlank(array[i].trim())) {
                result.append(array[i].trim() + splitStr);
            }
        }

        if(result.toString().length() > 0){
            return result.toString().substring(0,result.toString().length()-1);
        }
        return "";
    }
}
