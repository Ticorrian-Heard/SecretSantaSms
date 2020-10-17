package com.twilio;

import java.util.Vector;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

class SmsSender { //change this back to SmsSend
	
	public static final String ACCOUNT_SID = System.getenv("account_sid");
	public static final String AUTH_TOKEN = System.getenv("auth_token");
	public static final String TWILIO_NUMBER = System.getenv("phone_number");;
	
	String getGoogleLink(String item) {
		String newItem = item.replace(' ','+');
		return "https://www.google.com/search?q=" + newItem;
	}
	
	public void send(Vector<User> user) {
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		for (int i = 0; i < user.size(); i++) {
		     Message.creator(
		    		     new PhoneNumber(user.get(i).phoneNumber), // to
		                 new PhoneNumber(TWILIO_NUMBER), // from
		                 "\n\nYour recipient is " + user.get(i).recipient.name + "\n\n" + //message body
		                 "Their 3 choices are (from highest to lowest priority): \n" +
		                 user.get(i).recipient.item1 + " - " + getGoogleLink(user.get(i).recipient.item1) + "\n" +
		                 user.get(i).recipient.item2 + " - " + getGoogleLink(user.get(i).recipient.item2) + "\n" +
		                 user.get(i).recipient.item3 + " - " + getGoogleLink(user.get(i).recipient.item3) )
		                 .create();
		}
	}
}
