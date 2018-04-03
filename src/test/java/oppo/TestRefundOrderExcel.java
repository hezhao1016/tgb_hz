package oppo;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezhao on 2017/9/25 15:12.
 */
public class TestRefundOrderExcel {

    public static void main(String[] args)  {
        List huizong_list = new ArrayList(2);
        huizong_list.add(1);
        huizong_list.add(1);
        List order_list = new ArrayList(10);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);
        order_list.add(1);




        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wkb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wkb.createSheet("退款订单");
        // 设置表格默认列宽度
        sheet.setDefaultColumnWidth(12);

        //设置各列宽度
        sheet.setColumnWidth(0, (short) (35.7 * 38 * 7.666));
        sheet.setColumnWidth(1, (short) (35.7 * 35 * 7.666));
        sheet.setColumnWidth(2, (short) (35.7 * 24 * 7.666));
        sheet.setColumnWidth(3, (short) (35.7 * 15 * 7.666));
        sheet.setColumnWidth(4, (short) (35.7 * 15 * 7.666));
        sheet.setColumnWidth(5, (short) (35.7 * 10 * 7.666));
        sheet.setColumnWidth(6, (short) (35.7 * 10 * 7.666));
        sheet.setColumnWidth(7, (short) (35.7 * 13 * 7.666));
        sheet.setColumnWidth(8, (short) (35.7 * 25 * 7.666));
        sheet.setColumnWidth(9, (short) (35.7 * 10 * 7.666));


        //////////////////////////////在sheet里创建第一行
        HSSFRow row_0 = sheet.createRow(0);
//        row_0.setHeight((short) (20 * 15));
        HSSFCell cell_0 = row_0.createCell(0);
        cell_0.setCellValue("汇总数据");

        // 生成样式
        HSSFCellStyle style_0 = wkb.createCellStyle();

        //生成一个字体
        HSSFFont font2 = wkb.createFont();
        font2.setFontName("宋体");
        font2.setColor(HSSFColor.BLACK.index);
        font2.setFontHeightInPoints((short)12);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式
        style_0.setFont(font2);
        style_0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell_0.setCellStyle(style_0);

        // 合并单元格
        CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 2); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);

        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cra, sheet,wkb); // 下边框
        RegionUtil.setBorderLeft(1, cra, sheet,wkb); // 左边框
        RegionUtil.setBorderRight(1, cra, sheet,wkb); // 有边框
        RegionUtil.setBorderTop(1, cra, sheet,wkb); // 上边框
        ///////////////////////////////////////

        HSSFCellStyle style_title = createTitleStyle(wkb);
        HSSFCellStyle style_column = createColumnStyle(wkb);

        HSSFRow row_1=sheet.createRow(1);
        //设置高度
