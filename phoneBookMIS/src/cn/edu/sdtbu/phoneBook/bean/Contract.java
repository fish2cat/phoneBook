package cn.edu.sdtbu.phoneBook.bean;

import java.text.Collator;
import java.util.*;
import java.util.regex.Pattern;
class NameException extends Exception{
	public NameException() {
		super("姓名为空");
	}	
}
class GenderException extends Exception{
	public GenderException() {
		super("性别格式错误");
	}	
}
class EmailException extends Exception{
	public EmailException() {
		super("邮箱格式错误");
	}
}
class PhoneException extends Exception{
	public PhoneException() {
		super("电话号码格式错误");
	}
}
public class Contract implements Comparable<Contract>{
	private int id;
	private String name;
	private String gender;
	private String email;
	private List<String> phones;	
	public Contract() {
		
	}
	public Contract(int id,String name, String gender, String email,List<String> phones){
		try {
			setId(id);
			setName(name);
			setGender(gender);
			setEmail(email);		
			setPhones(phones);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	public Contract(int id,String name, List<String> phones){
		this(id,name,"","",phones);
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
	/*
	 * 姓名不能为空
	 */
	public void setName(String name) throws NameException{
		if(name == null || name.equals(""))
			throw new NameException();
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	/*
	 * 可以为空字符，男或者女之一
	 */
	public void setGender(String gender) throws GenderException{
		if(gender == null || !(gender.equals("男") || gender.equals("女") || gender.equals("")))
			throw new GenderException();
		else
			this.gender = gender;			
	}
	public String getEmail() {
		return email;
	}	
	public void setEmail(String email) throws EmailException{
		
		String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";		
		if(email != null && (email.equals("") || Pattern.matches(regex, email)))
			
				this.email = email;			
		else
			throw new EmailException();
			
	}
	public List<String> getPhones() {
		return phones;
	}	
	public void setPhones(List<String> phones) throws PhoneException{	
		String telReg = "^(0[1-9]\\d{1,2}\\-)?\\d{7,8}$";
		String phoneReg = "^1\\d{10}$";
		for(String phone:phones)
		if(phone == null || !(Pattern.matches(telReg, phone) || Pattern.matches(phoneReg, phone)))
			throw new PhoneException();			
		this.phones = phones;			
	}	
	public void display() {
		System.out.println("姓名："+getName()+"\t性别："+getGender()+"\te-mail："+getEmail());
		System.out.print("联系电话：\t");
		for(String phone:phones){
			System.out.print(phone+"\t");			
		}
		System.out.println();
	}
	public static void main(String[] args) {
		Contract c;
		List<String> list = new ArrayList<String>();
		list.add("13602344578");
		list.add("13506334789");
		list.add("010-34567913");
		try {
			c = new Contract(1,"王新明", list);
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		}
		c.display();	
		Contract cNew;
		List<String> listNew = new ArrayList<String>();
		listNew.add("13602344578");
		listNew.add("13506334788");
		listNew.add("010-34567914");
		try {
			cNew = new Contract(1,"王新明", listNew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		c.mergeContract(cNew);
		c.display();
	}
	@Override
	public int compareTo(Contract o) {		
		Collator instance = Collator.getInstance(java.util.Locale.CHINA);
		return  instance.compare(this.getName(),o.getName());
	}
	/*public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj != null && Contract.class.isAssignableFrom(obj.getClass())) {
			Contract c = (Contract)obj;
			return this.getName().equals(c.getName());
		}
		return false;			
	}*/
	/*
	 * 合并通讯录中的两条记录
	 */
	public void mergeContract(Contract o) {
		if(this.getName().equals(o.getName())) {
			if(this.getGender().equals("") )
				try {
					this.setGender(o.getGender());
				} catch (GenderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(this.getEmail().equals(""))
				try {
					this.setEmail(o.getEmail());
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			/*
			 * 复制并去重
			 */
			List<String> srcPhones = this.getPhones();			
			srcPhones.removeAll(o.getPhones());
			srcPhones.addAll(o.getPhones());
		}
	}
	public String toString() {
		return this.getName();
	}
}