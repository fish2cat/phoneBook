package cn.edu.sdtbu.phoneBook;

import java.util.*;
/*实现一个通讯录；

通讯录可以用来存储联系人的信息，每个人的信息包括：

姓名、性别、年龄、电话、住址

提供方法：

1. 添加联系人信息

2. 删除指定联系人信息

3. 查找指定联系人信息

4. 修改指定联系人信息

5. 显示所有联系人信息

6. 清空所有联系人

7. 以名字排序所有联系人

*/
public class PhoneBook {	
	private List<Contract> contracts;
	
	public PhoneBook() {
		
	}
	public PhoneBook(List<Contract> contracts) {
		setContracts(contracts);
		
	}
	public List<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(List<Contract> contracts) {					
		Collections.sort(contracts);
		this.contracts = contracts;	
	}	
	
	public void add(Contract c) {		
		if(contracts == null) {
			contracts = new ArrayList<Contract>();
			contracts.add(c);
			return;
		}
		/*受compareTo方法影响
		 * 
		 */
		int index = Collections.binarySearch(this.getContracts(), c);
		
		if(index <0){
			
				contracts.add(-index-1, c);
			return;
		}
		else
			contracts.get(index).mergeContract(c);
		
		
	}
	public boolean delete(Contract c) {	
		/*删除之前的查询，使用equals方法，所以要先重写该方法。
		 * 当然，这个方法也可以在查询之后再调用，保证引用相等的前提下使用，这样就可以不用重写equals方法。
		 */
		return contracts.remove(c);		
	}
	/*
	 * 模糊查询
	 */
	public List<Contract> findNameContains(String name) {
		List<Contract> result = new ArrayList<Contract>();
		Iterator<Contract> iter = contracts.iterator();
		while(iter.hasNext()) {
			Contract c = iter.next();
			if(c.getName().contains(name))
				result.add(c);
		}
		return result;
	}
	/*
	 * 显示通讯录
	 */
	public void display() {
		for(int i = 0; i < contracts.size(); i++) {
			contracts.get(i).display();			
		}
	}
	public boolean updateContract(String name, String gender, 
			String email, List<String> phones) throws Exception {
		int index = Collections.binarySearch(contracts, new Contract(name,gender,email,phones));		
		if(index <0)
			return false;
		contracts.get(index).setGender(gender);
		contracts.get(index).setEmail(email);
		contracts.get(index).setPhones(phones);
		return true;		
	}
	public void clearContracts() {
		contracts.clear();
	}
}