//        row_1.setHeight((short) (20 * 15));
        // 创建单元格并设置单元格内容
        HSSFCell cell_hz_0 = row_1.createCell(0);
        HSSFCell cell_hz_1 = row_1.createCell(1);
        HSSFCell cell_hz_2 = row_1.createCell(2);

        cell_hz_0.setCellStyle(style_title);
        cell_hz_1.setCellStyle(style_title);
        cell_hz_2.setCellStyle(style_title);

        cell_hz_0.setCellValue("应用名称");
        cell_hz_1.setCellValue("总退款订单数");
        cell_hz_2.setCellValue("总退款金额");

        if (order_list.size() != 0) {

            for (int i = 0; i < huizong_list.size(); i++) {

                HSSFRow row = sheet.createRow(i + 2);
                //设置高度
//                row.setHeight((short) (20 * 15));

                HSSFCell c_0 = row.createCell(0);
                HSSFCell c_1 = row.createCell(1);
                HSSFCell c_2 = row.createCell(2);

                c_0.setCellStyle(style_column);
                c_1.setCellStyle(style_column);
                c_2.setCellStyle(style_column);

                c_0.setCellValue("汤姆猫跑酷");
                c_1.setCellValue("10");
                c_2.setCellValue("50");
            }
        }


        //////////////////////////////以下是静态内容

        int start_size = huizong_list.size()+5;

        HSSFRow row_2 = sheet.createRow(start_size);
        //设置高度
        row_1.setHeight((short) (20 * 15));
        // 创建单元格并设置单元格内容
        HSSFCell cell_order_0 = row_2.createCell(0);
        HSSFCell cell_order_1 = row_2.createCell(1);
        HSSFCell cell_order_2 = row_2.createCell(2);
        HSSFCell cell_order_3 = row_2.createCell(3);
        HSSFCell cell_order_4 = row_2.createCell(4);
        HSSFCell cell_order_5 = row_2.createCell(5);
        HSSFCell cell_order_6 = row_2.createCell(6);
        HSSFCell cell_order_7 = row_2.createCell(7);
        HSSFCell cell_order_8 = row_2.createCell(8);
        HSSFCell cell_order_9 = row_2.createCell(9);

        cell_order_0.setCellStyle(style_title);
        cell_order_1.setCellStyle(style_title);
        cell_order_2.setCellStyle(style_title);
        cell_order_3.setCellStyle(style_title);
        cell_order_4.setCellStyle(style_title);
        cell_order_5.setCellStyle(style_title);
        cell_order_6.setCellStyle(style_title);
        cell_order_7.setCellStyle(style_title);
        cell_order_8.setCellStyle(style_title);
        cell_order_9.setCellStyle(style_title);

        cell_order_0.setCellValue("支付订单号");
        cell_order_1.setCellValue("商户订单号");
        cell_order_2.setCellValue("支付时间");
        cell_order_3.setCellValue("ssoid");
        cell_order_4.setCellValue("imei");
        cell_order_5.setCellValue("订单金额");
        cell_order_6.setCellValue("退款金额");
        cell_order_7.setCellValue("支付渠道");
        cell_order_8.setCellValue("应用名称");
        cell_order_9.setCellValue("退款原因");

        if (order_list.size() != 0) {

            for (int i = 0; i < order_list.size(); i++) {

                HSSFRow row = sheet.createRow(i + start_size + 1);
                //设置高度
                row.setHeight((short) (20 * 15));

                HSSFCell c_0 = row.createCell(0);
                HSSFCell c_1 = row.createCell(1);
                HSSFCell c_2 = row.createCell(2);
                HSSFCell c_3 = row.createCell(3);
                HSSFCell c_4 = row.createCell(4);
                HSSFCell c_5 = row.createCell(5);
                HSSFCell c_6 = row.createCell(6);
                HSSFCell c_7 = row.createCell(7);
                HSSFCell c_8 = row.createCell(8);
                HSSFCell c_9 = row.createCell(9);

                c_0.setCellStyle(style_column);
                c_1.setCellStyle(style_column);
                c_2.setCellStyle(style_column);
                c_3.setCellStyle(style_column);
                c_4.setCellStyle(style_column);
                c_5.setCellStyle(style_column);
                c_6.setCellStyle(style_column);
                c_7.setCellStyle(style_column);
                c_8.setCellStyle(style_column);
                c_9.setCellStyle(style_column);

                c_0.setCellValue("RM2017091406320320FKYT0Y1A105688");
                c_1.setCellValue("GC201709191358238200200000000");
                c_2.setCellValue("2017/9/22  13:58:27");
                c_3.setCellValue("123465");
                c_4.setCellValue("86561312465");
                c_5.setCellValue("5");
                c_6.setCellValue("5 ");
                c_7.setCellValue("现在支付");
                c_8.setCellValue("汤姆猫跑酷");
                c_9.setCellValue("掉单");
            }
        }

        // 输出Excel文件
        try {
            OutputStream out = new FileOutputStream("D://退款订单1.xls");
            wkb.write(out);
            wkb.close();
            System.out.println("ecport excel success !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *数据栏样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createColumnStyle(HSSFWorkbook workbook) {
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setColor(HSSFColor.BLACK.index);
        font2.setFontHeightInPoints((short)11);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        return style2;
    }


    /**
     * 标题栏样式
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createTitleStyle(HSSFWorkbook workbook) {
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //对齐方式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short)11);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }
}
