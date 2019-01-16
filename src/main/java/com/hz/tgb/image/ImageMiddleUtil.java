package com.hz.tgb.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片压缩和水印
 *
 * Created by hezhao on 2018-06-13 16:33
 */
public class ImageMiddleUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImageMiddleUtil.class);

	/**
	 * 图片压缩方法
	 * @param url
	 * 			图片地址
	 * @param savePath
	 * 			保存路径
	 * @param picW
	 * 			目标宽
	 * @param picH
	 * 			目标高
	 * @param percent
	 * 			百分比
	 * @param warterMark
	 * 			水印文字
	 * @param fontface
	 *			水印文字字体
	 * @param fontsize
	 *			水印文字字体大小
	 * @return
	 */
	public static FileOutputStream imgChange(String url, String savePath, int picW, int picH, Float percent,
											  String warterMark, String fontface, Integer fontsize) {
		File f = new File(url);
		// 如果未指定缩放比例、则自动计算
		float per;
		if (percent == null || "".equals(percent)) {
			per = (float) (picH * picW) / f.length(); // 图片压缩成H*W/返回由此抽象路径名表示的文件的大小（byte）
			if (f.length() > picH * picW) {
				per = (float) (per + (1 - per) / 5);// 不一定合适
			}
		} else {
			per = percent;
		}
		return tosmallerpic(url, new File(url), savePath, null, picW, picH,
				(float) per, warterMark, fontface, fontsize); // 固定压缩比例图片不易失真
	}

	/**
	 * 
	 * @param f
	 *            图片所在的文件夹路径
	 * @param filelist
	 *            图片路径
	 * @param savePath
	 *            保存路径
	 * @param n
	 *            图片名
	 * @param w
	 *            目标宽
	 * @param h
	 *            目标高
	 * @param per
	 *            百分比
	 * @param warterMark
	 *            水印文字
	 * @param fontface
	 *            水印文字字体
	 * @param fontsize
	 *            水印文字字体大小
	 */
	private static FileOutputStream tosmallerpic(String f, File filelist, String savePath, String n, int w, int h, float per,
												 String warterMark, String fontface, Integer fontsize) {
		Image src = null;
		FileOutputStream newimage = null;
		ImageOutputStream ios = null;
		try {
			src = ImageIO.read(filelist); // 构造Image对象

			String imgMidname = savePath;
			int old_w = src.getWidth(null); // 得到源图宽
			int old_h = src.getHeight(null); // 得到源图高
			int new_w = 0;
			int new_h = 0;

			double w2 = (old_w * 1.00) / (w * 1.00);
			double h2 = (old_h * 1.00) / (h * 1.00);

			// 图片跟据长宽留留白，成一个正方形图。
			BufferedImage oldpic;
			oldpic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = oldpic.createGraphics();
			g.setColor(Color.white);

			if (old_w > old_h) {
				g.fillRect(0, 0, old_w, old_w);
				g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
			} else {
				if (old_w < old_h) {
					g.fillRect(0, 0, old_h, old_h);
					g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
				} else {
					g.drawImage(src.getScaledInstance(old_w, old_h,
							Image.SCALE_SMOOTH), 0, 0, null);
				}
			}

			g.fillRect(0, 0, old_w, old_h);
			g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
			g.dispose();
			src = oldpic;
			// 图片调整为方形结束
			if (old_w > w)
				new_w = (int) Math.round(old_w / w2);
			else
				new_w = old_w;
			if (old_h > h)
				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
			else
				new_h = old_h;
			new_h = h;
			new_w = w;

			BufferedImage tag = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
					0, null);

			// 如果配置文件中配置了水印参数则添加水印
			if (!"".equals(fontface) && fontsize != null
					&& !"".equals(fontsize)) {
				markImageByText(tag, null, 10, 50, 0.5f, warterMark, fontface,
						fontsize); // 给图片加水印
			}

			// newimage = new FileOutputStream(img_midname); // 输出到文件流
			/***** jdk1.7 编译错误，暂时注释掉压缩质量方法 by 20150317 ******/
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			// JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/* 压缩质量 */
			// jep.setQuality(per, true);
			// encoder.encode(tag, jep);

			ImageWriter writer = null;
			ios = ImageIO.createImageOutputStream(new File(imgMidname));
			Iterator<ImageWriter> it = ImageIO
					.getImageWritersByFormatName("jpeg");
			if (it.hasNext()) {
				writer = it.next();

				writer.setOutput(ios);
				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(per);
				writer.write(null, new IIOImage(tag, null, null), param);

				ios.flush();
			}
			// encoder.encode(tag); //近JPEG编码
			// newimage.close();

		} catch (IOException ex) {
			logger.error("图片处理发生异常", ex);
		} finally {

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return newimage;
	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 * @param buffImg
	 * 			图片
	 * @param degree
	 * 			旋转角度
	 * @param positionWidth
	 *			水印宽度
	 * @param positionHeight
	 * 			水印高度
	 * @param alpha
	 * 			水印文字透明度
	 * @param warterMark
	 * 			水印文字
	 * @param fontface
	 * 			水印文字字体
	 * @param fontsize
	 * 			水印文字字体大小
	 */
	private static void markImageByText(BufferedImage buffImg, Integer degree,
			int positionWidth, int positionHeight, float alpha,
			String warterMark, String fontface, Integer fontsize) {

		InputStream is = null;
		OutputStream os = null;
		try {
			// 2、得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 3、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(
					buffImg.getScaledInstance(buffImg.getWidth(null),
							buffImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			// 4、设置水印旋转
			if (null != degree) {
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}
			// 5、设置水印文字颜色
			g.setColor(Color.red);
			// 6、设置水印文字Font
			Font font = new Font(fontface, Font.BOLD, fontsize);
			g.setFont(font);
			// 7、设置水印文字透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
			g.drawString(warterMark, buffImg.getWidth(null) / 2,
					buffImg.getHeight(null) / 2);
			// 9、释放资源
			g.dispose();

			System.out.println("图片完成添加水印文字");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}