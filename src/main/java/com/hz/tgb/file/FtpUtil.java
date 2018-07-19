package com.hz.tgb.file;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Ftp工具类
 *
 * @author hezhao
 */
public class FtpUtil {

	private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	/**
	 * 向FTP服务器上传文件。
	 *
	 * @param ftpUrl
	 *        ftp服务器hostname（IP）
	 * @param ftpPort
	 *        ftp服务器端口
	 * @param userName
	 *        ftp服务器登录名
	 * @param password
	 *        ftp服务器登录密码
	 * @param uploadPath
	 *        上传到ftp服务器的位置路径
	 * @param fileName
	 *        上传的文件名称
	 * @param input
	 *        上传的文件输入流
	 * @return boolean 上传成功与否
	 */
	public static boolean ftpUpload(final String ftpUrl, final int ftpPort, final String userName,
									final String password, final String uploadPath, final String fileName, final InputStream input) {
		boolean uploadResult = false;
		final FTPClient ftp = new FTPClient();
		try {
			int reply;

			// 连接FTP服务器
			ftp.connect(ftpUrl, ftpPort);

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			// 登录
			ftp.login(userName, password);

			ftp.enterLocalPassiveMode();
			// 设置FTPClient的传输模式为二进制（默认是ASCII）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.error("链接FTP服务器失败:[" + reply + "]");
				logger.error("ftpUrl:" + ftpUrl + " ftpPort:" + ftpPort + " userName:" + userName + " password:"
						+ password + " uploadPath:" + uploadPath + " fileName:" + fileName);
				final StringBuilder sb = new StringBuilder();
				if (ftp.getReplyStrings() != null) {
					for (final String s : ftp.getReplyStrings()) {
						sb.append(s);
					}
				}
				logger.error(ftp.getReplyString() + " " + sb.toString());
				ftp.disconnect();
			} else {

				final String[] paths = uploadPath.split("/");

				final StringBuffer sb = new StringBuffer();
				sb.append("/");
				// 循环每级目录
				for (int i = 0; i < paths.length; i++) {
					if (!paths[i].equals("")) {
						sb.append(paths[i] + "/");

						// 重新拼成目录，
						final String path = sb.toString();

						System.out.println(path + "path");
						// 切换工作目录，如果返回false表示该目录不存在，同时检查每级目录是否已经创建
						if (!ftp.changeWorkingDirectory(path)) {
							logger.debug("上传路径不存在。开始创建该路径目录...---第" + i + "次创建---...");
							// 创建目录

							if (ftp.makeDirectory(path)) {
								// 切换到新生成的工作目录
								ftp.changeWorkingDirectory(path);
								logger.debug("创建目录成功。");
							} else {
								logger.debug("创建目录失败！");
								return uploadResult;
							}
						}
					}
				}

				final boolean result = ftp.storeFile(fileName, input);

				if (result) {
					logger.debug("上传文件到FTP服务器成功。");
				} else {
					logger.debug("上传文件到FTP服务器失败!");
				}

				input.close();
				ftp.logout();
				uploadResult = true;
			}
		} catch (final IOException e) {
			logger.error("上传文件到FTP服务器失败!", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (final IOException ioe) {
					logger.error("关闭FTP服务器连接失败!", ioe);
				}
			}
		}

		return uploadResult;
	}


	/**
	 * 在FTP服务器删除文件。
	 *
	 * @param ftpUrl
	 *        ftp服务器hostname（IP）
	 * @param ftpPort
	 *        ftp服务器端口
	 * @param userName
	 *        ftp服务器登录名
	 * @param password
	 *        ftp服务器登录密码
	 * @param filePath
	 *        文件的路径
	 * @return boolean 上传成功与否
	 */
	public static boolean ftpDel(final String ftpUrl, final int ftpPort, final String userName,
								 final String password, final String filePath) {
		boolean uploadResult = false;
		final FTPClient ftp = new FTPClient();
		try {
			int reply;

			// 连接FTP服务器
			ftp.connect(ftpUrl, ftpPort);

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			// 登录
			ftp.login(userName, password);

			ftp.enterLocalPassiveMode();
			// 设置FTPClient的传输模式为二进制（默认是ASCII）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.error("链接FTP服务器失败:[" + reply + "]");
				logger.error("ftpUrl:" + ftpUrl + " ftpPort:" + ftpPort + " userName:" + userName + " password:"
						+ password + " filePath:" + filePath);
				final StringBuilder sb = new StringBuilder();
				if (ftp.getReplyStrings() != null) {
					for (final String s : ftp.getReplyStrings()) {
						sb.append(s);
					}
				}
				logger.error(ftp.getReplyString() + " " + sb.toString());
				ftp.disconnect();
			} else {
				//删除文件
				uploadResult = ftp.deleteFile(filePath);
			}
		} catch (final IOException e) {
			logger.error("删除文件失败!文件："+filePath, e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (final IOException ioe) {
					logger.error("关闭FTP服务器连接失败!", ioe);
				}
			}
		}

		return uploadResult;
	}


	/**
	 * 按长宽进行缩放图片,不需要裁剪的情况使用<br>
	 *
	 * @author yuanchangjian<br>
	 *         2015年4月7日17:04:19
	 * @param bufferedImage
	 *        文件流
	 * @param height
	 *        高度
	 * @param width
	 *        长度
	 * @throws IOException
	 */
	public static BufferedImage scalePic(final BufferedImage bufferedImage, final int height, final int width) {
		try {
			final BufferedImage bi = Thumbnails.of(bufferedImage).size(width, height).keepAspectRatio(true).asBufferedImage();
			return bi;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 裁剪的情况使用，以左下角的位置进行裁剪<br>
	 *
	 * @author yuanchangjian<br>
	 *         2015年4月8日10:48:11
	 * @param bufferedImage
	 *        文件流
	 * @param height
	 *        高度
	 * @param width
	 *        长度
	 * @throws IOException
	 */
	public static BufferedImage cutPic(final BufferedImage bufferedImage, final int height, final int width) {
		try {
			final BufferedImage bi = Thumbnails.of(bufferedImage).sourceRegion(Positions.BOTTOM_LEFT, width, height)
					.size(width, height).keepAspectRatio(true).asBufferedImage();
			return bi;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将BufferedImage的图片进行处理并返回输入流<br>
	 * 2015年4月9日09:15:56
	 *
	 * @author yuanchangjian
	 * @param stream
	 *        ,传入的BufferedImage
	 * @param width
	 *        指定的长度
	 * @param height
	 *        指定的高度
	 * @param operate
	 *        操作（cut：裁剪，scale：缩放）
	 * @return
	 */

	public static InputStream processPhoto(final BufferedImage stream, final int width, final int height,
										   final String operate) {

		try {
			// 生成新的图片文件,需要裁剪，返回BufferedImage,如果是裁剪图片
			if (operate.equals("cut")) {
				final BufferedImage newfBufferedImage = FtpUtil.cutPic(stream, width, height);
				// 将BufferedImage转为inputStream
				final ByteArrayOutputStream bs = new ByteArrayOutputStream();

				final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

				ImageIO.write(newfBufferedImage, "jpg", imOut);

				final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

				return newIs;
			} else if (operate.equals("scale")) {
				final BufferedImage newfBufferedImage = FtpUtil.scalePic(stream, width, height);
				// 将BufferedImage转为inputStream
				final ByteArrayOutputStream bs = new ByteArrayOutputStream();

				final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

				ImageIO.write(newfBufferedImage, "jpg", imOut);

				final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

				return newIs;
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
