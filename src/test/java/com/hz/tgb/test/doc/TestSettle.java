package com.hz.tgb.test.doc;

import com.hz.tgb.doc.ExportExcelByPoi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试结算管理导出
 */
public class TestSettle {
	public static void main(String[] args)  
    {  
        // 测试学生  
        ExportExcelByPoi<SettleAggregateExportDto> ex = new ExportExcelByPoi<SettleAggregateExportDto>();

        String[] headers = { "时间", "开发者ID", "企业名称", "纳税人类型", "业务类型", "账号状态", "申请结算时间", "审核通过时间", "结算状态", "订单流水","不分成可币券流水", "合作方分成金额"};
        List<SettleAggregateExportDto> dataset = new ArrayList<SettleAggregateExportDto>();

        SettleAggregateExportDto dto = new SettleAggregateExportDto("201707", "124356", "哇哈哈", "一般纳税人", "直充", "正常", "2017-08-11 21:12:35",
        		"2018-1-1 21:12:35", "等待审计审核", new BigDecimal(3059532230.15),new BigDecimal(3059532230.15),  new BigDecimal(3059532230.15));
        SettleAggregateExportDto dto1 = new SettleAggregateExportDto("201707", "124356", "哇哈哈", "一般纳税人", "直充", "正常", "2017-08-11 21:12:35",
        		"2018-1-1 21:12:35", "等待审计审核", new BigDecimal(3059532230.15), new BigDecimal(3059532230.15),  new BigDecimal(3059532230.15));
        SettleAggregateExportDto dto2 = new SettleAggregateExportDto("201707", "124356", "哇哈哈", "一般纳税人", "直充", "正常", "2017-08-11 21:12:35",
        		"2018-1-1 21:12:35", "等待审计审核", new BigDecimal(3059532230.15), new BigDecimal(3059532230.15),  new BigDecimal(12345D));
        dataset.add(dto);  
        dataset.add(dto1);  
        dataset.add(dto2);  
        
        //宽度
        Map<String,Integer> widths = new HashMap<String,Integer>();
        widths.put("settleMonth", 9);
        widths.put("userName", 25);
        widths.put("userTypeName", 12);
        widths.put("orderTypeName", 9);
        widths.put("isFreezeName", 9);
        widths.put("applyTime", 22);
        widths.put("checkTime", 22);
        
        try  
        {  
  
            OutputStream out = new FileOutputStream("E://a12xsd1得分1.xls");  
            
            ex.setDataset(dataset);
            ex.setHeaders(headers);
            ex.setOut(out);
//            ex.setAlign(HSSFCellStyle.ALIGN_LEFT);
            ex.setNeedBorder(true);
            ex.setWidthDefault(15);
            ex.setTitleFontSize(10);
            ex.setTitleFontBold(false);
            ex.setColumnFontSize(10);
            
            ex.createExcel("结算信息列表",widths);  
            out.close();  
//            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}
