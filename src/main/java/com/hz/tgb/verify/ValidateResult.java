package com.hz.tgb.verify;

/**
 * 参数校验结果
 * 
 * @author Yaphis 2015年10月30日 下午8:27:31
 */
public class ValidateResult {

    /**
     * 是否通过 true是 false否
     */
    private boolean success;
    /**
     * 校验结果信息
     */
    private String resMsg;
    /**
     * 扩展信息
     */
    private Object extra;

    public ValidateResult() {
        this.success = true;
    }

    public ValidateResult fail(String resMsg) {
        this.success = false;
        this.resMsg = resMsg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "ValidateResult [success=" + success + ", resMsg=" + resMsg + ", extra=" + extra + "]";
    }
}
