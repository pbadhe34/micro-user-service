package com.services; 

import javax.xml.bind.annotation.XmlRootElement;
 
 
@XmlRootElement(name = "User")
public class User
{
   private String userId; 
   private String userPass;   
   private String userMail;
   
   public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getUserPass() {
	return userPass;
}

public void setUserPass(String userPass) {
	this.userPass = userPass;
}

public String getUserMail() {
	return userMail;
}

public void setUserMail(String userMail) {
	this.userMail = userMail;
}

private String userName;   
  public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

   
}
    

 
   
  
