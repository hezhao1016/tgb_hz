package com.hz.tgb.common.pojo;

/**
 * 经纬度坐标。
 *
 * @author hezhao
 * @date 2016年5月20日
 */
public class Point {
	/** 经度 */
	private double longitude;
	/** 纬度 */
	private double latitude;

	public Point() {
	}

	public Point(double longitude, double latitude) {
		this.longitude = longitude;
		this.longitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Point{" +
				"longitude=" + longitude +
				", latitude=" + latitude +
				'}';
	}
}