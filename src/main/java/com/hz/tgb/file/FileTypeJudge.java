package com.hz.tgb.file;

import com.hz.tgb.common.ByteUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件类型
 *
 * create by hezhao on 2017-08-16
 */
public final class FileTypeJudge {

    private FileTypeJudge() {
        // 私有类构造方法
    }

    /**
     * 得到文件头
     *
     * @param inputStream 文件路径
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(InputStream inputStream) throws IOException {

        byte[] b = new byte[28];

        //InputStream inputStream = null;  

        try {
            //inputStream = new FileInputStream(filePath);  
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return ByteUtil.bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param inputStream 文件
     * @return 文件类型
     */
    public static FileType getType(InputStream inputStream) throws IOException {

        String fileHead = getFileContent(inputStream);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }
}
