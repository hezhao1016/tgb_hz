package com.hz.tgb.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezhao on 2018/12/20 15:19
 */
public class DirFileListUtil {

    public static void main(String[] args) {
        List<String> list = getList("D:\\聊天文件\\KuaSheng Files\\128692\\Group1");
        for (String str : list) {
            System.out.println("\""+ str +"\",");
        }
    }

    /**
     * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
     *
     * @param path
     *            文件夹
     */
    public static void printList(String path) {
        printList(new File(path));
    }

    public static void printList(File file) {
        if (!file.exists()) {
            System.out.println("文件名称不存在!");
        } else {
            if (file.isFile()) {
                // if (path.getName().toLowerCase().endsWith(".pdf")
                // || path.getName().toLowerCase().endsWith(".doc")
                // || path.getName().toLowerCase().endsWith(".chm")
                // || path.getName().toLowerCase().endsWith(".html")
                // || path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
                System.out.println(toUNIXpath(file.getAbsolutePath()));
                // System.out.println(file.getName());
                // }
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    printList(files[i]);
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
    public static List<String> getList(String path) {
        return getList(new File(path));
    }

    public static List<String> getList(File file) {
        List<String> fileNames = new ArrayList<>();
        if (!file.exists()) {
            System.out.println("文件名称不存在!");
        } else {
            if (file.isFile()) {
                fileNames.add(toUNIXpath(file.getAbsolutePath()));
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    fileNames.addAll(getList(files[i]));
                }
            }
        }

        return fileNames;
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
        String replace = filePath.replaceAll("(\\\\+)", "/");
        replace = replace.replaceAll("(/+)", "/");
        return replace;
    }

}
