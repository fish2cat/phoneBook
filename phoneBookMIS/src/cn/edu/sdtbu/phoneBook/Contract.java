package cn.edu.sdtbu.phoneBook;
public class Contract {
	private String name;
	private String gender;
	private String email;
	private String[] phones;	
	public Contract() {
		
	}
	public Contract(String name, String gender, String email,String[] phones) {
		setName(name);
		setGender(gender);
		setEmail(email);		
		setPhones(phones);
	}
	public Contract(String name, String[] phones) {
		this(name,"","",phones);
	}	
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		if(name == null || name.equals(""))
			return;
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}	
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getPhones() {
		return phones;
	}	
	public void setPhones(String[] phones) {		
		this.phones = phones;			
	}	
	public void display() {
		System.out.println("姓名："+getName()+"\t性别："+getGender()+"\te-mail："+getEmail());
		System.out.print("联系电话：\t");
		for(int i = 0; i < phones.length;i++){
			System.out.print(phones[i]+"\t");			
		}
		System.out.println();
	}
	public static void main(String[] args) {
		Contract c = new Contract("王新明", new String[] {"13602344578","13506334789","010-34567913"});
		c.display();		
	}	
}
