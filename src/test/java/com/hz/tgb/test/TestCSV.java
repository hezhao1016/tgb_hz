package com.hz.tgb.test;

import com.hz.tgb.common.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezhao on 2017/11/29 16:54.
 */
public class TestCSV {
    public static void main(String[] args) {
//        List<String[]> applyList = CSVUtil.readCSV(new File("D:\\上传 example\\退款原因为空 - 副本.csv").getAbsolutePath(), true);
//
//        int emptyCount = 0;
//        for (String[] strings : applyList) {
//            if(strings.length == 3){
//                emptyCount++;
//            }
//        }
//
//        if(emptyCount == applyList.size()){
//            System.out.println("商户订单号全部是空");
//
//            for (int i = 0; i < applyList.size(); i++) {
//                String[] array = (String[]) ArrayUtil.resizeArray(applyList.get(i), 4);
//                array[3] = "";
//                applyList.set(i,array);
//            }
//        }
//
//        for (String[] strings : applyList) {
//            System.out.println("------------------");
//            System.out.println(ArrayUtil.join(strings));
//        }




//        List<String[]> applyList = FileUtil.readTextForList("D:\\上传 example\\退款原因为空.txt", ",");
//        for (int i = 0; i < applyList.size(); i++) {
//            if(applyList.get(i).length == 3){
//                String[] array = (String[]) ArrayUtil.resizeArray(applyList.get(i), 4);
//                array[3] = "";
//                applyList.set(i,array);
//            }
//        }
//
//        for (String[] strings : applyList) {
//            System.out.println("------------------");
//            System.out.println(ArrayUtil.join(strings));
//        }




//        BigDecimal amount = new BigDecimal("1.0");
//        System.out.println(amount.compareTo(new BigDecimal("0.01")));
//        if(amount.compareTo(new BigDecimal("0.01")) < 0) {
//            System.out.println("退款金额最低为0.01");
//        }
//
//        String amountStr = "1";
//        BigDecimal amounts = new BigDecimal(amountStr);
//        System.out.println(amount.compareTo(amounts));
//        //结果是:-1 小于,0 等于,1 大于
//        if(amount.compareTo(amounts) > 0) {
//            System.out.println("退款金额超出订单金额,订单金额["+amountStr+"]");
//        }



//        List<String[]> applyList = FileUtil.readTextForList("D:/批量退款txt模板 (3).txt", ",");
//        for (String[] strings : applyList) {
//            for (String str : strings) {
//                System.out.println(str);
//                System.out.println(str.equals("KB201712061405182000015335816484"));
//            }
//        }




        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");

        try {
            List page = page(1, 10, list);
            System.out.println(ListUtil.join(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param pageNo 当前页码
     * @param pageSize 页数
     * @param list  所有集合
     * @return
     * @throws Exception
     */
    public static List page(int pageNo,int pageSize,List list) throws Exception{
        List result = new ArrayList<>();
        if(list != null && list.size() > 0 && pageNo > 0 && pageSize > 0){

            if(pageNo == 1 && pageSize >= list.size()){
                return list;
            }
            if((pageNo-1) * pageSize  >= list.size()){
                return new ArrayList<>();
            }

            int allCount = list.size();
            int pageCount = (allCount + pageSize-1) / pageSize;
            if(pageNo >= pageCount){
                pageNo = pageCount;
            }
            int start = (pageNo-1) * pageSize;
            int end = pageNo * pageSize;
            if(end >= allCount){
                end = allCount;
            }
            for(int i = start; i < end; i ++){
                result.add(list.get(i));
            }
        }
        return (result != null && result.size() > 0) ? result : new ArrayList<>();
    }
}
