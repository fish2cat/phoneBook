package cn.edu.sdtbu.phoneBook;

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
	public Partner(String name, String[] phones) {
		super(name, phones);		
	}
	public Partner(String name, String gender, String email, 
			String[] phones, String title, Company company) {
		super(name, gender, email, phones);
		setCompany(company);
		setTitle(title);
	}	
	public void display() {
		super.display();
		System.out.println("职务："+getTitle()+"\n工作单位："+getCompany());
	}
}
