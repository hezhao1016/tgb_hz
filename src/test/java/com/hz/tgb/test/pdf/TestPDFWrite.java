package com.hz.tgb.test.pdf;

import com.google.common.collect.Lists;
import com.hz.tgb.file.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * PDF 生成
 *
 * PDFBox教程 - https://www.yiibai.com/pdfbox/
 *
 * Created by hezhao on 2018/9/29 16:00
 */
public class TestPDFWrite {

    public static void main(String[] args) throws IOException {

//        createPage();

//        settingPDFAttr();

//        saveDocument();

//        saveManyLineDocument();

//        mergeDocument();

        mergeDocumentBytes();

    }

    /**
     * 创建页面
     */
    public static void createPage() throws IOException {
        // 创建文档
        PDDocument document = new PDDocument();

        for (int i=0; i<10; i++) {
            // 创建页面
            PDPage blankPage = new PDPage();

            // 添加
            document.addPage( blankPage );
        }

        // 保存
        document.save("D:/my_doc.pdf");
        System.out.println("PDF created");

        // 关闭
        document.close();
    }

    /**
     * 设置文档属性
     */
    public static void settingPDFAttr() throws IOException {
        // 创建文档
        PDDocument document = new PDDocument();

        // 创建页面
        PDPage blankPage = new PDPage();

        // 添加
        document.addPage( blankPage );

        // 文档属性
        PDDocumentInformation pdd = document.getDocumentInformation();

        //Setting the author of the document
        pdd.setAuthor("Yiibai.com");

        // Setting the title of the document
        pdd.setTitle("一个简单的文档标题");

        //Setting the creator of the document
        pdd.setCreator("PDF Examples");

        //Setting the subject of the document
        pdd.setSubject("文档标题");

        //Setting the created date of the document
        Calendar date = new GregorianCalendar();
        date.set(2017, 11, 5);
        pdd.setCreationDate(date);
        //Setting the modified date of the document
        date.set(2018, 10, 5);
        pdd.setModificationDate(date);

        //Setting keywords for the document
        pdd.setKeywords("pdfbox, first example, my pdf");

        // 保存
        document.save("D:/doc_attributes.pdf");

        System.out.println("Properties added successfully ");

        //Closing the document
        document.close();
    }

    /**
     * 添加文档
     * @throws IOException
     */
    public static void saveDocument() throws IOException {

        // 1、加载现有文件
//        File file = new File("D:/my_doc.pdf");
//        PDDocument document = PDDocument.load(file);
//        // 获取所需的页面
//        PDPage page = document.getPage(0);

        // 2、创建新文件
        PDDocument document = new PDDocument();
        // 创建页面
        PDPage page = new PDPage();
        document.addPage(page);

        // 准备内容流
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // 开始文本
        contentStream.beginText();

        // 字体
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

        // 设置文本的位置
        contentStream.newLineAtOffset(25, 500);

        String text = "This is the sample document and we are adding content to it. - By yiibai.com";

        // 插入文本
        contentStream.showText(text);

        // 结束文本
        contentStream.endText();
        System.out.println("Content added");

        // 关闭内容流
        contentStream.close();

        // 保存文档
        document.save(new File("D:/new-doc-text.pdf"));

        // 关闭文件
        document.close();
    }

    /**
     * 添加多行文档
     * @throws IOException
     */
    public static void saveManyLineDocument() throws IOException {

        // 1、加载现有文件
        File file = new File("D:/new-doc-text.pdf");
        PDDocument document = PDDocument.load(file);

        // 页数
        int pages = document.getNumberOfPages();
        System.out.println(pages);

        // 获取所需的页面
//        PDPage page = document.getPage(0);

        // 追加内容
        PDPage page = new PDPage();
        document.addPage(page);


        // 2、创建新文件
//        PDDocument document = new PDDocument();
//        // 创建页面
//        PDPage page = new PDPage();
//        document.addPage(page);

        // 准备内容流
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // 开始文本
        contentStream.beginText();

        // 字体
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

        // 设置文本引导
        contentStream.setLeading(14.5f);

        // 设置文本的位置
        contentStream.newLineAtOffset(25, 725);

        String text = "This is the sample document and we are adding content to it. - By yiibai.com";

        // 插入文本
        contentStream.showText(text);

        String text1 = "This is an example of adding text to a page in the pdf document. we can add as many lines";
        String text2 = "as we want like this using the ShowText()  method of the ContentStream class";

        // 插入文本
        contentStream.showText(text1);

        // 使用newline()插入多个字符串
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(text2);
        contentStream.newLine();

        // 结束文本
        contentStream.endText();
        System.out.println("Content added");

        // 关闭内容流
        contentStream.close();

        // 保存文档
        document.save(new File("D:/new-doc-text-lines.pdf"));

        // 关闭文件
        document.close();
    }

    /**
     * 合并多个PDF文档
     * @throws IOException
     */
    public static void mergeDocument() throws IOException {

        // 1、加载现有文件
        File file1 = new File("D:/360极速浏览器下载/财务平台-发票资源及示例代码/示例发票/pdf发票/京东个人电子发票.pdf");
        File file2 = new File("D:/360极速浏览器下载/财务平台-发票资源及示例代码/示例发票/pdf发票/网易严选电子发票.pdf");

        // 实例化PDFMergerUtility类
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        // 设置目标文件
        PDFmerger.setDestinationFileName("D:/merged.pdf");

        // 设置源文件
        PDFmerger.addSource(file1);
        PDFmerger.addSource(file2);

        // 合并文档
        PDFmerger.mergeDocuments(null);

        System.out.println("Documents merged");
    }

    /**
     * 合并多个PDF文档 - bytes
     * @throws IOException
     */
    public static void mergeDocumentBytes() throws IOException {

        List<byte[]> pdfBytes = Lists.newArrayList(
                FileUtil.readFileByBytes("D:/360极速浏览器下载/财务平台-发票资源及示例代码/示例发票/pdf发票/京东个人电子发票.pdf"),
                FileUtil.readFileByBytes("D:/360极速浏览器下载/财务平台-发票资源及示例代码/示例发票/pdf发票/网易严选电子发票.pdf")
        );

        byte[] mergedBytes = null;

        // 如果有PDF文件，先合并PDF
        if (CollectionUtils.isNotEmpty(pdfBytes)) {
            try {
                // 实例化PDFMergerUtility类
                PDFMergerUtility PDFmerger = new PDFMergerUtility();
                // 输出流
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                // 设置目标文件，即合并后的文件
                PDFmerger.setDestinationStream(bos);

                for (byte[] pdfByte : pdfBytes) {
                    // 输入流
                    ByteArrayInputStream bis = new ByteArrayInputStream(pdfByte);
                    // 设置源文件
                    PDFmerger.addSource(bis);
                }

                // 合并文档
                PDFmerger.mergeDocuments(null);
                System.out.println("Documents merged");

                // 得到最终合并后的字节数组
                mergedBytes = bos.toByteArray();
            } catch (IOException e) {
                System.out.println("发票附件生成接口 -> PDF合并出现异常" + e.toString() );
            }
        }

        // 写入文件 看看效果
        FileOutputStream fos = new FileOutputStream("D:/merged_byte.pdf");
        fos.write(mergedBytes);
        fos.flush();
        fos.close();
    }

}
