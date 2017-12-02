package com.medusabookdepot.view.observer;

import javax.mail.MessagingException;

import javafx.collections.ObservableList;

/**
 * Send E-mail
 */
public interface EmailViewObserver {
	
	/**
	 * Send an email to <i>receiver</i>, with <i>subject</i> and message <i>body</i>, with a mandatory <i>attachment</i> 
	 * @param receiver
	 * @param subject
	 * @param body
	 * @param attachment
	 */
	public void send(String receiver, String subject, String body, String attachment) throws MessagingException, IllegalArgumentException;
	
	/**
	 * This method populates the paths list with all the possible attachments, without the XML files
	 * @return the list of attachments, without XML files
	 */
	public ObservableList<String> setPaths();
}
