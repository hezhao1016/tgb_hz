package com.hz.tgb.file;

import com.hz.tgb.common.ArrayUtil;
import com.hz.tgb.common.EncodingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

/**
 * <p>zip 工具类</p> 
 *
 * <p>注意：此类中用到的压缩类ZipEntry等都来自于org.apache.tools包而非java.util包</p>
 * <p>依赖：ant-1.7.1.jar</p> 
 * 
 * @author hezhao
 * @Time   2017年7月28日 下午3:23:41
 */
public class ZipUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);
	
	/** 
     * 使用GBK编码可以避免压缩中文文件名乱码 
     */  
    private static final String CHINESE_CHARSET = "GBK";  
      
    /** 
     * 文件读取缓冲区大小 
     */  
    private static final int CACHE_SIZE = 1024;
    
    private ZipUtil(){
    	// 私用构造主法.因为此类是工具类.
    }

    /** 
     * <p> 
     * 压缩文件 
     * </p> 
     *  
     * @param sourceFolder 需压缩文件 或者 文件夹 路径
     * @param zipFilePath 压缩文件输出路径 
     * @throws Exception 
     */  
    public static void zip(String sourceFolder, String zipFilePath) throws Exception {  
    	logger.debug("开始压缩 ["+sourceFolder+"] 到 ["+zipFilePath+"]");
        OutputStream out = new FileOutputStream(zipFilePath);  
        BufferedOutputStream bos = new BufferedOutputStream(out);  
        org.apache.tools.zip.ZipOutputStream zos = new org.apache.tools.zip.ZipOutputStream(bos);  
        // 解决中文文件名乱码  
        zos.setEncoding(CHINESE_CHARSET);  
        File file = new File(sourceFolder);  
        String basePath = null;  
        if (file.isDirectory()) {  
            basePath = file.getPath();  
        } else {  
            basePath = file.getParent();  
        }  
        zipFile(file, basePath, zos);  
        zos.closeEntry();  
        zos.close();  
        bos.close();  
        out.close();  
        logger.debug("压缩 ["+sourceFolder+"] 完成！");
    }
    
    /** 
     * <p> 
     * 压缩文件 
     * </p> 
     *  
     * @param sourceFolders 一组 压缩文件夹 或 文件
     * @param zipFilePath 压缩文件输出路径 
     * @throws Exception 
     */  
    public static void zip(String[] sourceFolders, String zipFilePath) throws Exception {  
        OutputStream out = new FileOutputStream(zipFilePath);  
        BufferedOutputStream bos = new BufferedOutputStream(out);  
        org.apache.tools.zip.ZipOutputStream zos = new org.apache.tools.zip.ZipOutputStream(bos);  
        // 解决中文文件名乱码  
        zos.setEncoding(CHINESE_CHARSET);  
        
        for (int i = 0; i < sourceFolders.length; i++) {
        	logger.debug("开始压缩 ["+sourceFolders[i]+"] 到 ["+zipFilePath+"]");
        	File file = new File(sourceFolders[i]);  
        	String basePath = null;  
    		basePath = file.getParent();  
        	zipFile(file, basePath, zos);  
		}
        
        zos.closeEntry();  
        zos.close();  
        bos.close();  
        out.close();  
        logger.debug("压缩 "+ ArrayUtil.join(sourceFolders)+" 完成！");
    }
      
    /** 
     * <p> 
     * 递归压缩文件 
     * </p> 
     *  
     * @param parentFile 
     * @param basePath 
     * @param zos 
     * @throws Exception 
     */  
    private static void zipFile(File parentFile, String basePath, org.apache.tools.zip.ZipOutputStream zos) throws Exception {  
        File[] files = new File[0];  
        if (parentFile.isDirectory()) {  
            files = parentFile.listFiles();  
        } else {  
            files = new File[1];  
            files[0] = parentFile;  
        }  
        String pathName;  
        InputStream is;  
        BufferedInputStream bis;  
        byte[] cache = new byte[CACHE_SIZE];  
        for (File file : files) {  
            if (file.isDirectory()) {  
            	logger.debug("目录："+file.getPath());
            	
            	basePath = basePath.replace('\\', '/');
            	if(basePath.substring(basePath.length()-1).equals("/")){
            		pathName = file.getPath().substring(basePath.length()) + "/";  
            	}else{
            		pathName = file.getPath().substring(basePath.length() + 1) + "/";  
            	}
                
                zos.putNextEntry(new org.apache.tools.zip.ZipEntry(pathName));  
                zipFile(file, basePath, zos);  
            } else {  
            	pathName = file.getPath().substring(basePath.length()) ;  
            	pathName = pathName.replace('\\', '/');
            	if(pathName.substring(0,1).equals("/")){
            		pathName = pathName.substring(1);
            	}
            	
            	logger.debug("压缩："+pathName);
                
                is = new FileInputStream(file);  
                bis = new BufferedInputStream(is);  
                zos.putNextEntry(new org.apache.tools.zip.ZipEntry(pathName));  
                int nRead = 0;  
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
                    zos.write(cache, 0, nRead);  
                }  
                bis.close();  
                is.close();  
            }  
        }  
    }  
      
    /**
	 * 解压zip文件
	 * 
	 * @param zipFileName
	 *            待解压的zip文件路径，例如：c:\\a.zip
	 * 
	 * @param outputDirectory
	 *            解压目标文件夹,例如：c:\\a\
	 */
	public static void unZip(String zipFileName, String outputDirectory)
			throws Exception {
		logger.debug("开始解压 ["+zipFileName+"] 到 ["+outputDirectory+"]");
		org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(zipFileName);

		try {

			Enumeration<?> e = zipFile.getEntries();

			org.apache.tools.zip.ZipEntry zipEntry = null;

			createDirectory(outputDirectory, "");

			while (e.hasMoreElements()) {

				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();

				logger.debug("解压：" + zipEntry.getName());

				if (zipEntry.isDirectory()) {

					String name = zipEntry.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDirectory + File.separator + name);

					f.mkdir();

					logger.debug("创建目录：" + outputDirectory + File.separator + name);

				} else {

					String fileName = zipEntry.getName();

					fileName = fileName.replace('\\', '/');

					if (fileName.indexOf("/") != -1) {

						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));

						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1,
								fileName.length());

					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();

					InputStream in = zipFile.getInputStream(zipEntry);

					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];

					int c;

					while ((c = in.read(by)) != -1) {

						out.write(by, 0, c);

					}

					in.close();

					out.close();

				}

			}
			logger.debug("解压 ["+zipFileName+"] 完成！");

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		} finally {
			zipFile.close();
		}

	}

	/**
	 * 创建目录
	 * @author hezhao
	 * @Time   2017年7月28日 下午7:10:05
	 * @param directory
	 * @param subDirectory
	 */
	private static void createDirectory(String directory, String subDirectory) {

		String dir[];

		File fl = new File(directory);

		try {

			if (subDirectory == "" && fl.exists() != true) {

				fl.mkdir();

			} else if (subDirectory != "") {

				dir = subDirectory.replace('\\', '/').split("/");

				for (int i = 0; i < dir.length; i++) {

					File subFile = new File(directory + File.separator + dir[i]);

					if (subFile.exists() == false)

						subFile.mkdir();

					directory += File.separator + dir[i];

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		}

	}  
	
	/**
	 * 无需解压直接读取Zip文件和文件内容
	 * @author hezhao
	 * @Time   2017年7月28日 下午3:23:10
	 * @param file 文件
	 * @throws Exception
	 */
	public static void readZipFile(String file) throws Exception {
		java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);

		InputStream in = new BufferedInputStream(new FileInputStream(file));
		java.util.zip.ZipInputStream zin = new java.util.zip.ZipInputStream(in);
		java.util.zip.ZipEntry ze;
		while ((ze = zin.getNextEntry()) != null) {
			if (ze.isDirectory()) {
			} else {
				logger.info("file - " + ze.getName() + " : "
						+ ze.getSize() + " bytes");
				long size = ze.getSize();
				if (size > 0) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(zipFile.getInputStream(ze),"GB2312"));
					String line;
					while ((line = br.readLine()) != null) {
						line = EncodingUtil.convertEncoding(line);
						System.out.println(line);
					}
					br.close();
				}
				System.out.println();
			}
		}
		zin.closeEntry();
	}
	
	
	public static void main(String[] args) throws Exception {
		try {
//			readZipFile("D:\\new1\\文字.zip");
			
			//压缩文件
//			String sourceFolder = "D:/新建文本文档.txt";  
//	        String zipFilePath = "D:/新建文本文档.zip";  
//	        ZipUtil.zip(sourceFolder, zipFilePath);  
			
			//压缩文件夹
//			String sourceFolder = "D:/fsc1";  
//	        String zipFilePath = "D:/fsc1.zip";  
//	        ZipUtil.zip(sourceFolder, zipFilePath);  
	        
	        //压缩一组文件
//			String [] paths = {"D:/2000 空文件.txt","D:/2001 一次最多3000条.txt"};
//			zip(paths, "D:/xzzzz.zip");
			
//	        unZip("D:\\FastStoneCapturecn.zip", "D:/fsc2");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
