package com.hz.tgb.doc;

import com.hz.tgb.entity.Book;
import com.hz.tgb.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
  
/** 
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！ 
 *  
 * @author hezhao
 * @version v1.0 
 * @param <T> 
 *            应用泛型，代表任意一个符合javabean风格的类 
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx() 
 *            byte[]表jpg格式的图片数据 
 *            
 * http://blog.csdn.net/evangel_z/article/details/7332535
 */  
public class ExportExcelByPoi<T>  
{  
	
	//%%%%%%%%-------字段部分 开始----------%%%%%%%%%  
    /** 
     * 标题栏背景颜色 默认白色
     */
    private short titleBackColor = HSSFColor.WHITE.index;  
    
    /** 
     * 标题栏文字颜色 默认黑色
     */
    private short titleFontColor = HSSFColor.BLACK.index;
    
    /** 
     * 标题栏文字大小 默认12
     */
    private short titleFontSize = 12;
    
    /**
     * 标题栏文字是否粗体 默认true
     */
    private boolean titleFontBold = true;
	
	/**
	 * 标题栏高度 默认13
	 */
	private short titleHeight = 13;
      
    /** 
     * 数据栏背景颜色 默认白色
     */  
    private short columnBackColor = HSSFColor.WHITE.index;  
    
    /** 
     * 数据栏文字颜色 默认黑色
     */  
    private short columnFontColor = HSSFColor.BLACK.index;
    
    /** 
     * 数据栏文字大小 默认10
     */
    private short columnFontSize = 10;
    
    /**
     * 数据栏文字是否粗体 默认false
     */
    private boolean columnFontBold = false;
	
	/**
	 * 数据栏高度 默认13
	 */
	private short columnHeight = 13;
      
    /** 
     * 表格默认宽度 15个字节
     */  
    private short widthDefault = 15;  
    
    /** 
     * 图片高度  默认60
     */  
    private short imageHeight = 60;  
      
    /** 
     * 是否有框线
     */  
    private boolean needBorder = true;  
    
    /**
     * 对齐方式 默认中间对齐
     */
    private short align = HSSFCellStyle.ALIGN_CENTER;
    
    /**
     * 日期格式化
     */
    private String pattern = "yyyy-MM-dd";
    
    /**
     * 表头 可为空
     */
    private String[] headers;
    
    /**
     * 数据
     */
    private Collection<T> dataset;
    
    /**
     * 输出流
     */
    private OutputStream out;
      
    //%%%%%%%%-------字段部分 结束----------%%%%%%%%%  
      
  
      
    public ExportExcelByPoi(){  
          
    } 
    
  
    /**
     * 生成Excel
     * @param sheetName 工作表名称
     * @param widths 宽度集合   当需要自定义某一列的宽度时，需要把那个字段名作为键，然后宽度作为值存入widths，其余未存入的按照默认宽度处理
     */
    public void createExcel(String sheetName,Map<String,Integer> widths)  
    {  
    	create(sheetName,widths);  
    }  
    
