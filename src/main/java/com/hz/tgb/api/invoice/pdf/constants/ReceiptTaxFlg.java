package com.hz.tgb.api.invoice.pdf.constants;

/**
 * 发票类型
 *
 * 01 增值税专用发票<br>
 * 02 货物运输业增值税专用发票<br>
 * 03 机动车销售统一发票<br>
 * 04 增值税普通发票<br>
 * 10 增值税电子普通发票<br>
 * 11 增值税普通发票（卷票）<br>
 *
 * @author 
 * @since 2015年06月08日
 */
public final class ReceiptTaxFlg {
	
	/**
	 * 增值税专用发票
	 */
	public static final String ZHUANYONG = "01";

	/**
	 * 货物运输业增值税专用发票
	 */
	public static final String YUNSHU = "02";

	/**
	 * 机动车销售统一发票
	 */
	public static final String VEHICLE = "03";

	/**
	 * 增值税普通发票
	 */
	public static final String PUTONG = "04";

	/**
	 * 增值税电子普通发票
	 */
	public static final String PUTONG_DIANZI = "10";
	
	/**
	 * 增值税普通发票（卷票）
	 */
	public static final String PUTONG_JUANSHI = "11";
	

	/** 是否专票 */
	public static boolean getTaxFlg(String receiptTaxFlg) {
		if (ZHUANYONG.equals(receiptTaxFlg)) {
			return true;
		}

		return false;
	}

	/** 专票/普票，(0-普票，1-专票) */
	public static Integer getSpecialFlag(String receiptTaxFlg) {
		if (ZHUANYONG.equals(receiptTaxFlg)) {
			return 1;
		}

		return 0;
	}
	
}