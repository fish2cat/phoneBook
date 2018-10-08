package cn.edu.sdtbu.phoneBook;

import java.text.Collator;
import java.util.Arrays;

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
	public int compareTo(Contract o) {		
		Collator instance = Collator.getInstance(java.util.Locale.CHINA);
		return  instance.compare(this.getName(),o.getName());
	}
	/*
	 * 合并通讯录中的两条记录
	 */
	public void mergeContract(Contract o) {
		if(this.getName().equals(o.getName())) {
			if(this.getGender().equals("") )
				this.setGender(o.getGender());
			if(this.getEmail().equals(""))
				this.setEmail(o.getEmail());
			/*
			 * 复制并去重
			 */
			boolean flag;
			String[] newPhones = new String[o.getPhones().length];
			int count = 0;
			for(int j = 0; j < o.getPhones().length; j++) {
				flag = true;
				for(int i = 0; i < this.getPhones().length; i++) {
					if(o.getPhones()[j].equals(this.getPhones()[i])) {
						flag = false;
						break;
					}
				}		
				if(flag) 
				{
				/*
				 * 添加
				 */
					newPhones[count++] = o.getPhones()[j];
				}
			}
			int position = phones.length;
			//扩容
			phones = Arrays.copyOf(phones, phones.length+count);			
			//newPhones追加在当前phones后面
			System.arraycopy(newPhones, 0, phones, position, count);
			
		}
	}
	public static void main(String[] args) {
		Contract c = new Contract("王新明", new String[] {"13602344578","13506334789","010-34567913"});
		c.display();		
	}	
}
