package com.hz.tgb.test.spiders.jiandanmeizi;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用HttpClient实现一个简单爬虫，抓取煎蛋妹子图
 * 
 * @author hezhao
 * @Time 2017年8月2日 上午10:07:55
 */
public class SimpleSpider {
	// 起始页码
	private static final int page = 1538;

	public static void main(String[] args) {
		// 线程池
		ExecutorService pool = Executors.newFixedThreadPool(10);
		try {
			// HttpClient 超时配置
			RequestConfig globalConfig = RequestConfig.custom()
					.setCookieSpec(CookieSpecs.STANDARD)
					.setConnectionRequestTimeout(6000).setConnectTimeout(6000)
					.build();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(globalConfig).build();
			System.out.println("3秒后开始抓取煎蛋妹子图……");
			Thread.sleep(3000);
			for (int i = page; i > 0; i--) {
				// 创建一个GET请求
				HttpGet httpGet = new HttpGet("http://jandan.net/ooxx/page-"
						+ i);
				httpGet.addHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
				httpGet.addHeader(
						"Cookie",
						"_gat=1; nsfw-click-load=off; gif-click-load=on; _ga=GA1.2.1861846600.1423061484");
				try {
					// 不敢爬太快
					// Thread.sleep(1000);
					// 发送请求，并执行
					CloseableHttpResponse response = httpClient
							.execute(httpGet);
					InputStream in = response.getEntity().getContent();
					String html = Utils.convertStreamToString(in);
					// 网页内容解析
					// new Thread(new JianDanHtmlParser(html, i)).start();

					// 线程池 添加线程
					pool.execute(new JianDanHtmlParser(html, i));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
	}
}
