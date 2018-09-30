package com.hz.tgb.test.pdf;

import com.hz.tgb.image.ImageUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

/**
 * PDF插入图像
 *
 * Created by hezhao on 2018/9/30 16:44
 */
public class TestImageToPDF {

    public static void main(String[] args) throws IOException {
        saveImageDocument();
    }

    /**
     * 插入图像
     * @throws IOException
     */
    public static void saveImageDocument() throws IOException {

        // 创建新文件
        PDDocument document = new PDDocument();

        // 创建页面
        PDPage page = new PDPage();
        document.addPage(page);

        // 准备内容流
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // 缩放图片, 不清晰
        // ImageUtil.resize("C:/Users/Administrator/Pictures/TIM图片20180925144712.png", "D:/fapiao_s.png", 913, 591);
        // cn.hutool.core.util.ImageUtil.scale(new File("C:/Users/Administrator/Pictures/TIM图片20180925144712.png"), new File("D:/fapiao_s.png"), 913, 591, null);

        // 创建PDImageXObject对象
        PDImageXObject pdImage = PDImageXObject.createFromFile("C:/Users/Administrator/Pictures/TIM图片20180927182211.jpg", document);

        int width = pdImage.getWidth();
        int height = pdImage.getHeight();
        System.out.println(width);
        System.out.println(height);

        // 设置页面大小
        // MediaBox：页面边框, CropBox：修剪边框, ArtBox：对象边框, TrimBox：裁切边框, BleedBox：出血边框, 参考：https://blog.csdn.net/qinguo2000/article/details/23741221
        PDRectangle pdRectangle = new PDRectangle(width, height);
        page.setMediaBox(pdRectangle);

        // 在PDF文档中绘制图像
        contentStream.drawImage(pdImage, 0, 0);
        System.out.println("Image inserted");

        // 关闭内容流
        contentStream.close();

        // 保存文档
        document.save(new File("D:/new-doc-image1.pdf"));

        // 关闭文件
        document.close();
    }

}
