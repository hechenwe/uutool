package com.eduspace.service.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.eduspace.util.PathUtil;
import com.eduspace.util.PropertiesUtil;


public class EmailService {

	/**
     * 发送电子邮件
     * @param email 邮件地址
     * @param subject 主题
     * @param body 正文 
     * @throws UnsupportedEncodingException
     */
    public static void sendEmail(String email, String subject, String body) throws UnsupportedEncodingException {
        try {
        	PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath()+"email.properties");
        	
        	String server = pu.getString("server");
        	String port = pu.getString("port");
        	String from = pu.getString("from");
        	String user = pu.getString("user");
        	String password = pu.getString("password");
        	
            Properties props = new Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            Transport transport = null;
            Session session = Session.getDefaultInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = new InternetAddress(user,from,"UTF-8");
            msg.setFrom(fromAddress);
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject, "UTF-8");   
           // msg.setText(body, "UTF-8");
            msg.setContent(body, "text/html;charset=utf-8"); 
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
