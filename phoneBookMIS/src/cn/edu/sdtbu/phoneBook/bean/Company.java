package cn.edu.sdtbu.phoneBook.bean;

public class Company{
	private int id;
	private String name;
	private String address;
	private String phone;
	private String fax;
	public Company() {
		
	}
	public Company(int id,String name, String address, String phone, String fax) {		
		this.setId(id);
		this.setName(name);
		this.setAddress(address);
		this.setPhone(phone);
		this.setFax(fax);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		return String.format("%s\t地址:%s\n联系电话：%s\t传真:%s\n", this.getName(),this.getAddress()
				,this.getPhone(), this.getFax());
	}
	
}
