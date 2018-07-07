package com.hz.tgb.common.pojo;

import java.math.BigDecimal;

/**
 * 经纬度坐标。
 * 
 * @author hezhao
 * @date 2016年5月20日
 */
public class Point {
	private BigDecimal lat; // 纬度
	private BigDecimal lon; // 经度

	public Point(BigDecimal lat, BigDecimal lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "lon:" + lon + ",lat:" + lat;
	}
}