package cn.edu.sdtbu.phoneBook;

import java.util.Arrays;

/*ʵ��һ��ͨѶ¼��

ͨѶ¼���������洢��ϵ�˵���Ϣ��ÿ���˵���Ϣ������

�������Ա����䡢�绰��סַ

�ṩ������

1. �����ϵ����Ϣ

2. ɾ��ָ����ϵ����Ϣ

3. ����ָ����ϵ����Ϣ

4. �޸�ָ����ϵ����Ϣ

5. ��ʾ������ϵ����Ϣ

6. ���������ϵ��

7. ����������������ϵ��

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
	 * ��������
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
	 * �����ϵ��
	 * ���Ȳ�ѯ�Ƿ������������ϲ�
	 * ����������ϵ�ˣ�����ӡ�
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
			//��Ӳ���		
			//����
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
	 * ģ����ѯ
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
	 * ��ʾͨѶ¼
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
