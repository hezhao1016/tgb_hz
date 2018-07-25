package com.hz.tgb.api.express;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 物流公司工具类
 *
 * Created by hezhao on 2018-07-25 17:46
 */
public class ExpressCompanyUtil {

    /** 物流公司列表 */
    private static final Map<String, String> COMPANY = new HashMap<>(50);

    static {
        initialCountryMap();
    }

    /**
     * 初始化国家信息键值对
     */
    private static void initialCountryMap() {
        COMPANY.put("SF", "顺丰速运");
        COMPANY.put("HTKY", "百世快递");
        COMPANY.put("ZTO", "中通快递");
        COMPANY.put("STO", "申通快递");
        COMPANY.put("YTO", "圆通速递");
        COMPANY.put("YD", "韵达速递");
        COMPANY.put("YZPY", "邮政快递包裹");
        COMPANY.put("EMS", "EMS");
        COMPANY.put("HHTT", "天天快递");
        COMPANY.put("JD", "京东物流");
        COMPANY.put("UC", "优速快递");
        COMPANY.put("DBL", "德邦快递");
        COMPANY.put("FAST", "快捷快递");
        COMPANY.put("ZJS", "宅急送");
        COMPANY.put("ANE", "安能物流");
        COMPANY.put("BTWL", "百世快运");
        COMPANY.put("GTO", "国通快递");
        COMPANY.put("KYSY", "跨越速运");
        COMPANY.put("KYWL", "跨越物流");
        COMPANY.put("QFKD", "全峰快递");
        COMPANY.put("RFD", "如风达");
        COMPANY.put("RRS", "日日顺物流");
        COMPANY.put("ZTKY", "中铁快运");
        COMPANY.put("ZTWL", "中铁物流");
        COMPANY.put("ZYWL", "中邮物流");
        COMPANY.put("ZTOKY", "中通快运");
        COMPANY.put("ZYKD", "中邮快递");
    }

    /**
     * 获取快递公司名称，如不存在返回code
     * @param code
     * @return
     */
    public static String getExpressCompanyName(String code) {
        if (StringUtils.isBlank(code)){
            return "";
        }
        if (COMPANY.containsKey(code)) {
            return COMPANY.get(code);
        }
        return code;
    }

    public static void main(String[] args) {
        String companyName = ExpressCompanyUtil.getExpressCompanyName("JD");
        System.out.println(companyName);
    }
}


// 来自快递鸟官网：http://www.kdniao.com/api-track
/*
快递公司     编码    轨迹查询
顺丰速运	    SF	   "支持（注：仅支持通过快递鸟下单接口<1007/1001>返回的顺丰单号查询）"
百世快递	    HTKY	支持（注：仅支持物流跟踪接口<1008/8008>查询）
中通快递	    ZTO	    支持
申通快递	    STO	    支持（注：仅支持付费开通的在途监控接口<8001/8008>查询）
圆通速递	    YTO    	支持
韵达速递	    YD	    支持
邮政快递包裹 	YZPY	支持
EMS	        EMS    	支持
天天快递	    HHTT	支持（注：仅支持付费开通的在途监控接口<8001/8008>查询）
京东物流    	JD	    支持
优速快递	    UC    	支持
德邦快递    	DBL	    支持
快捷快递	    FAST	支持
宅急送	    ZJS	    支持
安能物流	    ANE	    支持
百世快运    	BTWL	支持
国通快递    	GTO	    支持
跨越速运	    KYSY	支持
跨越物流	    KYWL	支持
全峰快递	    QFKD	支持
如风达	    RFD	    支持
日日顺物流	RRS	    支持
中铁快运    	ZTKY	支持
中铁物流    	ZTWL	支持
中邮物流	    ZYWL	支持
中通快运	    ZTOKY	支持
中邮快递    	ZYKD	支持

*/