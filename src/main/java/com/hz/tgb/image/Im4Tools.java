package com.hz.tgb.image;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Stream2BufferedImage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 图片处理工具类<br>
 * 2015年7月22日15:11:48
 *
 * <p>依赖jar包：im4java</p>
 * 
 * @author hezhao
 * 
 */
public class Im4Tools {
	public static final String graphicsMagickPath = "C:/Program Files/GraphicsMagick-1.3.21-Q8";

	/**
	 * 根据前台传递的参数进行裁剪图片<br>
	 * 2015年7月22日15:15:23
	 * 
	 * @param file 文件路径
	 * @param fileExt 文件后缀名
	 * @param x
	 *            js传递参数,x轴坐标
	 * @param y
	 *            js传递参数,y轴坐标
	 * @param x1
	 *            js传递参数
	 * @param y1
	 *            js传递参数
	 * @param limitWidth
	 *            需要限制的宽，就是首先将图片压缩到限定大小再进行裁剪
	 * @param limitHeight
	 *            需要限制的高，就是首先将图片压缩到限定大小再进行裁剪
	 * @return 返回BufferedImage对象
	 * @throws Exception
	 */
	public static InputStream cutImage(final String file, final String fileExt,
			final int x, final int y, final int x1, final int y1,
			final int limitWidth, final int limitHeight) throws Exception {
		// 需要裁剪图片的宽度
		final int width = x1 - x;

		// 需要裁剪图片的高度
		final int height = y1 - y;

		// 初始化IMOperation对象
		final IMOperation op = new IMOperation();
		op.addImage();

		// 按照限定大小并且比例缩放
		if (limitWidth > 0 && limitHeight > 0) {
			op.resize(limitWidth, limitHeight, "!");
		}

		// 裁剪图片
		op.crop(width, height, x, y);

		// 裁剪图片的格式
		op.addImage(fileExt + ":-");

		final ConvertCmd convert = new ConvertCmd(true);

		// linux下不要设置此值，不然会报错
		convert.setSearchPath(graphicsMagickPath);
		final Stream2BufferedImage s2b = new Stream2BufferedImage();
		convert.setOutputConsumer(s2b);

		// 操作图片 然后返回BufferedImage
		convert.run(op, file);

		// 将BufferedImage转为inputStream
		final ByteArrayOutputStream bs = new ByteArrayOutputStream();

		final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

		ImageIO.write(s2b.getImage(), fileExt, imOut);

		final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

		return newIs;
	}

	/**
	 * 根据前台传递的参数进行缩放图片<br>
	 * 2015年7月22日15:35:55
	 * 
	 * @param file 文件路径
	 * @param fileExt 文件后缀名
	 * @param width
	 *            需要限制的宽，就是首先将图片压缩到限定大小再进行裁剪
	 * @param height
	 *            需要限制的高，就是首先将图片压缩到限定大小再进行裁剪
	 * @return 返回BufferedImage对象
	 * @throws Exception
	 */
	public static InputStream zoomImage(final String file,
			final String fileExt, final int width, final int height)
			throws Exception {

		// 初始化IMOperation对象
		final IMOperation op = new IMOperation();
		op.addImage(file);

		// 按照限定大小并且比例压缩,参数带感叹号说明不按比例压缩。
		op.resize(width, height);

		// 裁剪图片的格式
		op.addImage(fileExt + ":-");

		// 设置清晰度
		// op.quality(100d);
		// op.addRawArgs("-quality", "100");

		final ConvertCmd convert = new ConvertCmd(true);

		// linux下不要设置此值，不然会报错
		// convert.setSearchPath(graphicsMagickPath);
		final Stream2BufferedImage s2b = new Stream2BufferedImage();
		convert.setOutputConsumer(s2b);

		// 操作图片 然后返回BufferedImage
		convert.run(op);

		// 将BufferedImage转为inputStream
		final ByteArrayOutputStream bs = new ByteArrayOutputStream();

		final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

		ImageIO.write(s2b.getImage(), fileExt, imOut);

		final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

		return newIs;
	}

}
