package cn.edu.sdtbu.phoneBook;

import java.util.Arrays;

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
	private Contract[] contracts;
	
	public PhoneBook() {
		
	}
	public PhoneBook(Contract[] contracts) {
		setContracts(contracts);
		
	}
	public Contract[] getContracts() {
		return contracts;
	}
	public void setContracts(Contract[] contracts) {	
		this.contracts = contracts;			
		sortContracts();
	}
	/*
	 * 升序排列
	 */
	private void sortContracts() {
		for(int i = 0; i < contracts.length; i++) {
			int temp = i;
			for(int j = i+1; j < contracts.length; j++)
				if(contracts[temp].compareTo(contracts[j]) >0)
					temp = j;
			if(temp != i) {
			Contract c = contracts[temp];
			contracts[temp] = contracts[i];
			contracts[i] = c;
			}
		}
	}
	/*
	 * 添加联系人
	 * 首先查询是否包含，包含则合并
	 * 不包含该联系人，则添加。
	 */
	private int findContract(Contract c) {
		for(int i = 0; i <contracts.length; i++) {
			if(contracts[i].getName().equals(c.getName())) {
				return i;
			}
		}
		return -1;
	}
	public void add(Contract c) {
		
		if(contracts == null) {
			contracts = new Contract[1];
			contracts[0] = c;
			return;
		}
		int index = findContract(c);
		if(index <0) {	
			//添加操作		
			//扩容
			Contract[] contractAdded = Arrays.copyOf(contracts, contracts.length + 1);
			contractAdded[contractAdded.length-1] = c;			
			this.setContracts(contractAdded);
			return;
		}else
			contracts[index].mergeContract(c);
		
		
	}
	public boolean delete(Contract c) {		
		int index = findContract(c);
		if(index < 0)
			return false;
		Contract[] contractDeleted = new Contract[contracts.length-1];		
		System.arraycopy(contracts, 0, contractDeleted, 0, index);
		System.arraycopy(contracts, index+1, contractDeleted, index, contracts.length-1-index);
		contracts = contractDeleted;		
		return true;
	}
	/*
	 * 模糊查询
	 */
	public Contract[] findNameContains(String name) {
		Contract[] result = new Contract[contracts.length];
		int num = 0;
		for(int i = 0; i < contracts.length; i++) {
			if(contracts[i].getName().contains(name))
				result[num++] = contracts[i];			
		}
		return Arrays.copyOf(result, num);
	}
	/*
	 * 显示通讯录
	 */
	public void display() {
		for(int i = 0; i < contracts.length; i++) {
			contracts[i].display();			
		}
	}
	public boolean updateContract(String name, String gender, 
			String email, String[] phones) {
		int index = findContract(new Contract(name,gender,email,phones));		
		if(index <0)
			return false;
		contracts[index].setGender(gender);
		contracts[index].setEmail(email);
		contracts[index].setPhones(phones);
		return true;		
	}
	public void clearContracts() {
		contracts = null;
	}
}
