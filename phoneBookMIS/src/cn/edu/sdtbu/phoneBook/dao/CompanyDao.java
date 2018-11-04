package cn.edu.sdtbu.phoneBook.dao;

import java.util.Vector;

import cn.edu.sdtbu.phoneBook.bean.Company;

public interface CompanyDao {
	int add(Company c) throws Exception;
	boolean update(Company c) throws Exception;	
	Vector<Company> searchAll() throws Exception;
	//Company searchById(int id) throws Exception;
}
