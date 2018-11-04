package cn.edu.sdtbu.phoneBook.dao;

import java.util.List;

import cn.edu.sdtbu.phoneBook.bean.Contract;

public interface ContractDao {
	boolean add(Contract c) throws Exception;
	boolean deleteById(int contractId) throws Exception;
	boolean update(Contract c) throws Exception;
	List<Contract> searchByName(String name) throws Exception;	
}
