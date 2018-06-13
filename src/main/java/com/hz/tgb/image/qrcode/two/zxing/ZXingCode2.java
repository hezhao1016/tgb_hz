package com.hz.tgb.image.qrcode.two.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.hz.tgb.image.qrcode.two.zxing.util.BufferedImageLuminanceSource;
import com.hz.tgb.image.qrcode.two.zxing.util.MatrixToImageWriter;

/**
 * 生成与解析
 * @author hezhao
 *
 */
public class ZXingCode2 {
	//编码格式
	private static final String CHARSET = "utf-8";
	//二维码的图片格式 
    private static final String FORMAT = "gif";
    // LOGO宽度
    private static final int WIDTH = 300;
    // LOGO高度
    private static final int HEIGHT = 300;
    
    /**
     * 二维码的生成 
     * @param text
     * @param destPath
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static File createCode(String text,String destPath) throws WriterException, IOException{
        Hashtable hints = new Hashtable(); 
        //内容所使用编码 
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET); 
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, 
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints); 
        //生成二维码 
        File outputFile = new File(destPath+File.separator+"new."+FORMAT); 
        MatrixToImageWriter.writeToFile(bitMatrix, FORMAT, outputFile); 
        return outputFile;
    }
    
    /** 
     * 二维码的解析 
     * 
     * @param file 
     */  
    public static String parseCode(File file)  
    {  
        try  
        {  
            MultiFormatReader formatReader = new MultiFormatReader();  
   
            if (!file.exists())  
            {  
                return null;  
            }  
   
            BufferedImage image = ImageIO.read(file);  
   
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
   
            Map hints = new HashMap();  
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
   
            Result result = formatReader.decode(binaryBitmap, hints);  
   
            System.out.println("解析结果 = " + result.toString());  
            System.out.println("二维码格式类型 = " + result.getBarcodeFormat());  
            System.out.println("二维码文本内容 = " + result.getText());  
            return result.getText();
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            return null;
        }  
    }  
    
    /**test
     * @param args
     * @throws Exception 
     */ 
    public static void main(String[] args) throws Exception { 
    	//text就是二维码的内容里这里可以使普通的文字也可以是链接
        /*String text = "http://www.baidu.com"; 
        String destPath = "d:";
        ZXingCode2.createCode(text, destPath);*/
    	
    	ZXingCode2.parseCode(new File("d:/logo_QRCode.png"));
    }  
}
