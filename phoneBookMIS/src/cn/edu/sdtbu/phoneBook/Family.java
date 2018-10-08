package cn.edu.sdtbu.phoneBook;

import java.text.SimpleDateFormat;

public class Family extends Contract {
	private java.util.Date birthday;
	private String address;
	public Family() {
		
	}
	public Family(String name, String[] phones) {
		super(name, phones);		
	}
	public Family(String name, String gender, String email, 
			String[] phones,java.util.Date birthday, String address) {
		super(name, gender, email, phones);
		setBirthday(birthday);
		setAddress(address);
	}	
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void display() {
		super.display();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("出生日期："+sdf.format(getBirthday())+"\t家庭住址："+getAddress());
	}
}
