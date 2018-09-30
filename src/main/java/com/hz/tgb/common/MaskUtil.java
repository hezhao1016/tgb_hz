package com.hz.tgb.common;

/** 打码工具类
 * @author hezhao
 */
public class MaskUtil {

    /***
     * 手机号打码处理
     * 
     * @param mobileNo
     * @return
     */
    public static String maskMobileNo(String mobileNo) {
        // 格式1XX******XX
        if (null == mobileNo || "".equals(mobileNo) || mobileNo.length() < 6) {
            // 判断请求是否为空,长度是否正确
            return mobileNo;
        }

        // 遮挡字符串
        String maskStr = getMaskStr(mobileNo.length() - 5);
        // 前缀
        String prefix = mobileNo.substring(0, 3);
        // 后缀
        String subfix = mobileNo.substring(mobileNo.length() - 2);

        return prefix + maskStr + subfix;
    }

    /***
     * 用户名打码处理
     * 
     * @param userName
     * @return
     */
    public static String maskUserName(String userName) {
        // 格式**X
        if (null == userName || "".equals(userName) || userName.length() < 2) {
            // 判断请求是否为空,长度是否正确
            return userName;
        }

        // 遮挡字符串
        String maskStr = getMaskStr(userName.length() - 1);
        // 后缀
        String subfix = userName.substring(userName.length() - 1);

        return maskStr + subfix;
    }

    /***
     * 身份证号打码处理
     * 
     * @param idNo
     * @return
     */
    public static String maskIdNo(String idNo) {
        // 格式X*************X
        if (null == idNo || "".equals(idNo) || idNo.length() < 3) {
            // 判断请求是否为空,长度是否正确
            return idNo;
        }

        // 遮挡字符串
        String maskStr = getMaskStr(idNo.length() - 2);
        // 前缀
        String prefix = idNo.substring(0, 1);
        // 后缀
        String subfix = idNo.substring(idNo.length() - 1);

        return prefix + maskStr + subfix;
    }

    /***
     * 银行卡号打码处理
     * 
     * @param bankCardNo
     * @return
     */
    public static String maskBankCardNo(String bankCardNo) {
        // 格式 **********XXXX
        if (null == bankCardNo || "".equals(bankCardNo) || bankCardNo.length() < 5) {
            // 判断请求是否为空,长度是否正确
            return bankCardNo;
        }

        // 遮挡字符串
        String maskStr = getMaskStr(bankCardNo.length() - 4);
        // 后缀
        String subfix = bankCardNo.substring(bankCardNo.length() - 4);

        return maskStr + subfix;
    }

    /***
     * 获取遮挡的字符串
     * 
     * @param len 长度
     * @return
     */
    private static String getMaskStr(int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append("*");
        }

        return sb.toString();
    }
}
