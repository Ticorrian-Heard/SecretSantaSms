package com.twilio;

import java.util.Vector;

import java.io.IOException;

public class SecretSantaSms {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		FileProcessor file = new FileProcessor();
		MakeList ran = file.importList("/Users/ticorrianheard/Documents/SecretSanta.txt");
		
		Vector<User> list = ran.generateSecretSantaList();
		
		SmsSender sms = new SmsSender();
		sms.send(list);  //send sms
		file.exportList(list);
	}
}
