package com.hz.tgb.qrcode.two.qrcode;

import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
  
import javax.imageio.ImageIO;  
  
import com.swetake.util.Qrcode;  
  
/**  
 * @作者  Relieved 
 * @创建日期   2014年11月8日 
 * @描述  （带logo二维码）  
 * @版本 V 1.0 
 */  
public class Logo_Two_Code {  
/**  
     * 生成二维码(QRCode)图片  
     * @param content 二维码图片的内容 
     * @param imgPath 生成二维码图片完整的路径 
     * @param ccbpath  二维码图片中间的logo路径 
     */    
    public static int createQRCode(String content, String imgPath,String ccbPath,int version) {    
        try {    
            Qrcode qrcodeHandler = new Qrcode();    
            //设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小    
            qrcodeHandler.setQrcodeErrorCorrect('M');    
            //N代表数字,A代表字符a-Z,B代表其他字符  
            qrcodeHandler.setQrcodeEncodeMode('B');   
            // 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大    
            qrcodeHandler.setQrcodeVersion(version);   
            // 图片尺寸    
            int imgSize =67 + 12 * (version - 1) ;  
    
            byte[] contentBytes = content.getBytes("gb2312");    
            BufferedImage image = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);    
            Graphics2D gs = image.createGraphics();    
    
            gs.setBackground(Color.WHITE);    
            gs.clearRect(0, 0, imgSize, imgSize);    
    
            // 设定图像颜色 > BLACK    
            gs.setColor(Color.BLACK);    
    
            // 设置偏移量 不设置可能导致解析出错    
            int pixoff = 2;    
            // 输出内容 > 二维码    
            if (contentBytes.length > 0 && contentBytes.length < 130) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {    
                System.err.println("QRCode content bytes length = "    
                        + contentBytes.length + " not in [ 0,125]. ");    
                return -1;  
            }    
            Image logo = ImageIO.read(new File(ccbPath));//实例化一个Image对象。  
            int widthLogo = logo.getWidth(null)>image.getWidth()*2/10?(image.getWidth()*2/10):logo.getWidth(null),   
                heightLogo = logo.getHeight(null)>image.getHeight()*2/10?(image.getHeight()*2/10):logo.getWidth(null);  
             
             /** 
               * logo放在中心 
              */  
            int x = (image.getWidth() - widthLogo) / 2;  
            int y = (image.getHeight() - heightLogo) / 2;  
            gs.drawImage(logo, x, y, widthLogo, heightLogo, null);  
            gs.dispose();    
            image.flush();    
    
            // 生成二维码QRCode图片    
            File imgFile = new File(imgPath);    
            ImageIO.write(image, "png", imgFile);    
    
        } catch (Exception e)   
        {    
            e.printStackTrace();    
            return -100;  
        }    
          
        return 0;  
    }    
  
  
    public static void main(String[] args) {  
		String imgPath = "D:/logo_QRCode.png";
		String logoPath = "D:/touxiang.png";
		String encoderContent = "http://blog.csdn.net/gao36951";
		Logo_Two_Code logo_Two_Code = new Logo_Two_Code();
		logo_Two_Code.createQRCode(encoderContent, imgPath, logoPath, 8);
	}
}  