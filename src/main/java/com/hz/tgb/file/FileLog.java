package com.hz.tgb.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件日志读写
 * 
 * @author hezhao
 * @Time 2017年3月14日 上午10:56:41
 * @Description 无
 * @Version V 1.0
 */
public class FileLog {
	
	private static final Logger logger = LoggerFactory.getLogger(FileLog.class);

	/**
	 * 项目根路径
	 */
	private final static String relativelyPath = System.getProperty("user.dir");

	/**
	 * 存放日志文件的文件夹名称
	 */
	private final static String dirname = "logs";

	/**
	 * 判断文件是否存在
	 * 
	 * @author hezhao
	 * @Time 2017年3月14日 上午11:41:54
	 * @param file
	 */
	public static void judeFileExists(File file) {

		if (file.exists()) {
			logger.debug("文件[" + file.getPath() + "]已经存在");
		} else {
			logger.debug("文件[" + file.getPath() + "]不存在，马上创建...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @author hezhao
	 * @Time 2017年3月14日 上午11:41:50
	 * @param file
	 */
	public static void judeDirExists(File file) {

		if (file.exists()) {
			if (file.isDirectory()) {
				logger.debug("文件夹[" + file.getName() + "]已经存在");
			} else {
				logger.debug("本目录[" + file.getPath() + "]存在与此文件夹["
						+ file.getName() + "]同名的文件，不能再创建文件夹文件");
			}
		} else {
			logger.debug("文件夹[" + file.getName() + "]不存在，马上创建...");
			file.mkdir();
		}

	}

	/**
	 * 写入日志
	 * 
	 * @author hezhao
	 * @Time 2017年3月14日 上午11:16:48
	 * @param txt
	 *            文本
	 */
	public static void writeLog(String txt) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 2017/3/13 星期一 15:16:47 正在初始化...已完成
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss:S");
		String now = sdf.format(new Date());// 当前时间

		// 完整文件路径
		String fileName = relativelyPath + "/" + dirname + "/" + now + ".txt";
		// 完整文件夹路径
		String dirFullName = relativelyPath + "/" + dirname;

		File file = new File(fileName);
		File dirFile = new File(dirFullName);

		// 确保文件夹、文件都存在
		judeDirExists(dirFile);
		judeFileExists(file);

		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			fw = new FileWriter(file, true);
			writer = new BufferedWriter(fw);

			// 获取当前时间
			String now_1 = sdf_1.format(new Date());// 当前时间
			String content = now_1 + "	" + txt;

			writer.write(content);
			writer.newLine();// 换行
			writer.flush();

			System.out.println("正在写入日志...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	 public static void main(String[] args) {
		 writeLog("何钊发送失败");
		 writeLog("何钊发送成功");
	 }
	 
}
