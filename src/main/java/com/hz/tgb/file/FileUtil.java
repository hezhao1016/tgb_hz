package com.hz.tgb.file;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * java文件操作工具类<br>
 *
 * 此类包含利用JAVA进行文件的压缩，解压，删除，拷贝操作。部分代码总结了网上的代码，并修正了很多BUG,例如压缩中文问题， 压缩文件中多余空文件问题。<br>
 *
 * @author hezhao
 * @Time 2017年7月28日 上午11:47:16
 */
public class FileUtil {
	protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 分隔符
	 */
	public static final String separator = "/";

	private FileUtil() {
		// 私有类构造方法
	}

	public static void main(String[] args) throws Exception {
		// copyDir("D:/new1", "D:/new3");
		// copyTextFile("D:/w.txt", "D:/w1.txt");
		// copyFile("D:/a.jpg", "D:/c.jpg");

		// deleteAllFile("D:/new1");
		// deleteFile("D:/b.jpg");
		// delete("D:/c.jpg");
		// deleteFolder("D:/new2");

		// System.out.println(mkFile("D://z/z/a.txt"));
		// System.out.println(mkFile("D://z/z/b"));
		// System.out.println(mkDir("D://zI/z/b"));

		// list("D://maven");

		System.out.println(exists("D:\\\\sdf"));
	}

	/**
	 * 使用NIO进行快速的文件拷贝
	 * @author hezhao
	 * @Time   2017年8月1日 上午11:38:27
	 * @param in 源文件
	 * @param out 目标文件
	 * @throws IOException
	 */
	public static void fileCopy(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			// inChannel.transferTo(0, inChannel.size(), outChannel); //
			// original -- apparently has trouble copying large files on Windows

			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel
						.transferTo(position, maxCount, outChannel);
			}
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

	/**
	 * 复制二进制文件
	 *
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			logger.error("FileUtil.copyFile.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return false;
		}
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		// 新建文件输入流并对它进行缓冲
		InputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		OutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		return true;
	}

	/**
	 * 复制二进制文件
	 *
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws IOException
	 */
	public static boolean copyFile(String sourceFileName, String targetFileName)
			throws IOException {
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		return copyFile(sourceFile, targetFile);
	}

	/**
	 * 复制文本文件
	 *
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean copyTextFile(File sourceFile, File targetFile)
			throws IOException {
		if (!sourceFile.exists()) {
			logger.error("FileUtil.copyTextFile.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return false;
		}
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(sourceFile), "UTF-8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(targetFile), "UTF-8"));
		StringBuffer sbf = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			sbf.append(line + "\r\n");// \r\n回车换行(写入文本时可以保持风格不变)
			line = br.readLine();
		}
		bw.flush();// 刷新
		bw.write(sbf.toString());// 写入文件
		br.close();
		bw.close();
		return true;
	}

	/**
	 * 复制文本文件
	 *
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws IOException
	 */
	public static boolean copyTextFile(String sourceFileName,
									   String targetFileName) throws IOException {
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		return copyTextFile(sourceFile, targetFile);
	}

