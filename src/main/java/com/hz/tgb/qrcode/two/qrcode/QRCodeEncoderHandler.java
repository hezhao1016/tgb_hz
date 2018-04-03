package com.hz.tgb.qrcode.two.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import com.swetake.util.Qrcode;

/**
 * 生成二维码
 * @author hezhao
 * @Time   20160107
 */
public class QRCodeEncoderHandler {

	public void encoderQRCode(String content, String imgPath) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('H');
			//N代表数字,A代表字符a-Z,B代表其他字符  
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大    
			qrcodeHandler.setQrcodeVersion(5);
			System.out.println(content);
			// int imgSize = 67 + 12 * (size - 1);
			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(115, 115,
			BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 115, 115);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = "
				+ contentBytes.length + " not in [ 0,120 ]. ");
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// 取当前时间为图片名称 带毫秒的
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date d = new Date();
		String str = sdf.format(d);
		String imgPath = "D:/" + str + ".png";
		String content = "www.sohu.com";
		QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
		handler.encoderQRCode(content, imgPath);
		System.out.println("imgPath:" + imgPath);
		System.out.println("encoder QRcode success");
	}

}