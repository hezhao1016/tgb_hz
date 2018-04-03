package com.hz.tgb.doc;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.hz.tgb.file.EncodingDetect;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * .csv文件的读取和写入
 * Created by hezhao on 2017/9/25 11:19.
 */
public class CSVUtil {
    /*
    JAR - http://central.maven.org/maven2/net/sourceforge/javacsv/javacsv/2.0/javacsv-2.0.jar

    MAVEN:
    <dependency>
        <groupId>net.sourceforge.javacsv</groupId>
        <artifactId>javacsv</artifactId>
        <version>2.0</version>
    </dependency>

    API - http://javacsv.sourceforge.net/
    */

    /**
     * 写CSV文件
     * @param out 输出流
     * @param csvHeaders 表头
     * @param csvContents 内容
     */
    public static void writeCSV(OutputStream out, String[] csvHeaders, List<String[]> csvContents) {
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(out, ',', Charset.forName("UTF-8"));
            if(csvHeaders != null && csvHeaders.length > 0) {
                // 写表头
                csvWriter.writeRecord(csvHeaders);
            }
            // 写内容
            for (String[] csvContent : csvContents) {
                csvWriter.writeRecord(csvContent);
            }

            //在文件中增加BOM，详细说明可以Google,该处的byte[] 可以针对不同编码进行修改
            out.write(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF });

            csvWriter.flush();
            csvWriter.close();
//              System.out.println("--------CSV文件已经写入--------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读CSV文件
     * @param csvFilePath 文件路径
     * @param readHeader 是否读取表头
     * @return
     */
    public static List<String[]> readCSV(String csvFilePath,boolean readHeader) {
        List<String[]> list = new ArrayList<>();
        try {
            File file = new File(csvFilePath);
            if (!file.exists() || file.length() == 0) {
                return list;
            }
            String fileEncode = EncodingDetect.getJavaEncode(csvFilePath);

            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(fileEncode));
            list =  _readCSV(reader,readHeader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读CSV文件
     * @param input 输入流
     * @param readHeader 是否读取表头
     * @return
     */
    public static List<String[]> readCSV(InputStream input, boolean readHeader) {
        // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
        CsvReader reader = new CsvReader(input, ',', Charset.forName("UTF-8"));
        return _readCSV(reader,readHeader);
    }

    private static List<String[]> _readCSV(CsvReader reader,boolean readHeader) {
        // 用来保存数据
        ArrayList<String[]> csvFileList = new ArrayList<String[]>();
        try {
            // 跳过表头 如果需要表头的话，这句可以忽略
            if(!readHeader){
                reader.readHeaders();
            }
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
//                System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

//            // 遍历读取的CSV文件
//            for (int row = 0; row < csvFileList.size(); row++) {
//                // 取得第row行第0列的数据
//                String cell = csvFileList.get(row)[0];
//                System.out.println("------------>"+cell);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

    public static void main(String[] args) {
//        String[] headers = {"支付订单号","商户订单号","支付时间","ssoid ","imei","订单金额","退款金额","支付渠道","应用名称","退款原因","受理结果"};
//
//        List<String[]> contents = new ArrayList<>();
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款失败"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//        contents.add(new String[]{"RM2017091406320320FKYT0Y1A105688","GC201709191358238200200000000","2017/9/19  13:58:00 ","123456","86561312465","5","5","现在支付","汤姆猫跑酷","掉单","退款成功"});
//
//
//        try {
//            OutputStream out = new FileOutputStream("D://测试文件.csv");
//            CSVUtil.writeCSV(out,headers,contents);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


//        List<String[]> list = CSVUtil.readCSV("D:/小孩误充值王女士_20170807.csv", true);
//        // 遍历读取的CSV文件
//        for (String[] str : list) {
//            System.out.println(ArrayUtil.join(str));
//        }

//        try {
//            for (String[] strings : FileUtil.readTextForList("D:/3002 退款原因最多20个字符.txt",",")) {
//                System.out.println(ArrayUtil.join(strings));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        File f = new File("keyWord.txt");
//        System.out.println(f.getPath());
//        System.out.println(f.getAbsolutePath());//绝对路径

    }

}