    /**
     * 生成Excel
     * @param sheetName 工作表名称
     */
    public void createExcel(String sheetName)  
    {  
    	create(sheetName,null);  
    }  
  
    
    /** 
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     * @param sheetName 
     *            表格标题名 
     */
    @SuppressWarnings("unchecked")  
    private void create(String sheetName,Map<String,Integer> widths)  
    {  
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(sheetName);  
        // 设置表格默认列宽度 
        sheet.setDefaultColumnWidth(this.widthDefault);  
        
        // 标题栏样式  
        HSSFCellStyle style = createTitleStyle(workbook);  
        //数据栏样式
        HSSFCellStyle style2 = createColumnStyle(workbook);

        //整形样式
        HSSFCellStyle style_int = createColumnStyle(workbook);
        style_int.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

        //浮点数样式
        HSSFCellStyle style_float = createColumnStyle(workbook);
        style_float.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        // 声明一个画图的顶级管理器  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        // 定义注释的大小和位置,详见文档  
//        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,  
//                0, 0, 0, (short) 4, 2, (short) 6, 5));  
        // 设置注释内容  
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
//        comment.setAuthor("leno");  
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
		row.setHeight((short) (titleHeight * 20));
        for (short i = 0; i < headers.length; i++)  
        {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        while (it.hasNext())  
        {  
            index++;  
            row = sheet.createRow(index);  
			row.setHeight((short) (columnHeight * 20));
			
            T t = (T) it.next();  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Field[] fields = t.getClass().getDeclaredFields();  
            for (short i = 0; i < fields.length; i++)  
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellStyle(style2);  
                Field field = fields[i];  
                String fieldName = field.getName();  
                String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                try  
                {  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
                    Object value = getMethod.invoke(t, new Object[]{});  
                    
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    
                    if (value instanceof Integer) {
                        textValue = value.toString();
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                        cell.setCellStyle(style_int);
                    } else if (value instanceof Float) {
                        textValue = value.toString();
                        float fValue = (Float) value;
                        cell.setCellValue(fValue);
                        cell.setCellStyle(style_float);
                    } else if (value instanceof Double) {
                        textValue = value.toString();
                        double dValue = (Double) value;
                        cell.setCellValue(dValue);
                        cell.setCellStyle(style_float);
                    } else if (value instanceof Long) {
                        textValue = value.toString();
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                        cell.setCellStyle(style_int);
                    } else if (value instanceof BigDecimal) {
                        textValue = value.toString();
                        BigDecimal bigValue = new BigDecimal(textValue);
                        cell.setCellValue(bigValue.doubleValue());
                        cell.setCellStyle(style_float);
                    } else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue = "女";
                        }
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        cell.setCellValue(richString);
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        cell.setCellValue(richString);
                    } else if (value instanceof byte[]) {
                        int width = 80;
                        //处理自定义宽度
                        if (widths != null && widths.containsKey(fieldName)) {
                            width = widths.get(fieldName);
                        }

                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(this.imageHeight);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * width * 7.666));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);  // AnchorType.MOVE_DONT_RESIZE = 2
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        cell.setCellValue(richString);
                    }
                    // 如果不是图片数据
                    if (textValue != null)  
                    {  
                    	//处理自定义宽度
                    	if(widths!=null && widths.containsKey(fieldName)){
                    		int width = widths.get(fieldName);
                    		// 设置所在列宽度为 width px,注意这里单位的一个换算  
                    		sheet.setColumnWidth(i, (short) (35.7 * width * 7.666));  
                    	}
                    }
                }  
                catch (SecurityException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (NoSuchMethodException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (IllegalArgumentException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (IllegalAccessException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (InvocationTargetException e)  
                {  
                    e.printStackTrace();  
                }  
                finally  
                {  
                    // 清理资源  
                }  
            }  
        }  
        try  
        {  
            workbook.write(out);  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
    }


    /**
     *数据栏样式
     * @param workbook
     * @return
     */
	private HSSFCellStyle createColumnStyle(HSSFWorkbook workbook) {
		// 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(this.columnBackColor);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        
      //是否需要框线
        if(this.needBorder){
        	style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        	style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        	style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        }else{
        	style2.setBorderBottom(HSSFCellStyle.BORDER_NONE);  
        	style2.setBorderLeft(HSSFCellStyle.BORDER_NONE);  
        	style2.setBorderRight(HSSFCellStyle.BORDER_NONE);  
        	style2.setBorderTop(HSSFCellStyle.BORDER_NONE);  
        }
        style2.setAlignment(this.align);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setColor(this.columnFontColor);  
        font2.setFontHeightInPoints(this.columnFontSize);  
        if(this.columnFontBold){
        	font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        }else{
        	font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        }
        // 把字体应用到当前的样式  
        style2.setFont(font2);
		return style2;
	}


    /**
     * 标题栏样式
     * @param workbook
     * @return
     */
	private HSSFCellStyle createTitleStyle(HSSFWorkbook workbook) {
		// 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(this.titleBackColor);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        
        //是否需要框线
        if(this.needBorder){
        	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        	style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        	style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        }else{
        	style.setBorderBottom(HSSFCellStyle.BORDER_NONE);  
        	style.setBorderLeft(HSSFCellStyle.BORDER_NONE);  
        	style.setBorderRight(HSSFCellStyle.BORDER_NONE);  
        	style.setBorderTop(HSSFCellStyle.BORDER_NONE);  
        }
        //对齐方式
        style.setAlignment(this.align);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(this.titleFontColor);  
        font.setFontHeightInPoints(this.titleFontSize);  
        if(this.titleFontBold){
        	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        }else{
        	font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        }
        // 把字体应用到当前的样式  
        style.setFont(font);
		return style;
	}  
  
    public static void main(String[] args)  
    {  
        // 测试学生  
        ExportExcelByPoi<Student> ex = new ExportExcelByPoi<Student>();
        
        String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };  
        List<Student> dataset = new ArrayList<Student>();  
        dataset.add(new Student(10000001, "张三", 20, true, new Date()));  
        dataset.add(new Student(20000002, "李四", 24, false, new Date()));  
        dataset.add(new Student(30000003, "王五", 22, true, new Date()));  
        
        // 测试图书  
        ExportExcelByPoi<Book> ex2 = new ExportExcelByPoi<Book>();
        String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN", "图书出版社", "封面图片" };  
        List<Book> dataset2 = new ArrayList<Book>();  
        
        try  
        {  
            BufferedInputStream bis = new BufferedInputStream(  
                    new FileInputStream("D://barcode.png"));  
            byte[] buf = new byte[bis.available()];  
            while ((bis.read(buf)) != -1)  
            {  
                //  
            }  
            dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",  
                    "清华出版社", buf));  
            dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",  
                    "阳光出版社", buf));  
            dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",  
                    "清华出版社", buf));  
            dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",  
                    "清华出版社", buf));  
            dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",  
                    "汤春秀出版社", buf));  
  
            OutputStream out = new FileOutputStream("E://a.xls");  
            OutputStream out2 = new FileOutputStream("E://b.xls");  
            
            Map<String,Integer> widths = new HashMap<String, Integer>();
            widths.put("isbn", 15);
            widths.put("preface", 30);
            
            ex.setDataset(dataset);
            ex.setHeaders(headers);
            ex.setOut(out);
            ex.setAlign(HSSFCellStyle.ALIGN_LEFT);
            ex.setColumnFontColor(HSSFColor.RED.index);
            ex.setNeedBorder(false);
            ex.setTitleBackColor(HSSFColor.BLUE_GREY.index);
            ex.setWidthDefault(20);
            ex.setTitleFontSize(16);
            ex.setTitleFontBold(false);
            ex.setColumnFontSize(14);
            
            
            ex2.setDataset(dataset2);
            ex2.setHeaders(headers2);
            ex2.setOut(out2);
            ex2.setImageHeight(80);
            
            ex.createExcel("学生信息");  
            ex2.createExcel("书籍信息",widths);  
            out.close();  
            out2.close();  
            JOptionPane.showMessageDialog(null, "导出成功!");  
            System.out.println("excel导出成功！");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

    
	public short getTitleBackColor() {
		return titleBackColor;
	}


	/**
	 * 标题栏背景颜色 默认白色  HSSFColor.WHITE.index
	 * @param titleBackColor 
	 */
	public void setTitleBackColor(short titleBackColor) {
		this.titleBackColor = titleBackColor;
	}


	public short getTitleFontColor() {
		return titleFontColor;
	}


	/**
	 * 标题栏文字颜色 默认黑色 HSSFColor.BLACK.index
	 * @param titleFontColor 
	 */
	public void setTitleFontColor(short titleFontColor) {
		this.titleFontColor = titleFontColor;
	}


	public short getTitleFontSize() {
		return titleFontSize;
	}


	/**
	 * 标题栏文字大小 默认 12
	 * @param titleFontSize
	 */
	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = (short)titleFontSize;
	}


	public boolean getTitleFontBold() {
		return titleFontBold;
	}


	/**
	 * 标题栏文字是否粗体 默认true
	 * @param titleFontBold
	 */
	public void setTitleFontBold(boolean titleFontBold) {
		this.titleFontBold = titleFontBold;
	}


	public short getColumnBackColor() {
		return columnBackColor;
	}


	/**
	 * 数据栏背景颜色 默认白色 HSSFColor.WHITE.index
	 * @param columnBackColor 
	 */
	public void setColumnBackColor(short columnBackColor) {
		this.columnBackColor = columnBackColor;
	}


	public short getColumnFontColor() {
		return columnFontColor;
	}


	/**
	 * 数据栏文字颜色 默认黑色 HSSFColor.BLACK.index
	 * @param columnFontColor 
	 */
	public void setColumnFontColor(short columnFontColor) {
		this.columnFontColor = columnFontColor;
	}


	public short getColumnFontSize() {
		return columnFontSize;
	}


	/**
	 * 数据栏文字大小 默认10
	 * @param columnFontSize
	 */
	public void setColumnFontSize(int columnFontSize) {
		this.columnFontSize = (short)columnFontSize;
	}


	public boolean getColumnFontBold() {
		return columnFontBold;
	}


	/**
	 * 数据栏文字是否粗体 默认false
	 * @param columnFontBold
	 */
	public void setColumnFontBold(boolean columnFontBold) {
		this.columnFontBold = columnFontBold;
	}


	public short getWidthDefault() {
		return widthDefault;
	}


	/**
	 * 表格默认宽度 15个字节
	 * @param widthDefault
	 */
	public void setWidthDefault(int widthDefault) {
		this.widthDefault = (short)widthDefault;
	}


	public boolean isNeedBorder() {
		return needBorder;
	}


	/**
	 * 是否有框线
	 * @param needBorder
	 */
	public void setNeedBorder(boolean needBorder) {
		this.needBorder = needBorder;
	}


	public short getAlign() {
		return align;
	}


	/**
	 * 对齐方式 默认中间对齐 HSSFCellStyle.ALIGN_CENTER
	 * @param align
	 */
	public void setAlign(short align) {
		this.align = align;
	}


	public String getPattern() {
		return pattern;
	}


	/**
	 * 日期格式化
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public String[] getHeaders() {
		return headers;
	}


	/**
	 * 标题栏
	 * @param headers
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}


	public Collection<T> getDataset() {
		return dataset;
	}


	/**
	 * 表格数据集合
	 * @param dataset
	 */
	public void setDataset(Collection<T> dataset) {
		this.dataset = dataset;
	}


	public OutputStream getOut() {
		return out;
	}


	/**
	 * 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
	 * @param out
	 */
	public void setOut(OutputStream out) {
		this.out = out;
	}


	public short getImageHeight() {
		return imageHeight;
	}

	/**
	 * 图片高度 默认60
	 * @param imageHeight
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = (short)imageHeight;
	}
	
	public short getTitleHeight() {
		return titleHeight;
	}

	/**
	 * 标题栏高度 默认13
	 * @param titleHeight
	 */
	public void setTitleHeight(int titleHeight) {
		this.titleHeight = (short) titleHeight;
	}

	public short getColumnHeight() {
		return columnHeight;
	}

	/**
	 * 数据栏高度 默认13
	 * @param columnHeight
	 */
	public void setColumnHeight(int columnHeight) {
		this.columnHeight = (short) columnHeight;
	}
	
}  