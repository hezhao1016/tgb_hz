package com.hz.tgb.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/** 
 * @Description pdf工具类 
 * @author hezhao
 * @date 2016年11月29日 下午7:38:09  
 * @version V0.1 
 */  
public class PdfUtil {

	/**
	 * 添加空行
	 */
	public static void newBlankLine(Document document) {
		try {
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文字
	 * 
	 * @param content 内容
	 * @param font 字体
	 * @param align 对齐方式
	 * @return
	 */
	public static Paragraph newParagraph(String content, Font font, int align) {
		Paragraph paragraph = new Paragraph(content, font);
		paragraph.setAlignment(align);
		return paragraph;
	}

	/**
	 * 创建单元格(内容为图片)
	 * 
	 * @param bgColor
	 *            背景
	 * @param border
	 *            边框
	 * @param align
	 *            对齐方式
	 * @param colspan
	 *            所占列数
	 * @param image
	 *            内容(文字或图片对象)
	 * @return
	 */
	public static PdfPCell newPdfPCell(BaseColor bgColor, int border,
			int align, int colspan, Image image) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(bgColor);
		cell.setBorder(border);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.addElement(image);
		return cell;
	}

	/**
	 * 创建单元格(内容为文字)
	 * 
	 * @param bgColor
	 * @param border
	 * @param align
	 * @param colspan
	 * @param paragraph
	 * @return
	 */
	public static PdfPCell newPdfPCell(BaseColor bgColor, int border,
			int align, int colspan, Paragraph paragraph) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(bgColor);
		cell.setBorder(border);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.addElement(paragraph);
		return cell;
	}

	/**
	 * 创建字体
	 * 
	 * @param size
	 *            大小
	 * @param font
	 *            字体
	 * @return
	 * @throws Exception
	 */
	public static Font newFont(int size, int font) {
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Font(bfChinese, size, font);
	}

	/**
	 * 创建字体
	 * 
	 * @param size 大小
	 * @param font 字体
	 * @param color 字体颜色
	 * @return
	 * @throws Exception
	 */
	public static Font newFont(int size, int font, BaseColor color) {
		Font f = newFont(size, font);
		f.setColor(color);
		return f;
	}

	/**
	 * 创建图片
	 * 
	 * @param imgPath
	 *            图片路径
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param align
	 *            对齐方式
	 * @return
	 */
	public static Image newImage(String imgPath, int width, int height,
			int align) {
		Image img = null;
		try {
			img = Image.getInstance(imgPath);
			img.scaleAbsolute(width, height);
			img.setAlignment(align);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return img;
	}

	/**
	 * 创建一个跨多行的单元格
	 * 
	 * @param rows
	 *            所占行数
	 * @param bgColor
	 *            背景色
	 * @param paragraph
	 *            单元格内容文字
	 * @param align
	 *            对齐方式
	 */
	public static PdfPCell newPdfPCellByRows(int rows, BaseColor bgColor,
			Paragraph paragraph, int align) {
		PdfPTable iTable = new PdfPTable(1);
		PdfPCell iCell = new PdfPCell();
		iCell.setFixedHeight(iCell.getFixedHeight() * rows);
		iTable.addCell(iCell);
		iCell.setBackgroundColor(bgColor);
		iCell.addElement(paragraph);
		iCell.setHorizontalAlignment(align);
		PdfPCell cell = new PdfPCell(iTable);
		return cell;
	}

	/**
	 * 创建一个跨多列的单元格
	 * 
	 * @param colspan
	 *            所占列数
	 * @param bgColor
	 *            背景色
	 * @param paragraph
	 *            单元格内容文字
	 * @param align
	 *            对齐方式
	 */
	public static PdfPCell newPdfPCellByColspan(int colspan, BaseColor bgColor,
			Paragraph paragraph, int align) {
		PdfPTable iTable = new PdfPTable(1);
		PdfPCell iCell = new PdfPCell();
		iCell.setColspan(colspan);
		iCell.setBackgroundColor(bgColor);
		iCell.setBorder(0);
		iCell.addElement(paragraph);
		iCell.setHorizontalAlignment(align);
		iTable.addCell(iCell);
		PdfPCell cell = new PdfPCell(iTable);
		return cell;
	}

	/**
	 * 添加背景图片
	 * 
	 * @param document
	 * @param imgPath
	 *            图片路径
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param x
	 *            图片位置x值 (文档左下角为坐标原点)
	 * @param y
	 *            图片位置y值
	 */
	private static void addBackGroundImg(Document document, String imgPath,
			float width, float height, int x, int y) throws Exception {

		Image bakimage = Image.getInstance(imgPath);
		bakimage.setAlignment(Image.UNDERLYING);
		bakimage.setAbsolutePosition(x, y);
		bakimage.setAlignment(Element.ALIGN_TOP);
		bakimage.scaleAbsoluteWidth(width);
		bakimage.scaleAbsoluteHeight(height);
		document.add(bakimage);
	}

	/**
	 * 添加水印(图片+文字)
	 * 
	 * @param inputFile
	 *            原始文件
	 * @param outputFile
	 *            水印输出文件
	 * @param waterMark
	 *            水印名字
	 * @param waterMarkImgPath
	 *            水印图片路径
	 */
	// private static void addWaterMark(String inputFile,String
	// outputFile,String waterMark,String waterMarkImgPath)throws Exception{
	public static void addWaterMark(String inputFile, String outputFile,
			String waterMark) throws Exception {
		PdfReader reader = new PdfReader(inputFile);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
				outputFile));
		BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		int total = reader.getNumberOfPages() + 1;
		// String
		// waterMarkImgPath="http://127.0.0.1:8080/ceb/WebContent/images/report_background.jpg";
		String waterMarkImgPath = "D:/_My_Work_Space/ceb/WebContent/images/report_background.jpg";
		Image image = Image.getInstance(waterMarkImgPath);
		image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
		image.setAbsolutePosition(0, 0);
		PdfContentByte under;
		int j = waterMark.length();
		char c = 0;
		int rise = 0;
		for (int i = 1; i < total; i++) {
			rise = 500;
			under = stamper.getUnderContent(i);
			// 添加图片
			under.addImage(image);
			under.beginText();
			under.setColorFill(BaseColor.LIGHT_GRAY);
			under.setFontAndSize(base, 40);
			// 设置水印文字字体倾斜 开始
			if (j >= 15) {
				under.setTextMatrix(200, 50);
				for (int k = 0; k < j; k++) {
					under.setTextRise(rise);
					c = waterMark.charAt(k);
					under.showText(c + "");
					rise -= 35;
				}
			} else {
				under.setTextMatrix(180, 40);
				for (int k = 0; k < j; k++) {
					under.setTextRise(rise);
					c = waterMark.charAt(k);
					under.showText(c + "");
					rise -= 25;
				}
			}
			// 字体设置结束
			under.endText();

		}
		stamper.close();
		// 删除源文件,保留加水印后的文件
		File file = new File(inputFile);
		file.delete();
	}

}