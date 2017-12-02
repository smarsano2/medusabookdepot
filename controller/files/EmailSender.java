package com.medusabookdepot.controller.files;

import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.medusabookdepot.view.observer.EmailViewObserver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is a simple email sender, with an hard-coded Gmail username and password as static fields.
 * It requires javax.mail.jar library, and it uses SMTP to connect to Gmail.
 * 
 * It is basic, just to match the requirements of the app, but it could be easily modified to work 
 * with any SMTP capable account. To do this, you need to add more parameters to send() method, such
 * as String username, String password, int smtp, etc.
 */
public class EmailSender implements EmailViewObserver {

	// Gmail username and password
    private static final String USERNAME = "oop15bookdepot";
    private static final String PASSWORD = "noteasytoguess";
    
    private final ObservableList<String> paths = FXCollections.observableArrayList();
    
    /* MAIN METHOD ONLY FOR TESTING PURPOSE
        public static void main(String[] args) throws MessagingException {
        String receiver = "andread251@gmail.com"; // list of recipient email addresses
        String subject = "Here are the exported files";
        String body = "Check if now it works with the correct name file!";
        String attachment = "prova.jpg";
        send(receiver, subject, body, attachment);
        System.out.println("Mail Sent Successfully");
    }//*/

    /**
     * @param receiver
     * - The email address of the receiver (it works also if you put more than one receiver, separated by a comma)
     * @param subject
     * - The subject of the email
     * @param body
     * - The text body of the message
     * @param attachment
     * - One attachment file
     */
    public void send(String receiver, String subject, String body, String attachment) throws MessagingException, IllegalArgumentException {
    	
    	// Checking for blank fields
    	if(receiver.isEmpty() || subject.isEmpty() || body.isEmpty() || attachment.isEmpty()) throw new IllegalArgumentException("Please fill all the blank fields");
    	
        Properties props = System.getProperties();
        
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME+"@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, receiver);
        message.setSubject(subject);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(FileManager.getDirectoryPath()+attachment);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachment);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        Transport tr = session.getTransport("smtps");
        tr.connect(host, USERNAME, PASSWORD);
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();

    }
    
    /**
     * This method populates the paths list with all the possible attachments
     */
	public ObservableList<String> setPaths(){
		File directory = new File(FileManager.getDirectoryPath());
		File[] filesArray = directory.listFiles();
		for(int i=0; i<filesArray.length; i++){
			if(filesArray[i].isFile()){
				if(!filesArray[i].getName().endsWith("xml"))
					paths.add(filesArray[i].getName());
		    }
		}
		return paths;
	}
}
