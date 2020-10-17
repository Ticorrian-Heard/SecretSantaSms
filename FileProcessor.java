package com.twilio;

import java.util.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileProcessor {

	public MakeList importList(String pathway) throws FileNotFoundException {
		
		File myObj = new File(pathway);
	    Scanner myReader = new Scanner(myObj);
		MakeList ran = new MakeList();
		
		 while(myReader.hasNextLine()) { // create user list from txt file
			
			String data = myReader.nextLine();
	        String[] string = data.split(",",0);
			 
			User user = new User();
			
			user.phoneNumber = string[0];
			user.name        = string[1];
			user.item1       = string[2];
			user.item2       = string[3];
			user.item3       = string[4];
			
			ran.adduser(user);
		}
		myReader.close();
		return ran;
	}
	
	
	public void exportList(Vector<User> list) throws IOException {
		
	   FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Documents/" + "SecretSantaList.txt");
	   PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	   for (int i = 0; i < list.size(); i++) {
		   printWriter.println(list.get(i).phoneNumber + "," + list.get(i).recipient.name + "," +list.get(i).recipient.item1 + "," + list.get(i).recipient.item2 + "," + list.get(i).recipient.item3 );
	   }
	   
	   printWriter.flush();
	   printWriter.close();
	}
}
