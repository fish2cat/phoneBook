package cn.edu.sdtbu.phoneBook;

import java.text.Collator;
import java.util.Arrays;
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
public class Contract{
	private String name;
	private String gender;
	private String email;
	private String[] phones;	
	public Contract() {
		
	}
	public Contract(String name, String gender, String email,String[] phones){
		try {
			setName(name);
			setGender(gender);
			setEmail(email);		
			setPhones(phones);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	public Contract(String name, String[] phones){
		this(name,"","",phones);
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
	public String[] getPhones() {
		return phones;
	}	
	public void setPhones(String[] phones) throws PhoneException{	
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
		for(int i = 0; i < phones.length;i++){
			System.out.print(phones[i]+"\t");			
		}
		System.out.println();
	}
	public static void main(String[] args) {
		Contract c;
		try {
			c = new Contract("王新明", new String[] {"13602344578","13506334789","010-34567913"});
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		}
		c.display();	
		Contract cNew;
		try {
			cNew = new Contract("王新明", new String[] {"13602344578","13506334788","0535-34567914"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		c.mergeContract(cNew);
		c.display();
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
}