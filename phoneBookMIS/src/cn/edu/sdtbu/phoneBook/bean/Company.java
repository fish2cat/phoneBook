package cn.edu.sdtbu.phoneBook.bean;

public class Company{
	private String name;
	private String address;
	private String phone;
	private String fax;
	public Company() {
		
	}
	public Company(String name, String address, String phone, String fax) {		
		this.setName(name);
		this.setAddress(address);
		this.setPhone(phone);
		this.setFax(fax);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String toString() {
		return String.format("%s\t��ַ:%s\n��ϵ�绰��%s\t����:%s\n", this.getName(),this.getAddress()
				,this.getPhone(), this.getFax());
	}
	
}
