package com.hz.tgb.test.doc;

import com.hz.tgb.common.StringUtil;
import com.hz.tgb.common.datetime.DateUtil;
import com.hz.tgb.common.number.AmountUtil;
import com.hz.tgb.doc.ExportExcelByPoi;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hezhao on 2017-09-20 09:35.
 */
public class TestSettleSummary {
    public static void main(String[] args) {
        try {
            //get list
            List<SettleAggregateDto> aggregateInfos = new SettleAggDaoImpl().getReleaseInfo();

            List<SettleSummaryExportDto> resultList = new ArrayList<>();
            String orderTypeName = changeOrderType((byte)1);
            for(SettleAggregateDto agg : aggregateInfos){
                /*
                说明：
                1.订单总流水=交易总金额+退款总金额
                2.交易总金额为所有交易订单金额总和（不包括退款订单）
                3.退款总金额为所有退款订单总金额
                4.销售额=交易总金额-分成可币券流水-不可分成可币券流水
                5.分成可币券流水指所有游戏订单使用分成可币券的总金额，即xx承担成本，CP不承担成本的可币券
                6.不分成可币券流水指所有游戏订单使用不分成可币券的总金额，即xx和CP共同承担成本的可币券
                7.总分成基数=交易总金额-不分成可币券总流水
                8.结算总金额=总分成基数-渠道总成本-总税费
                9.总利润=销售额-合作方分成金额
                10.合作方分成金额=结算总金额*分成比例
                */

                SettleSummaryExportDto value = new SettleSummaryExportDto();

                String settleMonth = StringUtil.isBlank(agg.getSettleMonth()) ? "" : agg.getSettleMonth();
                String price = agg.getPrice() == null ? "0" : AmountUtil.moneyFenToYuan(agg.getPrice().toString());
                String refundTotal = agg.getRefundTotal() == null ? "0" : AmountUtil.moneyFenToYuan(agg.getRefundTotal().toString());
                String clearAmount = agg.getClearAmount() == null ? "0" : AmountUtil.changeF2YRound(agg.getClearAmount().toString());
                String notClear = agg.getNotClear() == null ? "0": AmountUtil.changeF2YRound(agg.getNotClear().toString());
                String channelCost = agg.getChannelCost() == null ? "0": AmountUtil.changeF2YRound(agg.getChannelCost().toString());
                String taxCost = agg.getTaxCost() == null ? "0" : AmountUtil.changeF2YRound(agg.getTaxCost().toString());

                BigDecimal check_price = agg.getPrice() == null ? new BigDecimal(0) : new BigDecimal(agg.getPrice());
                BigDecimal check_clearAmount = agg.getClearAmount() == null ? new BigDecimal(0) : agg.getClearAmount();
                BigDecimal check_notClear = agg.getNotClear() == null ? new BigDecimal(0) : agg.getNotClear();
                BigDecimal check_refundTotal = agg.getRefundTotal() == null ? new BigDecimal(0) : new BigDecimal(agg.getRefundTotal());
                BigDecimal check_channelCost = agg.getChannelCost() == null ? new BigDecimal(0) : agg.getChannelCost();
                BigDecimal check_taxCost = agg.getTaxCost() == null ? new BigDecimal(0) : agg.getTaxCost();
                BigDecimal check_realIncome = agg.getRealIncome() == null ? new BigDecimal(0) : agg.getRealIncome();

                String salesVolume = AmountUtil.changeF2YRound((check_price .subtract( check_refundTotal ).subtract( check_clearAmount ).subtract( check_notClear).doubleValue())+"");
                String dividedProportion = AmountUtil.changeF2YRound((check_price .subtract( check_refundTotal ).subtract( check_notClear).doubleValue()) + "");
                String settleAmount = AmountUtil.changeF2YRound((check_price .subtract( check_refundTotal ).subtract( check_notClear ).subtract( check_channelCost ).subtract( check_taxCost).doubleValue()) + "");
                String allIncome = AmountUtil.changeF2YRound((check_price .subtract( check_refundTotal ).subtract( check_clearAmount ).subtract( check_notClear ).subtract( check_realIncome).doubleValue()) + "");
                String traleTotal = AmountUtil.changeF2YRound((check_price .subtract( check_refundTotal).doubleValue()) + "");

                String allPlayMoney = agg.getRealIncome() == null ? "0" : AmountUtil.changeF2YRound((agg.getRealIncome().doubleValue()) + "");
                String statusName = changeStatus(agg.getStatus());

                value.setSettleMonth(settleMonth);
                value.setOrderTypeName(orderTypeName);
                value.setPrice(new BigDecimal(price));
                value.setTraleTotal(new BigDecimal(traleTotal));
                value.setRefundTotal(new BigDecimal(refundTotal));
                value.setSalesVolume(new BigDecimal(salesVolume));
                value.setClearAmount(new BigDecimal(clearAmount));
                value.setNotClear(new BigDecimal(notClear));
                value.setDividedProportion(new BigDecimal(dividedProportion));
                value.setChannelCost(new BigDecimal(channelCost));
                value.setTaxCost(new BigDecimal(taxCost));
                value.setSettleAmount(new BigDecimal(settleAmount));
                value.setAllIncome(new BigDecimal(allIncome));
                value.setAllPlayMoney(new BigDecimal(allPlayMoney));
                value.setStatus(statusName);

                resultList.add(value);
            }

            ExportExcelByPoi<SettleSummaryExportDto> ex = new ExportExcelByPoi<SettleSummaryExportDto>();

            String[] headers = { "月份", "业务类型", "订单总流水","交易总金额","退款总金额", "销售额", "分成可币券流水", "不分成可币券流水", "总分成基数", "渠道费", "总税费", "结算总金额", "总利润", "合作方分成金额","状态"};

            //宽度
            Map<String,Integer> widths = new HashMap<String,Integer>();
            widths.put("settleMonth", 9);
            widths.put("orderTypeName", 10);
            widths.put("status", 13);

            // 输出Excel文件
            try {

                //文件名  结算汇总2017-08-10 11-54-01.xls
                String now = DateUtil.format(new Date(), "yyyy-MM-dd HH-mm-ss");
                String fileName = "结算汇总"+now+".xls";

                OutputStream out = new FileOutputStream("E://"+fileName);

                ex.setDataset(resultList);
                ex.setHeaders(headers);
                ex.setOut(out);
//            ex.setAlign(HSSFCellStyle.ALIGN_LEFT);
                ex.setNeedBorder(true);
                ex.setWidthDefault(16);
                ex.setTitleFontSize(10);
                ex.setTitleFontBold(false);
                ex.setColumnFontSize(10);
                ex.setColumnHeight(15);
                ex.setTitleHeight(15);

                ex.createExcel("结算汇总",widths);
                out.close();
//            JOptionPane.showMessageDialog(null, "导出成功!");
                System.out.println("excel导出成功！");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String changeStatus(Byte status){
        String statusStr = "";
        if(status != null){
            //结算状态 0数据未汇总 1未申请 2 等待审计审核 3 等待打款 4 已打款 5结算失败 6 等待财务审核
            if(status == 0) {
                statusStr =  "未发布";
            }else{
                statusStr =  "已发布";
            }
//            if(status == 0){
//                statusStr = "数据未汇总";
//            }else if(status == 1){
//                statusStr = "未申请";
//            }else if(status == 2){
//                statusStr = "等待审计审核";
//            }else if(status == 3){
//                statusStr = "等待打款";
//            }else if(status == 4){
//                statusStr = "已打款";
//            }else if(status == 5){
//                statusStr = "结算失败";
//            }else if(status == 6) {
//                statusStr = "等待财务审核";
//            }
        }
        return statusStr;
    }

    private static String changeOrderType(Byte orderType){
        String orderTypeStr = "";

        if(orderType != null){
            //订单类型 1游戏中心 3主题商店 4直充业务 5第三方数据
            if(orderType == 1){
                orderTypeStr = "游戏中心";
            }else if(orderType == 3){
                orderTypeStr = "主题商店";
            }else if(orderType == 4){
                orderTypeStr = "直充业务";
            }else if(orderType == 5){
                orderTypeStr = "第三方数据";
            }
        }
        return orderTypeStr;
    }
}
