package com.hz.tgb.common;

import com.hz.tgb.common.pojo.Point;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * 地图工具类
 *
 * @author hezhao
 * @Time 2017年7月31日 下午8:38:50
 */
public class MapUtil {

	/** 赤道半径(单位m) */
	private static final double EARTH_RADIUS = 6378137;
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	/**
	 * 转化为弧度(rad)
	 * @param d 经度或纬度
	 * @return
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算经度与谷歌地图的距离经度差不多，相差范围在0.2米以下
	 *
	 * @param lon1
	 *            第一点的经度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的经度
	 * @param lat2
	 *            第二点的纬度
	 * @return 返回的距离，单位:千米(km)
	 * */
	public static double distanceKm(double lon1, double lat1, double lon2, double lat2) {
		return MapUtil.distanceM(lon1,lat1,lon2,lat2) / 1000;
	}

	/**
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算经度与谷歌地图的距离经度差不多，相差范围在0.2米以下
	 *
	 * @param lon1
	 *            第一点的经度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的经度
	 * @param lat2
	 *            第二点的纬度
	 * @return 返回的距离，单位:米(m)
	 * */
	public static double distanceM(double lon1, double lat1, double lon2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 坐标转换，腾讯地图转换成百度地图坐标
	 *
	 * @param lat
	 *            腾讯纬度
	 * @param lon
	 *            腾讯经度
	 * @return 返回结果：经度,纬度
	 */
	public static Point map_tx2bd(double lon, double lat) {
		double bd_lat;
		double bd_lon;
		double x = lon, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		bd_lon = z * Math.cos(theta) + 0.0065;
		bd_lat = z * Math.sin(theta) + 0.006;

		return new Point(bd_lon, bd_lat);
	}

	/**
	 * 坐标转换，百度地图坐标转换成腾讯地图坐标
	 *
	 * @param lat
	 *            百度坐标纬度
	 * @param lon
	 *            百度坐标经度
	 * @return 返回结果：纬度,经度
	 */
	public static Point map_bd2tx(double lon, double lat) {
		double tx_lat;
		double tx_lon;
		double x = lon - 0.0065, y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		tx_lon = z * Math.cos(theta);
		tx_lat = z * Math.sin(theta);
		return new Point(tx_lon, tx_lat);
	}

	/**
	 * 根据城市地区查询经纬度信息
	 * @param city
	 *            城市中文名称
	 * @return String
	 */
	public static String findLatLng(String city) {
		if (null != city && !"".equals(city)) {
			StringBuilder resultBuilder = new StringBuilder();
			Document doc;
			try {
				String address = URLEncoder.encode(city, "utf-8");
				doc = Jsoup
						.connect(
								"http://api.map.baidu.com/geocoder?address="
										+ address
										+ "&output=xml&key=37492c0ee6f924cb5e934fa08c6b1676")
						.timeout(30000).get();
				if (null != doc) {
					Elements location = doc.select("body")
							.select("geocodersearchresponse").select("result")
							.select("location");
					if (location != null && !"".equals(location)) {
						if (location.select("lng").text() != null
								&& !location.select("lng").text().equals("")) {
							resultBuilder.append(location.select("lng").text()
									+ ",");
							resultBuilder.append(location.select("lat").text());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (resultBuilder.toString().indexOf(",") != -1) {
				return resultBuilder.toString();
			} else {
				return "114.025974,22.546054";// 默认深圳经纬度
			}
		}

		return "114.025974,22.546054";// 默认深圳经纬度
	}

	public static void main(String[] args) {
		// 济南国际会展中心经纬度：117.11811 36.68484
		// 趵突泉：117.00999000000002 36.66123
		System.out.println(distanceM(117.11811, 36.68484, 117.00999000000002, 36.66123));
		System.out.println(distanceKm(117.11811, 36.68484, 117.00999000000002, 36.66123));
		System.out.println(findLatLng("上海"));
	}
}