package com.hz.tgb.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import com.hz.tgb.common.prop.PropsUtil;
import com.hz.tgb.file.FileLog;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送(配置信息基于腾讯企业邮箱)
 *
 * @author hezhao
 * @date 2017-03-14
 * by luwenyue_mail
 */
public class Mail {
	/**
	 * 发件人
	 */
	private String from;
	/**
	 * 发件人昵称
	 */
	private String nickname_from;
	/**
	 * 收件人
	 */
	private String to;
	/**
	 * 收件人昵称
	 */
	private String nickname_to;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 发件方式
	 */
	private String send;
	/**
	 * 收件方式
	 */
	private String get;
	/**
	 * 发件端口号
	 */
	private String smtpport;
	/**
	 * 收件端口号
	 */
	private String imapport;
	/**
	 * 发件邮件服务器
	 */
	private String stmpmailServer;
	/**
	 * 收件邮件服务器
	 */
	private String imapmailServer;
	/**
	 * 邮件内容
	 */
	private String mailContent;
	/**
	 * 邮件标题
	 */
	private String mailSubject;

	
	/**
	 * 初始化邮件组件
	 * @param to 收件人
	 * @param nickname_to 收件人昵称
	 * @param mailSubject 邮件标题
	 * @param mailContent 邮件内容
	 */
	public Mail(String to,String nickname_to,String mailSubject,String mailContent) {
		//初始化属性文件
		PropsUtil propsUtil = new PropsUtil("email");
		//获得Map集合
		Map<String, String> configMap = propsUtil.loadPropsToMap();
				
		// 设置邮件信息
		this.username = configMap.get("mail.username");
		this.password = configMap.get("mail.password");
		this.stmpmailServer = configMap.get("mail.stmp.mailServer");
		this.imapmailServer = configMap.get("mail.imap.mailServer");
		this.send = configMap.get("mail.from");
		this.get = configMap.get("mail.get");
		this.from = configMap.get("mail.from");
		this.nickname_from = configMap.get("mail.nickname");
		this.smtpport = configMap.get("mail.smtp.port");
		this.imapport = configMap.get("mail.imap.port");
		
		this.to = to;
		this.nickname_to = nickname_to;
		this.mailSubject = mailSubject;
		this.mailContent = mailContent;
	}

	/**
	 * 发送邮件
	 * @author hezhao
	 * @Time   2017年3月13日 上午11:25:15
	 */
	public void send() {
		System.out.println("正在发送邮件至:::["+to+"]  ...");
		
		// 设置邮件服务器
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", stmpmailServer);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", this.send);
		prop.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.port", this.smtpport);
		prop.put("mail.smtp.socketFactory.fallback", "false");

		// 使用SSL，企业邮箱必需！
		// 开启安全协议
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

		// 获取Session对象
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			// 此访求返回用户和密码的对象
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				PasswordAuthentication pa = new PasswordAuthentication(username,
						password);
				return pa;
			}
		});
		// 设置session的调试模式，发布时取消
		session.setDebug(true);

		try {
			// 封装Message对象
			Message message = new MimeMessage(session);
			// message.setFrom(new InternetAddress(from,from)); //设置发件人

			// 设置自定义发件人昵称
			String nick_from = "";
			try {
				nick_from = javax.mail.internet.MimeUtility.encodeText(this.nickname_from);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setFrom(new InternetAddress(nick_from + " <" + from + ">"));

			// 设置自定义收件人昵称
			String nick_to = "";
			try {
				nick_to = javax.mail.internet.MimeUtility.encodeText(this.nickname_to);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(nick_to + " <" + to + ">"));// 设置收件人
			message.setSubject(mailSubject);// 设置主题
			message.setContent(mailContent, "text/html;charset=utf8");// 设置内容(设置字符集处理乱码问题)
			message.setSentDate(new Date());// 设置日期

			// 发送
			Transport.send(message);
			System.out.println("发送成功...");

			//保存邮件到发件箱
			saveEmailToSentMailFolder(message);
			
			if(mailSubject.contains("考勤")){
				FileLog.writeLog(this.nickname_to + " <" + to + ">发送成功");
			}else{
				FileLog.writeLog(this.nickname_to + " <" + to + ">发送成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送邮件异常...");
			
			if(mailSubject.contains("考勤")){
				FileLog.writeLog(this.nickname_to + " <" + to + ">发送失败");
			}else{
				FileLog.writeLog(this.nickname_to + " <" + to + ">发送失败");
			}
		}
	}

	/**
	 * 获取用户的发件箱文件夹
	 * 
	 * @param message
	 *            信息
	 * @param store
	 *            存储
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	private Folder getSentMailFolder(Message message, Store store)
			throws IOException, MessagingException {
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", get);
		props.setProperty("mail.imap.host", imapmailServer);
		props.setProperty("mail.imap.port", "143");

		/** QQ邮箱需要建立ssl连接 */
		props.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.starttls.enable", "true");
		props.setProperty("mail.imap.socketFactory.port", imapport);

		// 创建Session实例对象
		Session session = Session.getInstance(props);
		URLName urln = new URLName(get, imapmailServer, 143, null,
				username, password);
		// 创建IMAP协议的Store对象
		store = session.getStore(urln);
		store.connect();

		// 获得发件箱
		Folder folder = store.getFolder("Sent Messages");
		// 以读写模式打开发件箱
		folder.open(Folder.READ_WRITE);

		return folder;
	}

	/**
	 * 保存邮件到发件箱
	 * 
	 * @param message
	 *            邮件信息
	 */
	private void saveEmailToSentMailFolder(Message message) {

		Store store = null;
		Folder sentFolder = null;
		try {
			sentFolder = getSentMailFolder(message, store);
			message.setFlag(Flag.SEEN, true); // 设置已读标志
			sentFolder.appendMessages(new Message[] { message });
			
			System.out.println("已保存到发件箱...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 判断发件文件夹是否打开如果打开则将其关闭
			if (sentFolder != null && sentFolder.isOpen()) {
				try {
					sentFolder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			// 判断邮箱存储是否打开如果打开则将其关闭
			if (store != null && store.isConnected()) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
