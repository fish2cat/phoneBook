package cn.edu.sdtbu.phoneBook;

import java.util.*;
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
		/*��compareTo����Ӱ��
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
		/*ɾ��֮ǰ�Ĳ�ѯ��ʹ��equals����������Ҫ����д�÷�����
		 * ��Ȼ���������Ҳ�����ڲ�ѯ֮���ٵ��ã���֤������ȵ�ǰ����ʹ�ã������Ϳ��Բ�����дequals������
		 */
		return contracts.remove(c);		
	}
	/*
	 * ģ����ѯ
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
	 * ��ʾͨѶ¼
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
