package cn.edu.sdtbu.phoneBook.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import cn.edu.sdtbu.phoneBook.bean.*;
import cn.edu.sdtbu.phoneBook.util.DBTool;

public class ContractDaoImpl implements ContractDao {
	private String getStringPhones(List<String>phones) {
		StringBuilder sb = new StringBuilder();
		sb.append(phones.get(0));
		for(int i = 1; i < phones.size(); i++) {
			sb.append(";");
			sb.append(phones.get(i));			
		}
		return new String(sb);
	}

	@Override
	public boolean add(Contract c) throws SQLException {
		boolean flag = false;
		String strPhones = getStringPhones(c.getPhones());
		int catalog = 0;
		java.sql.Date birthday = null;
		String address = null;
		int companyId = 0;
		String title = null;		
		if(c.getClass() == Family.class) {
			catalog = 1;
			java.util.Date birth = ((Family)c).getBirthday();
			if(birth != null)
				birthday =new java.sql.Date(birth.getTime());
			address = ((Family)c).getAddress();
		}
		if(c.getClass() == Partner.class) {
			catalog = 2;
			companyId = ((Partner)c).getCompany().getId();
			title = ((Partner)c).getTitle();
			}
		Connection conn = DBTool.getConnection();
		
		String sql = "insert into contracts(name,gender,email,phones,birthday,address,companyId,title,catalog) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1, c.getName());
		state.setString(2, c.getGender());
		state.setString(3, c.getEmail());
		state.setString(4, strPhones);
		state.setDate(5, birthday);
		state.setString(6, address);
		if(companyId == 0)
			state.setNull(7, Types.INTEGER);
		else
			state.setInt(7, companyId);
		state.setString(8, title);
		state.setInt(9, catalog);					
		int result = state.executeUpdate();
		if(result == 1) {			
			flag = true;
		}
		state.close();
		return flag;
	}

	@Override
	public boolean deleteById(int contractId) throws SQLException {
		boolean flag = false;		
		Connection conn = DBTool.getConnection();
		Statement state = conn.createStatement();
		String sql = "delete from contracts where id ="+contractId;
		int result = state.executeUpdate(sql);
		if(result == 1) 			
			flag = true;
		state.close();				
		return flag;
	}

	@Override
	public boolean update(Contract c) throws SQLException {
		boolean flag = false;
		String strPhones = getStringPhones(c.getPhones());
		int catalog = 0;
		String address = null;
		java.sql.Date birthday = null;		
		int companyId = 0;
		String title = null;		
		if(c.getClass() == Family.class) {
			catalog = 1;
			java.util.Date birth = ((Family)c).getBirthday();
			if(birth!=null)
				birthday =new java.sql.Date(birth.getTime());
			address = ((Family)c).getAddress();
		}
		if(c.getClass() == Partner.class) {
			catalog = 2;
			companyId = ((Partner)c).getCompany().getId();
			title = ((Partner)c).getTitle();
			}
		Connection conn = DBTool.getConnection();
		String sql = "update contracts set name=?,gender=?,phones=?,email=?,birthday=?,address=?,companyId=?,title=?,catalog=? where id=?";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1, c.getName());
		state.setString(2, c.getGender());
		state.setString(3, strPhones);
		state.setString(4, c.getEmail());
		state.setDate(5, birthday);	
		state.setString(6, address);
		if(companyId == 0)
			state.setNull(7, Types.INTEGER);
		else
			state.setInt(7, companyId);
		state.setString(8, title);
		state.setInt(9, catalog);
		state.setInt(10, c.getId());
		int result = state.executeUpdate();
		if(result == 1) {			
			flag = true;
		}
		state.close();
		return flag;
	}

	@Override
	public List<Contract> searchByName(String name) throws Exception {
		List<Contract> list = new ArrayList<Contract>();
		Connection conn = DBTool.getConnection();
		ResultSet rs;
		PreparedStatement pst = conn.prepareStatement("select contracts.*,companies.* from contracts left join companies on contracts.companyId=companies.id "
				+ "where contracts.name like ? order by convert(contracts.name using GBK)");
		pst.setString(1, "%"+name+"%");
		rs = pst.executeQuery();	
		
		while(rs.next()) {
			int id = rs.getInt(1);
			String n = rs.getString(2);
			String gender = rs.getString(3);
			String email = rs.getString(4);
			String phones = rs.getString(5);
			Date birth = rs.getDate(6);
			String addr = rs.getString(7);
			int companyId = rs.getInt(8);
			String title = rs.getString(9);
			int catalog = rs.getInt(10);
			String companyName = rs.getString(12);
			String companyAddr = rs.getString(13);
			String companyPhone = rs.getString(14);
			String companyFax = rs.getString(15);
			List<String> listPhones = new ArrayList<String>(Arrays.asList(phones.split(";")));
			if(catalog == 1) {
				Family f = new Family(id,n,gender,email,listPhones,new java.util.Date(birth.getTime()),addr);
				list.add(f);
				continue;
			}else	if(catalog == 2) {				
				Partner p = new Partner(id,n,gender,email,listPhones,title,new Company(companyId,companyName,companyAddr,companyPhone,companyFax));
					list.add(p);
					continue;
				}else {
			Contract c = new Contract(id,n,gender,email,listPhones);
			list.add(c);
				}
		}
		rs.close();
		pst.close();		
		return list;
	}

}
