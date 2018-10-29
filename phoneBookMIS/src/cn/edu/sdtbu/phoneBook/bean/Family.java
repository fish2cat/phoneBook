package cn.edu.sdtbu.phoneBook.bean;

import java.text.SimpleDateFormat;
import java.util.List;

public class Family extends Contract {
	private java.util.Date birthday;
	private String address;
	public Family() {
		
	}
	public Family(int id,String name, List<String> phones) {
		super(id,name, phones);		
	}
	public Family(int id,String name, String gender, String email, 
			List<String> phones,java.util.Date birthday, String address) {
		super(id,name, gender, email, phones);
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
