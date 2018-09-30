package com.hz.tgb.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Java生成缩略图工具类
 *
 * Created by hezhao on 2018-07-19 16:47
 */
@SuppressWarnings("restriction")
public class ImageThumbnailUtil {

    public static void toThumbnail(String uploadPath, String fileName){
        String uploadThumbPath = uploadPath + "/thumb/";
        File file = new File(uploadThumbPath);
        if (!file.isFile()) {
            file.mkdirs();
        }
        System.out.println(uploadPath+fileName);
        System.out.println(uploadThumbPath+fileName);
        try {
            createThumbnail(uploadPath+"/"+fileName, uploadThumbPath+fileName, 390, 225, 100);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建缩略图
     * @param filename 图片路径
     * @param outFilename 缩略图存放路径
     * @param thumbWidth 宽
     * @param thumbHeight 高
     * @param quality 图片质量, 0-100
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void createThumbnail(String filename, String outFilename, int thumbWidth,int thumbHeight, int quality)
            throws InterruptedException,FileNotFoundException, IOException {

        // 加载图片文件
        Image image = Toolkit.getDefaultToolkit().getImage(filename);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        // 强制加载图像
        mediaTracker.waitForID(0);
        // 测试图片是否加载成功:
        // System.out.println(mediaTracker.isErrorAny());

        // 通过参数判断缩略图大小
        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;
        // 缩小
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {// 放大
            thumbWidth = (int) (thumbHeight * imageRatio);
        }

        // 创建缩略图对象 并设置quality
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = thumbImage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics2D.drawImage(image, 0, 0,thumbWidth, thumbHeight, null);
        // 输出缩略图
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);

        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float) quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close();
    }

    public static void main(String[] args) {
        String filename = "E:/img.png";
        String outFilename = "E:/img_thumb.png";
        try {
            ImageThumbnailUtil.createThumbnail(filename, outFilename, 600, 960, 100);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
