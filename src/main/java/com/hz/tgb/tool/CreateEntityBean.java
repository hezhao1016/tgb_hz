package com.hz.tgb.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;

/**
 * 生成实体类
 * @author hezhao
 *
 */
public class CreateEntityBean {
	private String authorName = "HeZhao";// 作者名字

	// 数据库名
	private static final String databaseName = "orcl";

	// Oracle
	private static final String URL_ORACLE = "jdbc:oracle:thin:@localhost:1521:"
			+ databaseName;
	private static final String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
	// MySQL
	private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/"
			+ databaseName;
	private static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	// SQLServer
	private static final String URL_SQLSERVER = "jdbc:sqlserver://localhost:1433;DatabaseName="
			+ databaseName;
	private static final String DRIVER_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// 数据表名数组
	private static final String[] tablenames = {"book","comments"};

	// 指定实体生成所在包的路径
	private static final String packageOutPath = "entity";

	// 数据库连接信息
	private static final String USERNAME = "system";
	private static final String PWD = "ok";
	private static final String URL = URL_ORACLE;
	private static final String DRIVER = DRIVER_ORACLE;

	private String[] colnames; // 列名数组

	private String[] colTypes; // 列名类型数组

	private int[] colSizes; // 列名大小数组

	private boolean f_util = false; // 是否需要导入包java.util.*

	private boolean f_sql = false; // 是否需要导入包java.sql.*

	private static final String NBSP = "    ";
	
	private static final String[] newTablenames = new String[tablenames.length]; 

	/**
	 * 获得链接
	 * @return
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 转换表名
	 */
	private void parseTableName(){
		for (int i = 0; i < tablenames.length; i++) {
			newTablenames[i] = tablenames[i].substring(tablenames[i].lastIndexOf(".")+1);
		}
	}
	
