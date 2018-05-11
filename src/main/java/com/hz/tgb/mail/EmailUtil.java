package com.hz.tgb.mail;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 发送邮件工具类。
 * 必须在email.properties配置邮箱服务器配置。
 * @author hezhao
 * @date 2015年11月25日
 */
public class EmailUtil 
{
	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
	// 邮件发送协议 
    private static final String PROTOCOL = "smtp"; 
    
    // SMTP邮件服务器默认端口 
    private static final String PORT = "25"; 

    // 是否要求身份认证 
    private static final String IS_AUTH = "true"; 

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息） 
    private static String IS_ENABLED_DEBUG_MOD; 

    // SMTP邮件服务器 
    private static String HOST; 
    
    //邮件服务器帐号
    private static String ACCOUNT;
    
    //邮件服务器密码
    private static String PASSWORD;
    
    //邮件发送方地址
    private static String FROM_EMAIL;
    
  //邮件发送方名称
    private static String FROM_NAME;

    // 初始化连接邮件服务器的会话信息 
    private static Properties props = null; 
    
    static
    {
    	loadConfig();
    	
    	//创建配置
    	props = new Properties(); 
        props.setProperty("mail.transport.protocol", PROTOCOL); 
        props.setProperty("mail.smtp.host", HOST); 
        props.setProperty("mail.smtp.port", PORT); 
        props.setProperty("mail.smtp.auth", IS_AUTH); 
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD); 
    }

    /**
     * 加载邮件服务器配置
     */
    private static void loadConfig()
	{
		ResourceBundle bundle = ResourceBundle.getBundle("email");
		
		HOST = bundle.getString("email_smtp_host");
		
		ACCOUNT = bundle.getString("email_account");
		
		PASSWORD = bundle.getString("email_password");
		
		FROM_EMAIL = bundle.getString("email_from");
		
		FROM_NAME = bundle.getString("email_from_name");
		
		IS_ENABLED_DEBUG_MOD = bundle.getString("email_debug");
		
		if(StringUtils.isBlank(HOST))
		{
			logger.error("获取不到邮件服务器地址配置: email_smtp_host！");
		}
		
		if(StringUtils.isBlank(ACCOUNT))
		{
			logger.error("获取不到邮件服务器帐号配置: email_account！");
		}
		
		if(StringUtils.isBlank(PASSWORD))
		{
			logger.error("获取不到邮件服务器密码配置: email_password！");
		}
		
		if(StringUtils.isBlank(FROM_EMAIL))
		{
			logger.error("获取不到邮件发送方地址配置: email_from！");
		}
		
		if(StringUtils.isBlank(IS_ENABLED_DEBUG_MOD))
		{
			IS_ENABLED_DEBUG_MOD = "false";
		}
		
		if(StringUtils.isBlank(FROM_NAME))
		{
			FROM_NAME = "中车信息";
		}
	}
    
    /**
     * 发送邮件。
     *
     * @param toEmail 目的邮箱
     * @param subject 标题
     * @param content 内容，可以是html
     * @return
     */
    public static boolean sendEmail(String toEmail, String subject, String content)
    {
    	boolean sendResult = false;
    	
    	// 创建Session实例对象 
        Session session = Session.getInstance(props, new MyAuthenticator()); 

        
        // 创建MimeMessage实例对象 
        MimeMessage message = new MimeMessage(session); 
       
        try 
        {
        	 // 设置邮件主题 
			message.setSubject(subject);
			
			 // 设置发送人 
			String nick=javax.mail.internet.MimeUtility.encodeText("中车信息");
	        message.setFrom(new InternetAddress(FROM_EMAIL,nick)); 
	        
	        // 设置发送时间 
	        message.setSentDate(new Date()); 
	        
	        // 设置收件人 
	        message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail)); 
	        
	        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk 
	        message.setContent(content,"text/html;charset=utf-8"); 

	        // 保存并生成最终的邮件内容 
	        message.saveChanges(); 

	        // 发送邮件 
	        Transport.send(message); 
	        
	        sendResult = true;
		} 
        catch (MessagingException e) 
		{
			e.printStackTrace();
			logger.error("发送邮件失败，目标邮箱："+ toEmail + "！ 异常信息：" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("发送邮件失败，目标邮箱："+ toEmail + "！ 异常信息：" + e.getMessage());
		} 
    	
    	return sendResult;
    }
    
    /**
     * 向邮件服务器提交认证信息
     */ 
    static class MyAuthenticator extends Authenticator 
    { 
        private String username = ACCOUNT; 

        private String password = PASSWORD; 

        public MyAuthenticator() { 
            super(); 
        } 

        public MyAuthenticator(String username, String password) 
        { 
            super(); 
            this.username = username; 
            this.password = password; 
        } 

        @Override 
        protected PasswordAuthentication getPasswordAuthentication() 
        { 
            return new PasswordAuthentication(username, password); 
        } 
    } 
    
    /**
     * 发送邮件。
     *
     * @param toEmail 目的邮箱
     * @param subject 标题
     * @param content 内容，可以是html
     * @param replyEmail 回复Email
     * @param companyName 公司名称
     * @return
     */
    public static boolean sendEmail(String toEmail,String replyEmail,String companyName ,String subject, String content)
    {
    	boolean sendResult = false;
    	
    	// 创建Session实例对象 
        Session session = Session.getInstance(props, new MyAuthenticator()); 

        
        // 创建MimeMessage实例对象 
        MimeMessage message = new MimeMessage(session); 
       
        try 
        {
        	 // 设置邮件主题 
			message.setSubject(subject);
			
			 // 设置发送人 
			String nick=javax.mail.internet.MimeUtility.encodeText("中车信息");
			
			message.setFrom(new InternetAddress(FROM_EMAIL,nick)); 
	        
	        Address address[] = new InternetAddress[1];
	        //企业名称
	        String name=javax.mail.internet.MimeUtility.encodeText(companyName);
	        
	        address[0] = new InternetAddress(replyEmail,name);
	        
	        message.setReplyTo(address);
	        
	        // 设置发送时间 
	        message.setSentDate(new Date()); 
	        
	        // 设置收件人 
	        message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail)); 
	        
	        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk 
	        message.setContent(content,"text/html;charset=utf-8"); 

	        // 保存并生成最终的邮件内容 
	        message.saveChanges(); 

	        // 发送邮件 
	        Transport.send(message); 
	        
	        sendResult = true;
		} 
        catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
    	
    	return sendResult;
    }
}