	/**
	 * 复制对象
	 *
	 * @param sourceFile
	 * @param targetFile
	 * @param isAppend
	 *            为true表示追加方式写，false表示重新写
	 * @throws IOException
	 */
	public static boolean copyObjectFile(File sourceFile, File targetFile,
										 boolean isAppend) throws IOException {
		Object o = null;
		if (!sourceFile.exists()) {
			logger.error("FileUtil.copyTextFile.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return false;
		}
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				sourceFile));
		MyObjectOutputStream out = MyObjectOutputStream.newInstance(targetFile,
				new FileOutputStream(targetFile, isAppend));
		try {
			o = in.readObject();

			out.flush();
			out.writeObject(o);
		} catch (ClassNotFoundException e) {
			logger.error(e.toString(), e);
		} finally {
			in.close();
			out.close();
		}
		return true;
	}

	/**
	 * 复制对象
	 *
	 * @param sourceFileName
	 * @param targetFileName
	 * @param isAppend
	 *            为true表示追加方式写，false表示重新写
	 * @throws IOException
	 */
	public static boolean copyObjectFile(String sourceFileName,
										 String targetFileName, boolean isAppend) throws IOException {
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		return copyObjectFile(sourceFile, targetFile, isAppend);
	}

	/**
	 * 此类继承ObjectOutputStream，重写writeStreamHeader()方法,以实现追加写入时去掉头部信息
	 */
	static class MyObjectOutputStream extends ObjectOutputStream {
		private static File f;

		// writeStreamHeader()方法是在ObjectOutputStream的构造方法里调用的
		// 由于覆盖后的writeStreamHeader()方法用到了f。如果直接用此构造方法创建
		// 一个MyObjectOutputStream对象，那么writeStreamHeader()中的f是空指针
		// 因为f还没有初始化。所以这里采用单态模式
		private MyObjectOutputStream(OutputStream out, File f)
				throws IOException, SecurityException {
			super(out);
		}

		// 返回一个MyObjectOutputStream对象，这里保证了new MyObjectOutputStream(out, f)
		// 之前f已经指向一个File对象
		public static MyObjectOutputStream newInstance(File file,
													   OutputStream out) throws IOException {
			f = file;// 本方法最重要的地方：构建文件对象，两个引用指向同一个文件对象
			return new MyObjectOutputStream(out, f);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			// 文件不存在或文件为空,此时是第一次写入文件，所以要把头部信息写入。
			if (!f.exists() || (f.exists() && f.length() == 0)) {
				super.writeStreamHeader();
			} else {
				// 不需要做任何事情
			}
		}
	}

	/**
	 *
	 * 拷贝文件夹中的所有文件到另外一个文件夹
	 *
	 * @param srcDirector
	 *            源文件夹
	 *
	 * @param desDirector
	 *            目标文件夹
	 */

	public static void copyDir(String srcDirector, String desDirector)
			throws IOException {

		(new File(desDirector)).mkdirs();

		File[] file = (new File(srcDirector)).listFiles();

		for (int i = 0; i < file.length; i++) {

			if (file[i].isFile()) {

				logger.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				FileInputStream input = new FileInputStream(file[i]);

				FileOutputStream output = new FileOutputStream(desDirector
						+ "/" + file[i].getName());

				byte[] b = new byte[1024 * 5];

				int len;

				while ((len = input.read(b)) != -1) {

					output.write(b, 0, len);

				}

				output.flush();

				output.close();

				input.close();

			}

			if (file[i].isDirectory()) {

				logger.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				copyDir(srcDirector + "/" + file[i].getName(), desDirector
						+ "/" + file[i].getName());

			}

		}

	}

	/**
	 * 复制文件/文件夹 若要进行文件夹复制，请勿将目标文件夹置于源文件夹中
	 *
	 * @param source
	 *            源文件（夹）
	 * @param target
	 *            目标文件（夹）
	 * @param isFolder
	 *            若进行文件夹复制，则为True；反之为False
	 * @throws Exception
	 */
	public static void copy(String source, String target, boolean isFolder)
			throws Exception {
		if (isFolder) {
			(new File(target)).mkdirs();
			File a = new File(source);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (source.endsWith(File.separator)) {
					temp = new File(source + file[i]);
				} else {
					temp = new File(source + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(target + "/"
							+ (temp.getName()).toString());
					byte[] b = new byte[1024];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copy(source + "/" + file[i], target + "/" + file[i], true);
				}
			}
		} else {
			int byteread = 0;
			File oldfile = new File(source);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(source);
				File file = new File(target);
				file.getParentFile().mkdirs();
				file.createNewFile();
				FileOutputStream fs = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		}
	}

	/**
	 * 移动指定的文件（夹）到目标文件（夹）
	 *
	 * @param source
	 *            源文件（夹）
	 * @param target
	 *            目标文件（夹）
	 * @param isFolder
	 *            若为文件夹，则为True；反之为False
	 * @return
	 * @throws Exception
	 */
	public static boolean move(String source, String target, boolean isFolder)
			throws Exception {
		copy(source, target, isFolder);
		if (isFolder) {
			return delete(source);
		} else {
			return deleteFile(source);
		}
	}

	/**
	 * 删除文件,如果是目录则失败
	 *
	 * @param file
	 */
	public static boolean deleteFile(File file) {
		if(file == null)	return false;
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				return false;
			}
			file.delete();
			return true;
		} else {
			logger.error("FileUtil.deleteFile.FileNotFoundException :找不到"
					+ file.getPath() + "文件！");
			return false;
		}
	}

	/**
	 * 删除文件,如果是目录则失败
	 *
	 * @param fileName
	 */
	public static boolean deleteFile(String fileName) {
		if(fileName == null || fileName.trim().length() == 0)	return false;
		File file = new File(fileName);
		return deleteFile(file);
	}

	/**
	 * 删除文件或文件夹，如果是文件夹则删除目录下所有文件，包括子文件
	 *
	 * @param file
	 */
	public static boolean delete(File file) {
		if(file == null)	return false;
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
			return true;
		} else {
			logger.error("FileUtil.deleteFileOrDirectory.FileNotFoundException :找不到"
					+ file.getPath() + "文件！");
			return false;
		}
	}

	/**
	 * 删除文件或文件夹
	 *
	 * @param fileName
	 */
	public static boolean delete(String fileName) {
		if(fileName == null || fileName.trim().length() == 0)	return false;
		File file = new File(fileName);
		return delete(file);
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 *
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 * @since 1.0
	 */
	public static boolean deleteDir(File directory) {
		if(directory == null)	return false;
		boolean result = true;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 *
	 * @param directoryName
	 *            要清空的目录的目录名
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean deleteDir(String directoryName) {
		if(directoryName == null || directoryName.trim().length() == 0)	return false;
		File dir = new File(directoryName);
		return deleteDir(dir);
	}

	/**
	 *
	 * 删除指定目录及其中的所有内容。
	 *
	 * @param folderPath
	 *            folderPath 文件夹完整绝对路径
	 */

	public static void deleteFolder(String folderPath) throws Exception {
		if(folderPath == null || folderPath.trim().length() == 0)	return;

		// 删除完里面所有内容

		deleteAllFile(folderPath);

		String filePath = folderPath;

		filePath = filePath.toString();

		File myFilePath = new File(filePath);

		// 删除空文件夹

		myFilePath.delete();

	}

	/**
	 *
	 * 删除指定文件夹下所有文件,保留文件夹
	 *
	 * @param path
	 *            文件夹完整绝对路径
	 */

	public static boolean deleteAllFile(String path) throws Exception {
		if(path == null || path.trim().length() == 0)	return false;

		boolean flag = false;
		File file = new File(path);

		if (!file.exists()) {
			return flag;
		}

		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();

		File temp = null;

		for (int i = 0; i < tempList.length; i++) {

			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}

			if (temp.isFile()) {
				temp.delete();
			}

			if (temp.isDirectory()) {

				// 先删除文件夹里面的文件
				deleteAllFile(path + "/" + tempList[i]);

				// 再删除空文件夹
				deleteFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 在指定的位置创建指定的文件
	 *
	 * @param filePath
	 *            完整的文件路径
	 * @throws Exception
	 */
	public static boolean mkFile(String filePath) {
		try {
			File file = new File(filePath);
			File parentFile = file.getParentFile();
			if (parentFile != null && !parentFile.exists()) {
				parentFile.mkdirs();// 创建父文件夹 如果已有父文件夹则创建失败
			}
			boolean flag = file.createNewFile();
			file = null;
			if (flag)
				logger.debug("创建文件完成");
			return flag;
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
		return false;
	}

	/**
	 * 在指定的位置创建文件夹,可创建多级目录
	 *
	 * @param dirPath
	 *            文件夹路径
	 * @return 若创建成功，则返回True；反之，则返回False
	 */
	public static boolean mkDir(String dirPath) {
		boolean flag = new File(dirPath).mkdirs();
		if (flag)
			logger.debug("创建目录完成");
		return flag;
	}

	/**
	 * 通过InputStream读取文件
	 *
	 * @param sourceFile
	 *            源文件
	 * @param length
	 *            缓冲区大小
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File sourceFile, Integer length)
			throws IOException {
		if (!sourceFile.exists()) {
			logger.error("FileUtil.readByInputStream.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return null;
		}
		if (length == null) {
			length = 1024;
		}
		InputStream is = new FileInputStream(sourceFile);
		byte[] bytes = new byte[length];
		int count = is.read(bytes);
		String result = null;
		while (count != -1) {
			for (byte b : bytes) {
				result += (char) b;
			}
			bytes = new byte[length];
			count = is.read(bytes);
		}
		is.close();
		return result;
	}

	/**
	 * 通过InputStream读取文件
	 *
	 * @param filePath
	 *            源文件
	 * @param length
	 *            缓冲区大小
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String filePath, Integer length)
			throws IOException {
		File sourceFile = new File(filePath);
		return readFile(sourceFile, length);
	}

	/**
	 * 把二进制文件读入字节数组，如果没有内容，字节数组为null
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:23:26
	 * @param filePath
	 * @return
	 */
	public static byte[] readFile(String filePath) {
		byte[] data = null;
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(filePath));
			try {
				data = new byte[in.available()];
				in.read(data);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
		return data;
	}

	/**
	 * 读取 URL 中的文件
	 */
	public static String readText(URL url) throws Exception {
		logger.info("try to read file, {}", url.toString());
		try {
			File file = new File(url.toURI());
			logger.info("read file succeed, {}", url.toString());
			return readText(file);
		} catch (Exception e) {
			logger.info("read file error, {}", url.toString(), e);
			throw e;
		}
	}

	/**
	 * 通过BufferedReader读取文件,自动识别文件编码
	 *
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 */
	public static String readText(File sourceFile) throws IOException {
		if (!sourceFile.exists()) {
			logger.error("FileUtil.readByInputStream.FileNotFoundException :找不到"
					+ sourceFile.getPath() + "文件！");
			return null;
		}
		if(sourceFile.length() != 0){
			//获取文件编码
			String fileEncode = EncodingDetect.getJavaEncode(sourceFile.getAbsolutePath());
			FileInputStream fis = new FileInputStream(sourceFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,
					fileEncode));
			String line = br.readLine();
			String LINE_SEPARATOR = System.getProperty("line.separator");// 代表换行符
			StringBuffer sbf = new StringBuffer();
			while (line != null) {
				sbf.append(line + LINE_SEPARATOR);
				line = br.readLine();
			}
			br.close();
			fis.close();
			return sbf.toString();
		}
		return "";
	}

	/**
	 * 通过BufferedReader读取文件,自动识别文件编码
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String readText(String filePath) throws IOException {
		File sourceFile = new File(filePath);
		return readText(sourceFile);
	}

	/**
	 * 读取文件内容（使用UTF-8编码）
	 *
	 * @param filePath
	 *            输出文件路径
	 * @return
	 * @throws Exception
	 */
	public static String readTextUTF8(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("FileUtil.readByInputStream.FileNotFoundException :找不到"
					+ file.getPath() + "文件！");
			return null;
		}

		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,
				"UTF-8"));
		String LINE_SEPARATOR = System.getProperty("line.separator");// 代表换行符
		StringBuffer sbf = new StringBuffer();
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sbf.append(temp + LINE_SEPARATOR);
		}
		br.close();
		fis.close();
		return sbf.toString();
	}

	/**
	 * 读取文件内容，返回List。
	 * 适用于文本内容由标识符分隔而成，需要将其转换为数组
	 * 自动识别文件编码
	 *
	 * @param filePath
	 *            输出文件路径
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readTextForList(String filePath,String regex) {
		List<String[]> list = new ArrayList<>();

		try {
			File file = new File(filePath);
			if (!file.exists() || file.length() == 0) {
				return list;
			}
			//获取文件编码
			String fileEncode = EncodingDetect.getJavaEncode(filePath);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(new BOMInputStream(fis), fileEncode));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				//去除空行，空格
				if(temp != null && temp.trim().length() > 0) {
					list.add(temp.trim().split(regex));
				}
			}
			br.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 读取对象，返回一个对象
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:21:40
	 * @param filePath
	 * @return
	 */
	public static Object readObject(String filePath) {
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				o = in.readObject();
			} finally {
				in.close();
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return o;
	}

	/**
	 * 读取对象，返回一个对象数组，count表示要读的对象的个数
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:21:44
	 * @param filePath
	 * @param count
	 * @return
	 */
	public static Object[] readObject(String filePath, int count) {
		Object[] objects = new Object[count];
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				for (int i = 0; i < count; i++) {

					objects[i] = in.readObject();
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return objects;
	}

	/**
	 * 把一个对象写入文件，isAppend为true表示追加方式写，false表示重新写
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:19:15
	 * @param filePath
	 * @param o
	 * @param isAppend
	 */
	public static void writeObject(String filePath, Object o, boolean isAppend) {
		if (o == null)
			return;
		try {
			File f = new File(filePath);
			MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,
					new FileOutputStream(f, isAppend));
			try {
				out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 把一个对象数组写入文件，isAppend为true表示追加方式写，false表示重新写
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:21:25
	 * @param filePath
	 * @param objects
	 * @param isAppend
	 */
	public static void writeObject(String filePath, Object[] objects,
								   boolean isAppend) {
		if (objects == null || objects.length == 0)
			return;
		try {
			File f = new File(filePath);
			MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,
					new FileOutputStream(f, isAppend));
			try {
				for (Object o : objects)
					out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 将文件内容写入文件（使用UTF-8编码）
	 *
	 * @param content
	 *            文件内容
	 * @param outputPath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void writeTextUTF8(String content, String outputPath)
			throws Exception {
		if (content == null || content.length() == 0)
			return;
		mkFile(outputPath);
		File file = new File(outputPath);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,
				"UTF-8"));
		bw.write(content);
		bw.flush();
		bw.close();
		fos.close();
	}

	/**
	 * 将字符串写到文件内
	 *
	 * @param outputPath
	 *            输出文件路径
	 * @param content
	 *            字符串
	 * @param isApend
	 *            是否追加
	 * @throws IOException
	 */
	public static void writeText(String content, String outputPath,
								 boolean isApend) throws IOException {
		if (content == null || content.length() == 0)
			return;
		if (new File(outputPath).exists()) {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath,
					isApend));
			bw.write(content);
			bw.flush();
			bw.close();
		}
	}

	/**
	 * 把字节数组为写入二进制文件，数组为null时直接返回
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午8:22:39
	 * @param filePath
	 * @param data
	 */
	public static void writeFile(String filePath, byte[] data) {
		if (data == null || data.length == 0)
			return;
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(filePath));
			try {
				out.write(data);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
	 *
	 * @param file
	 *            文件夹
	 */
	public static void list(File file) {
		if (!file.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (file.isFile()) {
				// if (path.getName().toLowerCase().endsWith(".pdf")
				// || path.getName().toLowerCase().endsWith(".doc")
				// || path.getName().toLowerCase().endsWith(".chm")
				// || path.getName().toLowerCase().endsWith(".html")
				// || path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
				System.out.println(file);
				// System.out.println(file.getName());
				// }
			} else {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i]);
				}
			}
		}
	}

	/**
	 * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
	 *
	 * @param path
	 *            文件夹
	 */
	public static void list(String path) {
		list(new File(path));
	}

	/**
	 * 获取文件后缀名
	 *
	 * @param filename
	 *            文件名
	 * @return 文件的后缀名以.开头
	 */
	public static String getFileSuffix(String filename) {
		int index = filename.lastIndexOf(".");
		return index > 0 ? filename.substring(index) : "";
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 *
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 *
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 *
	 * @param fileName
	 *            文件名
	 * @return 文件名中的名字部分
	 * @since 1.0
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
	 * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
	 *
	 * @param fileName
	 *            文件名
	 * @return 父路径，不存在或者已经是父目录时返回""
	 * @since 1.0
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf('/');
		if (point == -1) {
			point = fileName.indexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.indexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 * @since 1.0
	 */
	public static int getPathLsatIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 *
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 1.0
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 *
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 * @since 1.0
	 */
	public static String toUNIXpath(String filePath) {
		String replace = filePath.replaceAll("(\\\\+)", separator);
		replace = replace.replaceAll("(/+)", separator);
		return replace;
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 *
	 * @param fileName
	 *            文件名
	 * @return 对应的UNIX风格的文件路径
	 * @since 1.0
	 * @see #toUNIXpath(String filePath) toUNIXpath
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 检查路径是否以 “/” “\” 结尾,如果不是 则加上“/”
	 *
	 * @author hezhao
	 * @Time 2017年7月31日 下午2:52:13
	 * @param filePath
	 * @return
	 */
	public static String endSeparator(String filePath) {
		if(filePath == null || filePath.trim().length() == 0){
			return "";
		}

		String unixPath = toUNIXpath(filePath);
		if (!unixPath.endsWith("/")) {
			filePath = filePath + "/";
		}
		return filePath;
	}

	/**
	 * 检查路径是否以 “/” “\” 结尾,如果不是 则加上“/”
	 *
	 * @author hezhao
	 * @Time 2017年7月31日 下午2:52:13
	 * @param filePath
	 * @return
	 */
	public static String endSeparator(Object filePath) {
		if(filePath == null){
			return "";
		}

		return endSeparator(filePath.toString());
	}

	/**
	 * 检查路径是否以 “/” “\” 结尾
	 *
	 * @author hezhao
	 * @Time 2017年7月31日 下午2:52:13
	 * @param filePath
	 * @return
	 */
	public static boolean endsWithSeparator(String filePath) {
		String unixPath = toUNIXpath(filePath);
		if (!unixPath.endsWith("/")) {
			return false;
		}
		return true;
	}

	/**
	 * 返回文件的URL地址。
	 *
	 * @param file
	 *            文件
	 * @return 文件对应的的URL地址
	 * @throws MalformedURLException
	 * @since 1.0
	 * @deprecated 在实现的时候没有注意到File类本身带一个toURL方法将文件路径转换为URL。 请使用File.toURL方法。
	 */
	public static URL getURL(File file) throws MalformedURLException {
		String fileURL = "file:/" + file.getAbsolutePath();
		URL url = new URL(fileURL);
		return url;
	}

	/**
	 * 从文件路径得到文件名。
	 *
	 * @param filePath
	 *            文件的路径，可以是相对路径也可以是绝对路径
	 * @return 对应的文件名
	 * @since 1.0
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 从文件名得到文件绝对路径。
	 *
	 * @param fileName
	 *            文件名
	 * @return 对应的文件路径
	 * @since 1.0
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 得到文件的前缀名.
	 *
	 * @date 2005-10-18
	 *
	 * @param fileName
	 *            需要处理的文件的名字.
	 * @return the prefix portion of the file's name.
	 */
	public static String getPrefix(String fileName) {
		if (fileName != null) {
			fileName = toUNIXpath(fileName);

			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * 得到文件的短路径, 不保护目录.
	 *
	 * @date 2005-10-18
	 *
	 * @param fileName
	 *            需要处理的文件的名字.
	 * @return the short version of the file's name.
	 */
	public static String getShortFileName(String fileName) {
		if (fileName != null) {
			fileName = fileName.replace("//", "/");

			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			return fileName;
		}
		return "";
	}

	/**
	 * 是否存在
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午9:22:37
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public static boolean exists(String filePath) {
		return exists(new File(filePath));
	}

	/**
	 * 是否存在
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午9:22:37
	 * @param file
	 *            文件
	 * @return
	 */
	public static boolean exists(File file) {
		return file.exists();
	}

	/**
	 * 是否是文件
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午9:23:34
	 * @param fileName
	 * @return
	 */
	public static boolean isFile(String fileName) {
		File file = new File(fileName);
		return file.isFile();
	}

	/**
	 * 是否是目录
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午9:24:25
	 * @param fileName
	 * @return
	 */
	public static boolean isDirectory(String fileName) {
		File file = new File(fileName);
		return file.isDirectory();
	}

	/**
	 * 是否是隐藏文件
	 *
	 * @author hezhao
	 * @Time 2017年7月28日 下午9:25:18
	 * @param fileName
	 * @return
	 */
	public static boolean ishidden(String fileName) {
		File file = new File(fileName);
		return file.isHidden();
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 *
	 * @param file
	 *            需要修改最后访问时间的文件。
	 * @since 1.0
	 */
	public static void touch(File file) {
		long currentTime = System.currentTimeMillis();
		if (!file.exists()) {
			System.err.println("file not found:" + file.getName());
			System.err.println("Create a new file:" + file.getName());
			try {
				if (file.createNewFile()) {
					System.out.println("Succeeded!");
				} else {
					System.err.println("Create file failed!");
				}
			} catch (IOException e) {
				System.err.println("Create file failed!");
				logger.error(e.toString(), e);
			}
		}
		boolean result = file.setLastModified(currentTime);
		if (!result) {
			System.err.println("touch failed: " + file.getName());
		}
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 *
	 * @param fileName
	 *            需要修改最后访问时间的文件的文件名。
	 * @since 1.0
	 */
	public static void touch(String fileName) {
		File file = new File(fileName);
		touch(file);
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 *
	 * @param files
	 *            需要修改最后访问时间的文件数组。
	 * @since 1.0
	 */
	public static void touch(File[] files) {
		for (int i = 0; i < files.length; i++) {
			touch(files[i]);
		}
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考虑中。</b>
	 *
	 * @param fileNames
	 *            需要修改最后访问时间的文件名数组。
	 * @since 1.0
	 */
	public static void touch(String[] fileNames) {
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		touch(files);
	}

}