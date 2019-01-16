package com.hz.tgb.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 数据访问基类<br>
 * <strong>支持 MySQL、Oracle、SQLServer、DB2、Sybase、PostgreSql、SQLite、Derby、H2、HSQLDB、ODBC、SQLServer_JTDS、Sybase_JTDS</strong>
 * <br>切换数据库时，需要指定相关参数
 * 
 * @author hezhao
 * @Time   2017年7月27日 下午4:22:23
 * @Description 无
 * @Version V 1.0
 */
public class BaseDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	private final boolean isLog = true;//是否记录SQL日志
	private final boolean isConfig = false;//是否需要配置文件
	private final boolean isDataSource = false;//是否从连接池取连接
	private final String datasource = "";//如果是从连接池取连接，那么给出连接名称
	private final DBType type = DBType.MySQL;//数据库类型
	
	
	// 数据库名
	private final String db = "db_settle";
	private final String ip = "172.17.160.39";
	private String name = "account";
	private String pwd = "ysj123456";
	private String driver;
	private String url;
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	protected ResultSet rs = null;

	{
		//把JDBC配置信息放在配置文件中
		if(isConfig){
			final Properties properties = new Properties();
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
			} catch (final IOException e) {
				logger.error("加载JDBC配置信息失败...");
				logger.error(e.toString(),e);
			}
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			name = properties.getProperty("jdbc.username");
			pwd = properties.getProperty("jdbc.password");
			
		}else{
			if(type == DBType.MySQL){
				driver = "com.mysql.jdbc.Driver";                                                                                  
//				url = "jdbc:mysql://"+ip+":3306/"+db+"?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
				url = "jdbc:mysql://"+ip+":33066/"+db+"?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			}else if(type == DBType.SQLServer){
				//1、使用sqljdbc
				driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";                                                                                  
				url = "jdbc:sqlserver://"+ip+":1433;DatabaseName="+db;
			}else if(type == DBType.SQLServer_JTDS){
				//2、使用jtds
				driver = "net.sourceforge.jtds.jdbc.Driver";                                                                                  
				url = "jdbc:jtds:sqlserver://"+ip+":1433;DatabaseName="+db;
			}else if(type == DBType.Oracle){
				driver = "oracle.jdbc.driver.OracleDriver";                                                                                  
				url = "jdbc:oracle:thin:@"+ip+":1521:"+db;
			}else if(type == DBType.DB2){
				driver = "com.ibm.db2.jcc.DB2Driver";                                                                                  
				url = "jdbc:db2://"+ip+":50000/"+db;
			}else if(type == DBType.Sybase){
				driver = "com.sybase.jdbc.SybDriver";                                                                                  
				url = "jdbc:sybase:Tds:"+ip+":5007/"+db;
			}else if(type == DBType.Sybase_JTDS){
				driver = "net.sourceforge.jtds.jdbc.Driver";                                                                                  
				url = "jdbc:jtds:sybase://"+ip+":5007/"+db;
			}else if(type == DBType.PostgreSql){
				driver = "org.postgresql.Driver";                                                                                  
				url = "jdbc:postgresql://"+ip+"/"+db;
			}else if(type == DBType.SQLite){
				driver = "org.sqlite.JDBC";                                                                                  
				url = "jdbc:sqlite:"+db;	//jdbc:sqlite:person.db
			}else if(type == DBType.H2){
				driver = "org.h2.Driver";                                                                                  
				url = "jdbc:h2:tcp://"+ip+"/~/"+db;
			}else if(type == DBType.HSQLDB){
				driver = "org.hsqldb.jdbcDriver";                                                                                  
				url = "jdbc:hsqldb:hsql://"+ip+":9001/"+db;
			}else if(type == DBType.Derby){
				driver = "org.apache.derby.jdbc.ClientDriver";                                                                                  
				url = "jdbc:derby://"+ip+":1527/"+db+";";
			}else if(type == DBType.ODBC){
				driver = "sun.jdbc.odbc.JdbcOdbcDriver";                                                                                  
				url = "jdbc:odbc:"+db;
			}
			
		}
	}

	private void getConnection() {
		if(!isDataSource){
			//1、通过JDBC获得链接
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, name, pwd);
			} catch (Exception e) {
				logger.error(e.toString(),e);
			}
		}else{
			//2、通过数据连接池获得链接
			try {
				Context ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup(datasource);
				this.conn = ds.getConnection();
			} catch (NamingException e) {
				logger.error(e.toString(),e);
			} catch (SQLException e) {
				logger.error(e.toString(),e);
			}
		}
	}

	/**
	 * 关闭资源
	 */
	protected void closeAll() {
		try {
			if (this.rs != null) {
				rs.close();
			}
			if (this.pstmt != null) {
				pstmt.close();
			}
			if (this.conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.toString(),e);
		}
	}

	/**
	 * 增删改通用
	 * @author hezhao
	 * @Time   2017年7月27日 下午4:22:09
	 * @Description 无
	 * @Version V 1.0
	 * @param sql
	 * @param prams
	 * @return
	 */
	protected int executeUpdate(String sql, Object... prams) {
		try {
			this.getConnection();
			this.pstmt = conn.prepareStatement(sql);
			if (prams != null) {
				for (int i = 0; i < prams.length; i++) {
					this.pstmt.setObject(i + 1, prams[i]);
				}
			}

			log(sql, prams);
			return this.pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString(),e);
		} finally {
			closeAll();
		}
		return 0;
	}
	
	/**
	 * 新增，返回主键
	 * @author hezhao
	 * @Time   2017年1月18日 下午2:23:07
	 * @param sql
	 * @param prams
	 * @return
	 */
	protected int executeInsert(String sql, Object... prams) {
		try {
			this.getConnection();
			
			//传入参数：STATEMENT.RETURN_GENERATED_KEYS，指定返回生成的主键  
			this.pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			if (prams != null) {
				for (int i = 0; i < prams.length; i++) {
					this.pstmt.setObject(i + 1, prams[i]);
				}
			}

			log(sql, prams);
			
			//执行sql
			int count = this.pstmt.executeUpdate();
			
			//返回的主键
			int autoIncKey = 0;
			
			if(count > 0){
				ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
				
				if (rs.next()) {
					autoIncKey = rs.getInt(1);//取得ID
				} else {
					// throw an exception from here
					logger.error("插入语句出错，没有获取到新增的ID！");
				}
				
			}
			
			return autoIncKey;
		} catch (SQLException e) {
			logger.error(e.toString(),e);
		} finally {
			closeAll();
		}
		return 0;
	}

	/**
	 * 查询通用
	 * @author hezhao
	 * @Time   2017年7月27日 下午4:22:45
	 * @Description 无
	 * @Version V 1.0
	 * @param sql
	 * @param prams
	 */
	protected void executeQuery(String sql, Object... prams) {
		try {
			this.getConnection();
			this.pstmt = conn.prepareStatement(sql);
			if (prams != null) {
				for (int i = 0; i < prams.length; i++) {
					this.pstmt.setObject(i + 1, prams[i]);
				}
			}

			log(sql, prams);
			this.rs = this.pstmt.executeQuery();
		} catch (SQLException e) {
			logger.error(e.toString(),e);
		}
	}

	/**
	 * 打印日志
	 * @author hezhao
	 * @Time   2017年7月27日 下午4:22:56
	 * @Description 无
	 * @Version V 1.0
	 * @param sql
	 * @param prams
	 */
	private void log(String sql, Object[] prams) {
		if(isLog) {
			System.out.println("===========" + sql);
			if (prams != null && prams.length > 0) {
				System.out.println("参数：");
				for (int i = 0; i < prams.length; i++) {
					if (i == prams.length - 1) {
						System.out.print(prams[i] + "\n");
					} else {
						System.out.print(prams[i] + ",");
					}
				}
			}
		}
	}

	/**
	 * 数据库类型
	 * @author hezhao on 2017年7月27日 上午11:26:08
	 *
	 */
	static enum DBType{
		MySQL,
		Oracle,
		SQLServer,	//SQLServer2005
		SQLServer_JTDS,	//使用jtds连接SQLServer
		DB2,
		Sybase,
		Sybase_JTDS,	//使用jtds连接Sybase
		PostgreSql,
		SQLite,
		Derby,
		H2,
		HSQLDB,
		ODBC;	//桥连接
	} 
	
}