	/**
	 * 入口
	 */
	public void createBeanMethod() {
		if(tablenames==null || tablenames.length==0){
			System.out.println("请选择表名!");
			return;
		}
		Connection conn = getConnection(); // 得到数据库连接
		parseTableName();//转换表名
		for (int index = 0; index < tablenames.length; index++) {
			// myDB为数据库名
			String strsql = "select * from " + tablenames[index];
			PreparedStatement pstmt = null;
			ResultSetMetaData rsmd = null;
			try {
				pstmt = conn.prepareStatement(strsql);
				ResultSet rs2 = pstmt.executeQuery();
				rsmd = pstmt.getResultSet().getMetaData();// 或者 rs2.getMetaData();或者pstmt.getMetaData();
				int size = rsmd.getColumnCount(); // 共有多少列
				colnames = new String[size];
				colTypes = new String[size];
				colSizes = new int[size];
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					colnames[i] = rsmd.getColumnName(i + 1);
					colTypes[i] = rsmd.getColumnTypeName(i + 1);
					if (colTypes[i].equalsIgnoreCase("datetime")
							||colTypes[i].equalsIgnoreCase("date")
							||colTypes[i].equalsIgnoreCase("time")
							||colTypes[i].equalsIgnoreCase("timestamp")
							||colTypes[i].equalsIgnoreCase("year")
							||colTypes[i].equalsIgnoreCase("timestamp with local time zone")
							||colTypes[i].equalsIgnoreCase("timestamp with time zone")) {
						f_util = true;
					}
					if (colTypes[i].equalsIgnoreCase("image")
							|| colTypes[i].equalsIgnoreCase("binary")
							|| colTypes[i].equalsIgnoreCase("VARBINARY")
							|| colTypes[i].equalsIgnoreCase("TINYBLOB")
							|| colTypes[i].equalsIgnoreCase("BLOB")
							|| colTypes[i].equalsIgnoreCase("LONGBLOB")
							|| (colTypes[i].equalsIgnoreCase("BIT")&&this.URL.equals(this.URL_MYSQL))){
						f_sql = true;
					}
					colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
				}
				String content = parse(colnames, colTypes, colSizes,index);
				writeEntityBean(content,index);
				System.out.println("生成 "+initcap(this.newTablenames[index])+" 实体类成功");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成实体类
	 * @param content
	 */
	private void writeEntityBean(String content,int index){
		try {
			String newPackageOutPath = packageOutPath.replace(".", "/");
			String path = "src/"+newPackageOutPath+"/"+initcap(newTablenames[index]) + ".java";
			File file = new File("src/"+newPackageOutPath);
			if(!file.exists()){
				file.mkdirs();//创建没有存在的所有文件夹
			}
			FileWriter fw = new FileWriter(path);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			System.out.println("CreateEntityBeanException: 生成"+initcap(newTablenames[index])+"实体类出现异常......");
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析处理(生成实体类主体代码)
	 */
	private String parse(String[] colNames, String[] colTypes, int[] colSizes,int index) {
		StringBuffer sb = new StringBuffer();
		if (packageOutPath != null && !packageOutPath.isEmpty()) {
			sb.append("package " + this.packageOutPath + ";\r\n");
		}
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append(" * " + initcap(newTablenames[index]) + " 实体类\r\n");
		sb.append(" * " + new Date() + " " + this.authorName + "\r\n");
		sb.append(" */ \r\n");
		// 实体部分
		sb.append("public class " + initcap(newTablenames[index]) + " {\r\n");
		processAllAttrs(sb);// 属性
		processAllConstructors(sb,index);// 构造方法
		processAllMethod(sb);// getter setter 方法
		sb.append("}\r\n");

		return sb.toString();
	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			colnames[i] = colnames[i].toLowerCase();
			sb.append(NBSP + "public void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append(NBSP + NBSP + "this." + colnames[i] + " = " + colnames[i]
					+ ";\r\n");
			sb.append(NBSP + "}\r\n");
			sb.append("\r\n");

			sb.append(NBSP + "public " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append(NBSP + NBSP + "return " + colnames[i] + ";\r\n");
			sb.append(NBSP + "}\r\n");
			sb.append("\r\n");
		}
	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append(NBSP + "private " + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i].toLowerCase() + ";\r\n");
		}
		sb.append("\r\n");
	}

	/**
	 * 生成构造方法
	 * 
	 * @param sb
	 */
	private void processAllConstructors(StringBuffer sb,int index) {
		// 无参构造
		sb.append(NBSP + "public " + initcap(newTablenames[index]) + "(){\r\n");
		sb.append("\r\n");
		sb.append(NBSP + "}\r\n");
		sb.append("\r\n");

		// 带参构造
		sb.append(NBSP + "public " + initcap(newTablenames[index]) + "(");
		for (int i = 0; i < colnames.length; i++) {
			colnames[i] = colnames[i].toLowerCase();
			if (i != colnames.length - 1) {
				sb.append(sqlType2JavaType(colTypes[i]) + " " + colnames[i]
						+ ", ");
			} else {
				sb.append(sqlType2JavaType(colTypes[i]) + " " + colnames[i]);
			}
		}
		sb.append("){\r\n");
		for (int i = 0; i < colnames.length; i++) {
			colnames[i] = colnames[i].toLowerCase();
			sb.append(NBSP + NBSP + "this." + colnames[i] + " = " + colnames[i]
					+ ";\r\n");
		}
		sb.append(NBSP + "}\r\n");
		sb.append("\r\n");
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 功能：获得属性的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {
		// 根据Oracle进行转换
		if (this.URL.equals(this.URL_ORACLE)) {
			if (sqlType.equalsIgnoreCase("binary_double")) {
				return "double";
			} else if (sqlType.equalsIgnoreCase("binary_float")) {
				return "float";
			} else if (sqlType.equalsIgnoreCase("blob")) {
				return "byte[]";
			} else if (sqlType.equalsIgnoreCase("clob")) {
				return "byte[]";
			} else if (sqlType.equalsIgnoreCase("char")
					|| sqlType.equalsIgnoreCase("nvarchar2")
					|| sqlType.equalsIgnoreCase("varchar2")
					|| sqlType.equalsIgnoreCase("varchar")
					|| sqlType.equalsIgnoreCase("nvarchar")) {
				return "String";
			} else if (sqlType.equalsIgnoreCase("date")
					|| sqlType.equalsIgnoreCase("timestamp")
					|| sqlType
							.equalsIgnoreCase("timestamp with local time zone")
					|| sqlType.equalsIgnoreCase("timestamp with time zone")) {
				return "Date";
			} else if (sqlType.equalsIgnoreCase("number")) {
				return "int";
			}
		} else if (this.URL.equals(this.URL_MYSQL)) {
			// MySQL
			if (sqlType.equalsIgnoreCase("tinyint")) {
				return "byte";
			} else if (sqlType.equalsIgnoreCase("smallint")) {
				return "short";
			} else if (sqlType.equalsIgnoreCase("int")) {
				return "int";
			} else if (sqlType.equalsIgnoreCase("bigint")) {
				return "long";
			} else if (sqlType.equalsIgnoreCase("float")) {
				return "float";
			} else if (sqlType.equalsIgnoreCase("decimal")
					|| sqlType.equalsIgnoreCase("double")) {
				return "double";
			} else if (sqlType.equalsIgnoreCase("varchar")
					|| sqlType.equalsIgnoreCase("char")
					|| sqlType.equalsIgnoreCase("nvarchar")
					|| sqlType.equalsIgnoreCase("enum")
					|| sqlType.equalsIgnoreCase("text")
					|| sqlType.equalsIgnoreCase("set")) {
				return "String";
			} else if (sqlType.equalsIgnoreCase("datetime")
					|| sqlType.equalsIgnoreCase("date")
					|| sqlType.equalsIgnoreCase("time")
					|| sqlType.equalsIgnoreCase("timestamp")
					|| sqlType.equalsIgnoreCase("year")) {
				return "Date";
			} else if (sqlType.equalsIgnoreCase("binary")
					|| sqlType.equalsIgnoreCase("VARBINARY")
					|| sqlType.equalsIgnoreCase("BIT")
					|| sqlType.equalsIgnoreCase("TINYBLOB")
					|| sqlType.equalsIgnoreCase("BLOB")
					|| sqlType.equalsIgnoreCase("LONGBLOB")) {
				return "Blod";
			}
		} else if (this.URL.equals(this.URL_SQLSERVER)) {
			// SqlServer
			if (sqlType.equalsIgnoreCase("bit")) {
				return "boolean";
			} else if (sqlType.equalsIgnoreCase("tinyint")) {
				return "byte";
			} else if (sqlType.equalsIgnoreCase("smallint")) {
				return "short";
			} else if (sqlType.equalsIgnoreCase("int")) {
				return "int";
			} else if (sqlType.equalsIgnoreCase("bigint")) {
				return "long";
			} else if (sqlType.equalsIgnoreCase("float")) {
				return "float";
			} else if (sqlType.equalsIgnoreCase("decimal")
					|| sqlType.equalsIgnoreCase("numeric")
					|| sqlType.equalsIgnoreCase("real")
					|| sqlType.equalsIgnoreCase("money")
					|| sqlType.equalsIgnoreCase("smallmoney")) {
				return "double";
			} else if (sqlType.equalsIgnoreCase("varchar")
					|| sqlType.equalsIgnoreCase("char")
					|| sqlType.equalsIgnoreCase("nvarchar")
					|| sqlType.equalsIgnoreCase("nchar")
					|| sqlType.equalsIgnoreCase("text")) {
				return "String";
			} else if (sqlType.equalsIgnoreCase("datetime")
					|| sqlType.equalsIgnoreCase("date")
					|| sqlType.equalsIgnoreCase("time")
					|| sqlType.equalsIgnoreCase("timestamp")
					|| sqlType.equalsIgnoreCase("year")) {
				return "Date";
			} else if (sqlType.equalsIgnoreCase("image")) {
				return "Blod";
			}
		}
		return "String";
	}

	public static void main(String[] args) {
		CreateEntityBean createBean = new CreateEntityBean();
		createBean.createBeanMethod();
	}
}
