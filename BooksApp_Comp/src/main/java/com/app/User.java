package com.app;

public class User {
	
 private String name;
 private String dob;
 private String emailid;
 private String mobile;
 private String password;
 private String confirmPword;
 
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}

public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getPassword() {
	return password;
}
public void setPword(String password) {
	this.password = password;
}
public String getConfirmPword() {
	return confirmPword;
}
public void setConfirmPword(String confirmPword) {
	this.confirmPword = confirmPword;
}

}
