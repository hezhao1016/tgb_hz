package com.hz.tgb.doc.test;

import com.hz.tgb.common.number.NumberUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算单下载 excel生成
 */
public class TestSettleOrder {

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String moneyUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "圆", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(零.)+", "");
		}
//		if (s.length() < 1) {
//			s = "整";
//		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零圆", "圆").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零")/*.replaceAll("^整$", "零圆整")*/;
	}
	
	public static void main(String[] args)  {
		List<SettleFileDto> list = new ArrayList<>();
		SettleFileDto sfd = new SettleFileDto();
		sfd.setSettleMonth("201707");
		sfd.setOrderType((byte) 0);
		sfd.setUserId(255155839);
		sfd.setProductName("手心的蔷薇");
		sfd.setUnitPrice(300);
		sfd.setBuyCount(8);
		sfd.setChannelCost(120L);
		sfd.setShareRatio(0.007);
		sfd.setPlatformCost(684L);
		sfd.setUserCost(1596L);
		
		sfd.setAppName("王者荣耀");
		sfd.setPrice(1000L);
		sfd.setNotClear(500L);
		sfd.setShareBaseCost(300L);
		sfd.setRatioCost(10L);
		sfd.setRealIncome(60L);
		sfd.setPackageName("com.oppo.pay.settlement");
		
		
		SettleFileDto sfd2 = new SettleFileDto();
		sfd2.setSettleMonth("201707");
		sfd2.setOrderType((byte) 0);
		sfd2.setUserId(255155839);
		sfd2.setProductName("手心的蔷薇2");
		sfd2.setUnitPrice(300);
		sfd2.setBuyCount(8);
		sfd2.setChannelCost(120L);
		sfd2.setShareRatio(0.007);
		sfd2.setPlatformCost(684L);
		sfd2.setUserCost(1596L);
		
		sfd2.setAppName("王者荣耀2");
		sfd2.setPrice(1000L);
		sfd2.setNotClear(500L);
		sfd2.setShareBaseCost(300L);
		sfd2.setRatioCost(10L);
		sfd2.setRealIncome(60L);
		sfd2.setPackageName("com.oppo.pay.settlement2");
		
		list.add(sfd);
		list.add(sfd2);
		
		//企业名称
		String userName = "XXX";
		//订单类型
		Byte orderType = 1;
		//类型名称
		String orderTypeName = changeOrderType(orderType);
		//合计
		Double amount = 0.0;
		String amountChinese = "";
		
		for (SettleFileDto dto : list) {
			Long money = 0L;
			if(orderType == 0 || orderType == 2 || orderType == 3){
				money = dto.getUserCost();
			}else if(orderType == 1 || orderType == 4){
				money = dto.getRealIncome();
			}
			if(money != null && money > 0L){
				Double dou = Double.valueOf(NumberUtil.moneyFenToYuan(money.toString()));
				amount += dou;
			}
		}
		amountChinese = moneyUppercase(amount);
		
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wkb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wkb.createSheet("结算单");
		// 设置表格默认列宽度 
        sheet.setDefaultColumnWidth(12);  
        
 		//在sheet里创建第一行  
		HSSFRow row0=sheet.createRow(0);   
		//设置高度
		row0.setHeight((short) (20 * 15));
		
		///////////////////订单类型 0下载付费 1游戏中心 2内嵌支付 3主题商店 4直充
		if(orderType == 0 || orderType == 3){/////////////////////////下载,主题商店
			// 创建单元格并设置单元格内容
			row0.createCell(0).setCellValue("结算月份");
			row0.createCell(1).setCellValue("账单类型");
			row0.createCell(2).setCellValue("真实ID");
			row0.createCell(3).setCellValue("商品名称");
			row0.createCell(4).setCellValue("价格");
			row0.createCell(5).setCellValue("销量");
			row0.createCell(6).setCellValue("销售额");
			row0.createCell(7).setCellValue("渠道成本费");
			row0.createCell(8).setCellValue("合作比例");
			row0.createCell(9).setCellValue("平台所得");
			row0.createCell(10).setCellValue("开发者所得");
			
			//设置各列宽度
	        sheet.setColumnWidth(0, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(1, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(2, (short) (35.7 * 14 * 7.666));  
	        sheet.setColumnWidth(3, (short) (35.7 * 21 * 7.666));  
	        sheet.setColumnWidth(6, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(7, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(8, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(9, (short) (35.7 * 15 * 7.666));  
			
			if (list.size() != 0) {
				SettleFileDto fileDto;
				
				Double unitPrice = 0D;
				Double saleMoney = 0D;
				Double channelCost = 0D;
				Double platformCost = 0D;
				Double userCost = 0D;
				
				for (int i = 0; i < list.size(); i++) {
					fileDto = list.get(i);
					unitPrice = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf((int)fileDto.getUnitPrice())));
					saleMoney = unitPrice * fileDto.getBuyCount();
					channelCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getChannelCost())));
					platformCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getPlatformCost())));
					userCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getUserCost())));
					
					HSSFRow row = sheet.createRow(i + 1);
					//设置高度
					row.setHeight((short) (20 * 15));
					
					row.createCell(0).setCellValue(fileDto.getSettleMonth());
					row.createCell(1).setCellValue(orderTypeName);
					row.createCell(2).setCellValue(fileDto.getUserId());
					row.createCell(3).setCellValue(fileDto.getProductName());
					row.createCell(4).setCellValue(unitPrice);				
					row.createCell(5).setCellValue(fileDto.getBuyCount());
					row.createCell(6).setCellValue(saleMoney);
					row.createCell(7).setCellValue(channelCost);				
					row.createCell(8).setCellValue(fileDto.getShareRatio());				
					row.createCell(9).setCellValue(platformCost);
					row.createCell(10).setCellValue(userCost);
				}
			}
			
		}else if(orderType == 1){////////////////////////////////游戏中心
			// 创建单元格并设置单元格内容
			row0.createCell(0).setCellValue("结算期");
			row0.createCell(1).setCellValue("结算游戏");
			row0.createCell(2).setCellValue("游戏流水");
			row0.createCell(3).setCellValue("不分成流水");
			row0.createCell(4).setCellValue("分成基数");
			row0.createCell(5).setCellValue("渠道成本");
			row0.createCell(6).setCellValue("税费");
			row0.createCell(7).setCellValue("分成比例");
			row0.createCell(8).setCellValue("合作方分成金额");
			
			//设置各列宽度
	        sheet.setColumnWidth(0, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(1, (short) (35.7 * 20 * 7.666));  
	        sheet.setColumnWidth(2, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(3, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(4, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(5, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(6, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(7, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(8, (short) (35.7 * 15 * 7.666));  
			
			if (list.size() != 0) {
				SettleFileDto fileDto;
				
				Double price = 0D;
				Double notClear = 0D;
				Double shareBaseCost = 0D;
				Double ratioCost = 0D;
				Double realIncome = 0D;
				Double channelCost = 0D;
				
				for (int i = 0; i < list.size(); i++) {
					fileDto = list.get(i);
					price = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getPrice())));
					notClear = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getNotClear())));
					shareBaseCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getShareBaseCost())));
					ratioCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getRatioCost())));
					realIncome = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getRealIncome())));
					channelCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getChannelCost())));
					
					HSSFRow row = sheet.createRow(i + 1);
					//设置高度
					row.setHeight((short) (20 * 15));
					
					row.createCell(0).setCellValue(fileDto.getSettleMonth());
					row.createCell(1).setCellValue(fileDto.getAppName());
					row.createCell(2).setCellValue(price);				
					row.createCell(3).setCellValue(notClear);				
					row.createCell(4).setCellValue(shareBaseCost);
					row.createCell(5).setCellValue(channelCost);				
					row.createCell(6).setCellValue(ratioCost);				
					row.createCell(7).setCellValue(fileDto.getShareRatio());				
					row.createCell(8).setCellValue(realIncome);
				}
			}
			
		}else if(orderType == 2){//////////////////////////////////内嵌支付
			// 创建单元格并设置单元格内容
			row0.createCell(0).setCellValue("结算月份");
			row0.createCell(1).setCellValue("账单类型");
			row0.createCell(2).setCellValue("真实ID");
			row0.createCell(3).setCellValue("包名");
			row0.createCell(4).setCellValue("销售额(元)");
			row0.createCell(5).setCellValue("渠道成本费");
			row0.createCell(6).setCellValue("合作比例");
			row0.createCell(7).setCellValue("平台所得");
			row0.createCell(8).setCellValue("开发者所得");
			
			//设置各列宽度
	        sheet.setColumnWidth(0, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(1, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(2, (short) (35.7 * 14 * 7.666));  
	        sheet.setColumnWidth(3, (short) (35.7 * 25 * 7.666));  
	        sheet.setColumnWidth(4, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(5, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(6, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(7, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(8, (short) (35.7 * 15 * 7.666));  
			
			if (list.size() != 0) {
				SettleFileDto fileDto;
				
				Double unitPrice = 0D;
				Double saleMoney = 0D;
				Double channelCost = 0D;
				Double platformCost = 0D;
				Double userCost = 0D;
				
				for (int i = 0; i < list.size(); i++) {
					fileDto = list.get(i);
					unitPrice = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf((int)fileDto.getUnitPrice())));
					saleMoney = unitPrice * fileDto.getBuyCount();
					channelCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getChannelCost())));
					platformCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getPlatformCost())));
					userCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getUserCost())));
					
					HSSFRow row = sheet.createRow(i + 1);
					//设置高度
					row.setHeight((short) (20 * 15));
					
					row.createCell(0).setCellValue(fileDto.getSettleMonth());
					row.createCell(1).setCellValue(orderTypeName);
					row.createCell(2).setCellValue(fileDto.getUserId());
					row.createCell(3).setCellValue(fileDto.getPackageName());
					row.createCell(4).setCellValue(saleMoney);
					row.createCell(5).setCellValue(channelCost);				
					row.createCell(6).setCellValue(fileDto.getShareRatio());				
					row.createCell(7).setCellValue(platformCost);
					row.createCell(8).setCellValue(userCost);
				}
			}
			
		}else if(orderType == 4){/////////////////////////////////////直充
			// 创建单元格并设置单元格内容
			row0.createCell(0).setCellValue("结算期");
			row0.createCell(1).setCellValue("名称");
			row0.createCell(2).setCellValue("流水");
			row0.createCell(3).setCellValue("不分成流水");
			row0.createCell(4).setCellValue("分成基数");
			row0.createCell(5).setCellValue("渠道成本");
			row0.createCell(6).setCellValue("税费");
			row0.createCell(7).setCellValue("分成比例");
			row0.createCell(8).setCellValue("合作方分成金额");
			
			//设置各列宽度
	        sheet.setColumnWidth(0, (short) (35.7 * 10 * 7.666));  
	        sheet.setColumnWidth(1, (short) (35.7 * 20 * 7.666));  
	        sheet.setColumnWidth(2, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(3, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(4, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(5, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(6, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(7, (short) (35.7 * 15 * 7.666));  
	        sheet.setColumnWidth(8, (short) (35.7 * 15 * 7.666));  
			
			if (list.size() != 0) {
				SettleFileDto fileDto;
				
				Double price = 0D;
				Double notClear = 0D;
				Double shareBaseCost = 0D;
				Double ratioCost = 0D;
				Double realIncome = 0D;
				Double channelCost = 0D;
				
				for (int i = 0; i < list.size(); i++) {
					fileDto = list.get(i);
					price = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getPrice())));
					notClear = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getNotClear())));
					shareBaseCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getShareBaseCost())));
					ratioCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getRatioCost())));
					realIncome = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getRealIncome())));
					channelCost = Double.valueOf(NumberUtil.moneyFenToYuan(String.valueOf(fileDto.getChannelCost())));
					
					HSSFRow row = sheet.createRow(i + 1);
					//设置高度
					row.setHeight((short) (20 * 15));
					
					row.createCell(0).setCellValue(fileDto.getSettleMonth());
					row.createCell(1).setCellValue(fileDto.getProductName());
					row.createCell(2).setCellValue(price);				
					row.createCell(3).setCellValue(notClear);				
					row.createCell(4).setCellValue(shareBaseCost);
					row.createCell(5).setCellValue(channelCost);				
					row.createCell(6).setCellValue(ratioCost);				
					row.createCell(7).setCellValue(fileDto.getShareRatio());				
					row.createCell(8).setCellValue(realIncome);
				}
			}
			
		}

		//////////////////////////////以下是静态内容
		
		int size0 = list.size();
		int size = list.size()+3;
		
		if(list.size() == 0){
			size0 += 1;
			size += 1;
		}
		
		HSSFRow row_amount = sheet.createRow(size0+1);   
		row_amount.setHeight((short) (20 * 15));
		row_amount.createCell(0).setCellValue("合计（小写）: "+amount);
		
		HSSFRow row_amountChinese = sheet.createRow(size0+2);   
		row_amountChinese.setHeight((short) (20 * 15));
		row_amountChinese.createCell(0).setCellValue("支付金额（大写）："+amountChinese);
		
		HSSFRow row_taxName = sheet.createRow(size+1);   
		row_taxName.setHeight((short) (20 * 15));
		row_taxName.createCell(0).setCellValue("纳税人名称：东莞市讯怡电子科技有限公司");
		row_taxName.createCell(5).setCellValue("收款信息："+userName);
		
		HSSFRow row_taxNo = sheet.createRow(size+2);   
		row_taxNo.setHeight((short) (20 * 15));
		row_taxNo.createCell(0).setCellValue("纳税识别号：9144 1900 0734 7438 9D");
		
		HSSFRow row_ticketContent = sheet.createRow(size+3);   
		row_ticketContent.setHeight((short) (20 * 15));
		row_ticketContent.createCell(0).setCellValue("开票内容：信息服务费");
		row_ticketContent.createCell(5).setCellValue("开户银行：");
		
		HSSFRow row_openBank = sheet.createRow(size+4);   
		row_openBank.setHeight((short) (20 * 15));
		row_openBank.createCell(0).setCellValue("开户银行：东莞建行长安支行");
		row_openBank.createCell(5).setCellValue("帐号：");
		
		HSSFRow row_bankNo = sheet.createRow(size+5);   
		row_bankNo.setHeight((short) (20 * 15));
		row_bankNo.createCell(0).setCellValue("银行帐号：4400 1779 1080 5302 0863");
		row_bankNo.createCell(5).setCellValue("业务经办人：");
		
		HSSFRow row_registerAddress = sheet.createRow(size+6);   
		row_registerAddress.setHeight((short) (20 * 15));
		row_registerAddress.createCell(0).setCellValue("注册地址：东莞市长安镇乌沙社区兴一路372号二楼202室");
		row_registerAddress.createCell(5).setCellValue("确认盖章：");
		
		HSSFRow row_Phone = sheet.createRow(size+7);   
		row_Phone.setHeight((short) (20 * 15));
		row_Phone.createCell(0).setCellValue("电话号码：0769-82926431");
		row_Phone.createCell(5).setCellValue("联系人及电话：");
		
		HSSFRow row_invoice = sheet.createRow(size+8);   
		row_invoice.setHeight((short) (20 * 15));
		row_invoice.createCell(0).setCellValue("发票邮寄资料：");
		row_invoice.createCell(5).setCellValue("邮寄地址：");
		
		HSSFRow row_mailAddress = sheet.createRow(size+9);   
		row_mailAddress.setHeight((short) (20 * 15));
		row_mailAddress.createCell(0).setCellValue("邮寄地址：深圳市南山区海德三道126号卓越后海金融中心8楼 ");
		
		HSSFRow row_nsr = sheet.createRow(size+10);   
		row_nsr.setHeight((short) (20 * 15));
		row_nsr.createCell(0).setCellValue("收件人：卜振中");
		
		HSSFRow row_mobilePhone = sheet.createRow(size+11);   
		row_mobilePhone.setHeight((short) (20 * 15));
		row_mobilePhone.createCell(0).setCellValue("联系电话：15899917712");
		
		HSSFRow row_notice = sheet.createRow(size+13);   
		row_notice.setHeight((short) (20 * 15));
		row_notice.createCell(0).setCellValue("注意：");
		
		HSSFRow row_notice_one = sheet.createRow(size+14);   
		row_notice_one.setHeight((short) (20 * 15));
		row_notice_one.createCell(0).setCellValue("1、已抵扣6.6%的税费，请开具6个点的增值税专用发票；");
		
		HSSFRow row_notice_two = sheet.createRow(size+15);   
		row_notice_two.setHeight((short) (20 * 15));
		row_notice_two.createCell(0).setCellValue("2、如果提供增值税普通发票或者地税发票，我们将拒绝接收，游戏流水扣除渠道费用和税费后重新结算。");
		
		HSSFRow row_notice_three = sheet.createRow(size+16);   
		row_notice_three.setHeight((short) (20 * 15));
		row_notice_three.createCell(0).setCellValue("3、结算需要提供一份盖章的结算单和增值税专用发票。");
		
		HSSFRow row_notice_four = sheet.createRow(size+17);   
		row_notice_four.setHeight((short) (20 * 15));
		row_notice_four.createCell(0).setCellValue("4、我们在收到贵公司的结算单（需盖章确认）和发票后，将走付款流程；");
		
		HSSFRow row_notice_five = sheet.createRow(size+18);   
		row_notice_five.setHeight((short) (20 * 15));
		row_notice_five.createCell(0).setCellValue("5、我方收到发票后10个工作日付款。（付款时间段为每个月的第16个工作日至次月的第六个工作日） ");
		 
		// 输出Excel文件
		try {
			OutputStream out = new FileOutputStream("E://order("+orderType+").xls");  
			wkb.write(out);
			wkb.close();
			System.out.println("ecport excel success !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String changeOrderType(Byte orderType){
		String orderTypeStr = "";
		
		if(orderType != null){
			//订单类型 0下载付费 1游戏中心 2内嵌支付 3主题商店 4直充
			if(orderType == 0){
				orderTypeStr = "下载付费";
			}else if(orderType == 1){
				orderTypeStr = "游戏中心";
			}else if(orderType == 2){
				orderTypeStr = "内嵌支付";
			}else if(orderType == 3){
				orderTypeStr = "主题商店";
			}else if(orderType == 4){
				orderTypeStr = "直充";
			}
		}
		return orderTypeStr;
	}
	
}
