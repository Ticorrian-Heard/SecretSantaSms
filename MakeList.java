package com.twilio;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

class MakeList implements Cloneable {
	
	private Vector<User> userList = new Vector<User>();
	
	public void adduser(User user) {		
		userList.add(user);
	}
	public Vector<User> getList() {
		return userList;
	}
	
    int getRandomWithExclusion(Random rnd, int start, int end, int index, Vector<Integer> ex) {
    	
    	@SuppressWarnings("unchecked")
		Vector<Integer> ex2 = (Vector<Integer>) ex.clone();
	    int random = start + rnd.nextInt(end - start + 1 - ex.size());
	    
	    for (int i = 0; i <= ex.size()-1; i++) {
	        if (random < ex.get(i) ) {
	        	if (random == index) {
	        		ex2.add(random);
	        		random = getRandomWithExclusion(new Random(), 0, end, index, ex2);	
	        	}
	            break;
	        }
	        random++;
	    }
	    
	    if (random == 0 && index == 0) random++;
	    else if (random == end && index == end) return -1;
	    return random;
	}
	public Vector<User> generateSecretSantaList() {
		
       Vector<Integer> ex = new Vector<Integer>();
		
		for (int i=0; i<userList.size(); i++) {
			int num = getRandomWithExclusion(new Random(), 0, userList.size() - 1, i, ex);
			
			if (num == -1) {
				userList.get(userList.size() - 1).recipient = userList.get(0).recipient;  //switch first and last when end user get assigned to itself
				userList.get(0).recipient = userList.get(userList.size() - 1); 
			}
			
			else userList.get(i).recipient = userList.get(num);
			ex.add(num);
			Collections.sort(ex);
		}
		
		return userList;
	}
	
}
