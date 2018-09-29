package com.hz.tgb.api.invoice.pdf.entity;

/**
 * 发票XY轴坐标定位
 *
 * Created by hezhao on 2018/9/18 16:12
 */
public class ReceiptPosition {

    private float posX = 0;

    private float posY = 0;

    private float posLastX = 0;
    /**
     * 结束x
     */
    private float posEndX = 0;

    private float posLastY = 0;
    /**
     * 结束y
     */
    private float posEndY = 0;

    private String text;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosLastX() {
        return posLastX;
    }

    public void setPosLastX(float posLastX) {
        this.posLastX = posLastX;
    }

    public float getPosLastY() {
        return posLastY;
    }

    public void setPosLastY(float posLastY) {
        this.posLastY = posLastY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the posEndX
     */
    public float getPosEndX() {
        return posEndX;
    }

    /**
     * @param posEndX the posEndX to set
     */
    public void setPosEndX(float posEndX) {
        this.posEndX = posEndX;
    }

    /**
     * @return the posEndY
     */
    public float getPosEndY() {
        return posEndY;
    }

    /**
     * @param posEndY the posEndY to set
     */
    public void setPosEndY(float posEndY) {
        this.posEndY = posEndY;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ReceiptPosition [posEndX=" + posEndX + ", posEndY=" + posEndY
                + ", posLastX=" + posLastX + ", posLastY=" + posLastY
                + ", posX=" + posX + ", posY=" + posY + ", text=" + text + "]";
    }

}