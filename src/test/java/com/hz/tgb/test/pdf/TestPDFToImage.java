package com.hz.tgb.test.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * PDF转图片
 *
 * Created by hezhao on 2018/9/30 15:17
 */
public class TestPDFToImage {

    public static void main(String[] args) {
        readPDF();

        String f ="C:\\Users\\weidx\\Documents\\My Access-IS Data\\PDFs\\1.pdf";
        String path ="C:\\Users\\weidx\\Desktop\\";
        pdfSaveImage(f, path);
    }

    /**
     * 读取PDF
     */
    public static void readPDF() {
        File pdfFile = new File("C:\\Users\\weidx\\Documents\\My Access-IS Data\\PDFs\\1.pdf");
        PDDocument document = null;
        try {
            // 方式一：
            // InputStream input = null;
            // input = new FileInputStream(pdfFile);
            // // 加载 pdf 文档
            // PDFParser parser = new PDFParser(input, new
            // RandomAccessBuffer());
            // parser.parse();
            // document = parser.getPDDocument();
            // 方式二：
            document = PDDocument.load(pdfFile);
            // 获取页码
            int pages = document.getNumberOfPages();
            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            String content = stripper.getText(document);
		    System.out.println(content);
            document.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 从PDF读取图片并保存
     * @param file
     * @param imgSavePath
     */
    public static void pdfSaveImage(String file, String imgSavePath) {
        try {
            // 打开pdf文件流
            FileInputStream fis = new FileInputStream(file);
            // 加载 pdf 文档,获取PDDocument文档对象
            PDDocument document = PDDocument.load(fis);
            /** 文档页面信息 **/// 获取PDDocumentCatalog文档目录对象
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            // 获取文档页面PDPage列表
            int pages = document.getNumberOfPages();
            int count = 1;
            for (int j = 1; j < pages; j++) {
                PDPage page = document.getPage(j);
                PDResources resources = page.getResources();
                Iterable xobjects = resources.getXObjectNames();
                if (xobjects != null) {
                    Iterator imageIter = xobjects.iterator();
                    while (imageIter.hasNext()) {
                        COSName key = (COSName) imageIter.next();
                        if (resources.isImageXObject(key)) {
                            try {
                                PDImageXObject image = (PDImageXObject) resources.getXObject(key);
                                BufferedImage bimage = image.getImage();
                                ImageIO.write(bimage, "jpg", new File(imgSavePath + count + ".jpg"));
                                count++;
                                System.out.println(count);
                            } catch (Exception e) {
                            }
                        }

                    }
                }
            }
//			document.close();
//			fis.close();

        } catch (Exception e) {
            System.out.println();
        }
    }

    /**
     * 读取图片
     */
    public static void readImage() {

        // 待解析PDF
        File pdfFile = new File("C:\\Users\\weidx\\Documents\\My Access-IS Data\\PDFs\\in.pdf");
        // 空白PDF
        File pdfFile_out = new File("C:\\Users\\weidx\\Documents\\My Access-IS Data\\PDFs\\out.pdf");

        PDDocument document = null;
        PDDocument document_out = null;
        try {
            document = PDDocument.load(pdfFile);
            document_out = PDDocument.load(pdfFile_out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pages_size = document.getNumberOfPages();

        System.out.println("getAllPages===============" + pages_size);
        int j = 0;

        for (int i = 0; i < pages_size; i++) {
            PDPage page = document.getPage(i);
            PDPage page1 = document_out.getPage(0);
            PDResources resources = page.getResources();
            Iterable xobjects = resources.getXObjectNames();

            if (xobjects != null) {
                Iterator imageIter = xobjects.iterator();
                while (imageIter.hasNext()) {
                    COSName key = (COSName) imageIter.next();
                    if (resources.isImageXObject(key)) {
                        try {
                            PDImageXObject image = (PDImageXObject) resources.getXObject(key);

                            // 方式一：将PDF文档中的图片 分别存到一个空白PDF中。
                            PDPageContentStream contentStream = new PDPageContentStream(document_out, page1, PDPageContentStream.AppendMode.APPEND,
                                    true);

                            float scale = 1f;
                            contentStream.drawImage(image, 20, 20, image.getWidth() * scale, image.getHeight() * scale);
                            contentStream.close();
                            document_out.save("C:\\Users\\weidx\\Documents\\My Access-IS Data\\PDFs\\" + j + ".pdf");

                            System.out.println(image.getSuffix() + "," + image.getHeight() + "," + image.getWidth());

                            /**
                             * // 方式二：将PDF文档中的图片 分别另存为图片。 File file = new
                             * File("/Users/xiaolong/Downloads/123"+j+".png");
                             * FileOutputStream out = new
                             * FileOutputStream(file);
                             *
                             * InputStream input = image.createInputStream();
                             *
                             * int byteCount = 0; byte[] bytes = new byte[1024];
                             *
                             * while ((byteCount = input.read(bytes)) > 0) {
                             * out.write(bytes,0,byteCount); }
                             *
                             * out.close(); input.close();
                             **/

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        j++;
                    }
                }
            }
        }
        System.out.println(j);
    }


}
