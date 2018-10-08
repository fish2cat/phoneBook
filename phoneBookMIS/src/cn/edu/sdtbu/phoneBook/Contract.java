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
		System.out.println("������"+getName()+"\t�Ա�"+getGender()+"\te-mail��"+getEmail());
		System.out.print("��ϵ�绰��\t");
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
	 * �ϲ�ͨѶ¼�е�������¼
	 */
	public void mergeContract(Contract o) {
		if(this.getName().equals(o.getName())) {
			if(this.getGender().equals("") )
				this.setGender(o.getGender());
			if(this.getEmail().equals(""))
				this.setEmail(o.getEmail());
			/*
			 * ���Ʋ�ȥ��
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
				 * ���
				 */
					newPhones[count++] = o.getPhones()[j];
				}
			}
			int position = phones.length;
			//����
			phones = Arrays.copyOf(phones, phones.length+count);			
			//newPhones׷���ڵ�ǰphones����
			System.arraycopy(newPhones, 0, phones, position, count);
			
		}
	}
	public static void main(String[] args) {
		Contract c = new Contract("������", new String[] {"13602344578","13506334789","010-34567913"});
		c.display();		
	}	
}
