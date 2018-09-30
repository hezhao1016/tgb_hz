package com.hz.tgb.test.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * PDF读取
 *
 * Created by hezhao on 2018/9/17 21:39
 */
public class TestPDFRead {

    public static void main(String[] args) throws IOException {

        readPDF1();

        System.out.println("\n\n\n============================================================\n\n\n");

        readPDF2();
    }


    public static void readPDF1() throws IOException {
        PDDocument pdDoc = null;
        StringWriter writer = null;
        String result = null;
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            File file = new File("D:\\360极速浏览器下载\\PDF发票解析\\发票\\中国电信电子发票.pdf");
            pdDoc = PDDocument.load(file);
            writer = new StringWriter();
            pdfStripper.writeText(pdDoc, writer);
            result = writer.getBuffer().toString();

            System.out.println(result);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (pdDoc != null) {
                pdDoc.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void readPDF2() {
        PDDocument helloDocument = null;
        try {
            helloDocument = PDDocument.load(new File("D:\\360极速浏览器下载\\PDF发票解析\\发票\\京东企业电子发票.pdf"));
            PDFTextStripper textStripper = new PDFTextStripper();
            System.out.println(textStripper.getText(helloDocument));

            helloDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
