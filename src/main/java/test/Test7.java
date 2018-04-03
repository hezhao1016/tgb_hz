package test;

import com.hz.tgb.common.ArrayUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hezhao on 2017/9/28 16:13.
 */
public class Test7 {
    public static void main(String[] args) {
//        BigDecimal b = new BigDecimal("sfdjhmbvchg");
//
//        System.out.println(b);


        String s = "";
        String[] split = s.split(",");

        System.out.println(ArrayUtil.join(split,","));

        Set<String> set = new HashSet();
        set.add("123");
        set.add("1223");
        System.out.println(join_set(set));
    }

    /**
     * 拼接Set集合，使用 “、”分隔
     * @param set
     * @return
     */
    public static String join_set(Set<String> set) {
        if (set == null || set.size() == 0)
            return "";
        if (set.size() == 1){
            for (String s : set) {
                return String.valueOf(s);
            }
        }

        StringBuffer result = new StringBuffer("");

        for (String s : set) {
            result.append(s + "、");
        }
        return result.toString().substring(0,result.length() - 1);
    }
}
