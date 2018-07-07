package com.hz.tgb.verify;

/**
 * 参数校验结果
 * 
 * @author Yaphis 2017年1月22日 下午8:39:55
 */
public class ValidateResultT<T> extends ValidateResult {

    /**
     * 校验结果
     */
    public T result;

    public ValidateResultT<T> fail(T result, String resMsg) {
        this.result = result;
        super.fail(resMsg);
        return this;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ValidateResultT [" + (result != null ? "result=" + result + ", " : "") + (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
    }
}
