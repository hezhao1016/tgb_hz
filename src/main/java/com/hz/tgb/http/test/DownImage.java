package com.hz.tgb.http.test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 从网上下载图片
 * @author hezhao
 * @Time   2017年7月31日 下午4:38:41
 */
public class DownImage {
	/**
	 * 下载图片
	 * @author hezhao
	 * @Time   2017年7月31日 下午4:14:08
	 * @param destUrl
	 */
	public static void downImage(String destUrl) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream("F://signup.png");
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			
			System.out.println("下载成功！");
		} catch (Exception e) {
			
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (Exception e) {
				
			}
		}
	}

	public static void main(String[] args) {
		downImage("http://o2osite.xc580.net/ycxczone_imgs/2016/11/20161109171703012sys7033.jpg");  
	}
}
