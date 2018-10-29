package cn.edu.sdtbu.phoneBook.bean;

import java.util.List;

public class Partner extends Contract {
	private String title;
	private Company company;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Partner() {
		
	}
	public Partner(int id,String name, List<String> phones) {
		super(id,name, phones);		
	}
	public Partner(int id,String name, String gender, String email, 
			List<String> phones, String title, Company company) {
		super(id,name, gender, email, phones);
		setCompany(company);
		setTitle(title);
	}
	
	public void display() {
		super.display();
		System.out.println("职务："+getTitle()+"\n工作单位："+getCompany());
	}
}
