package com.twilio;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import static spark.Spark.*;

import java.io.FileNotFoundException;
import java.util.Vector;

public class SmsReceive {
	

	String getGoogleLink(String item) {
		String newItem = item.replace(' ','+');
		return "https://www.google.com/search?q=" + newItem;
	}
	private void startServer(Vector<User> user) {
		 
		 post("/sms", (req, res) -> {
	        	
	        	//get phone number of sender 
	        	String str = req.body();   
	        	String[] str2 = str.split("&");
	        	String num = str2[17].substring(str2[17].indexOf('B') + 1, str2.length);
	      //-------------------------------------------------------------
	      
	      boolean inlist = false;
	      for (int i = 0; i < user.size(); ++i) {
	        		  
	    	    String comp = user.get(i).phoneNumber.replaceAll("\\p{C}", ""); //remove unicode prepended to first phonenumber from file import

	        	if (num.equals(comp) ) {
	        		
	        	  inlist = true;	 //if number is in list  
	        	
	              Body body = new Body.Builder("\n\nYour recipient is " + user.get(i).name + "\n\n" + //message body  
			                 "Their 3 choices are (from highest to lowest priority): \n" +
			                 user.get(i).item1 + " - " + getGoogleLink(user.get(i).item1) + "\n" +
			                 user.get(i).item2 + " - " + getGoogleLink(user.get(i).item2) + "\n" +
			                 user.get(i).item3 + " - " + getGoogleLink(user.get(i).item3)  )
	                    .build();
	        	 
	              Message sms = new Message.Builder()
	                    .body(body)
	                    .build();

	              MessagingResponse twiml = new MessagingResponse.Builder()
	                    .message(sms)
	                    .build();

	              return twiml.toXml();
	        	}
	        }
	      
	      if (inlist == false) {
  		    Body bod = new Body.Builder("\n\nPhone number " + num + " not found in Secret Santa list. Please contact Corri at (205) 478-6805.")
                  .build();
            Message sms = new Message.Builder()
                  .body(bod)
                  .build();
            MessagingResponse twiml = new MessagingResponse.Builder()
                  .message(sms)
                  .build();

            return twiml.toXml();
	      }
	      
		return req;
	      
	    });
	}
    public static void main(String[] args) throws FileNotFoundException {
    	FileProcessor file = new FileProcessor();
		MakeList ran = file.importList("/Users/ticorrianheard/Documents/SecretSantaList.txt");
		
		SmsReceive server = new SmsReceive();
		server.startServer( ran.getList() );
    }
}




 /** uses https configured using ngrok command: ./ngrok http 4567    (ngrok Ngrok exposes local servers behind NATs and firewalls to the public internet over secure tunnels.)
 * 
  listening on port 4567. If there is a process already using the port use commands to kill the process and try again:
     sudo lsof -i :3000
     kill -9 <PID>

 
 * */


/*
req is an object containing information about the HTTP request that raised the event. In response to req, you use res to send back the desired HTTP response.

Those parameters can be named anything. You could change that code to this if it's more clear:

	get("/", function(request, response){
	   	response.send('/ ' + request.params.id);
	   }
	    
Say I have this method:

get('/people.json', function(request, response) { });


flow:
  1. someone texts the twilio number
  2. when the message comes in, twilio makes a HTTP request to this code(app) by sending it to the ngrok url which is listening on port 4567
  3. Spark sees that the HTTP request was forwarded by ngrok to port 4567 and returns twiml instructions(twilio xml derivative)
  4. twilio gets the twiml response and processes it as a text message to send back to the original sender


get("/hello", (req, res) -> { //can open chrome dev console to view reqest/response data in "network"
	 return req.body();
});*/   
   
//get("/sms", (req, res) -> "hello world "); 

