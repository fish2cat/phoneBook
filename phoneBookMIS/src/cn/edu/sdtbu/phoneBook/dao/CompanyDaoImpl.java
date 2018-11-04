package cn.edu.sdtbu.phoneBook.dao;

import java.sql.*;
import java.util.*;
import cn.edu.sdtbu.phoneBook.bean.*;
import cn.edu.sdtbu.phoneBook.util.DBTool;

public class CompanyDaoImpl implements CompanyDao {

	@Override
	public int add(Company c) throws Exception {
		int key = -1;
		Connection conn = DBTool.getConnection();
		Statement state = conn.createStatement();
		String sql = "insert into companies(name,address,phone,fax) values ('"+c.getName()+"','"
		+c.getAddress()+ "','"+c.getPhone()+"','"+c.getFax()+"')";
		int result = state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		if(result == 1) {			
			ResultSet keys = state.getGeneratedKeys();
			keys.next();
			key = keys.getInt(1);			 
		}
		state.close();
		return key;
	}

	

	@Override
	public boolean update(Company c) throws SQLException {
		boolean flag = false;
		Connection conn = DBTool.getConnection();
		String sql = "update companies set name=?,address=?,phone=?,fax=? where id=?";
		PreparedStatement state = conn.prepareStatement(sql);
		state.setString(1, c.getName());
		state.setString(2, c.getAddress());
		state.setString(3, c.getPhone());
		state.setString(4, c.getFax());
		state.setInt(5, c.getId());		
		int result = state.executeUpdate();
		if(result == 1) {
			
			flag = true;
		}
		state.close();
		return flag;
	}

	@Override
	public Vector<Company> searchAll() throws Exception {
		Vector<Company> result = new Vector<Company>();
		Connection conn = DBTool.getConnection();
		ResultSet rs;
		Statement st = conn.createStatement();
		rs = st.executeQuery("select * from companies");			
		while(rs.next()) {
			result.add(new Company(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
		}
		rs.close();
		st.close();
		return result;
	}

	/*@Override
	public Company searchById(int id) throws Exception {
		Company result = null;
		Connection conn = DBTool.getConnection();
		ResultSet rs;
		PreparedStatement pst = conn.prepareStatement("select * from companies where id=?");
		pst.setInt(1, id);
		rs = pst.executeQuery();		
		if(rs.next()) {
			result = new Company(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
		}
		rs.close();
		pst.close();		
		return result;
	}*/
}
