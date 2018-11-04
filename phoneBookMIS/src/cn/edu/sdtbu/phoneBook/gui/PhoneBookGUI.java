package cn.edu.sdtbu.phoneBook.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdtbu.phoneBook.bean.*;

import cn.edu.sdtbu.phoneBook.dao.*;

import cn.edu.sdtbu.phoneBook.gui.customStyle.*;

import cn.edu.sdtbu.phoneBook.service.*;
import cn.edu.sdtbu.phoneBook.util.DBTool;

public class PhoneBookGUI extends JFrame {	
	private Contract currentContract;
	private ListFont phoneList;
	private DefaultListModel<Contract> listModel;

	public PhoneBookGUI(){
		super("通讯录");
		ContractDao contractDao = new ContractDaoImpl();
		CompanyDao companyDao = new CompanyDaoImpl();		
		ContractService conService = new ContractServImpl(contractDao,companyDao);
		
		Container c = this.getContentPane();
		JPanel top = new JPanel();
		Font f = new Font(StyleArgument.FONTNAME, StyleArgument.FONTSTYLE, StyleArgument.FONTSIZE);
		TextFieldFont tfdSearch = new TextFieldFont("搜索", 20, f);
		ButtonFont btnAdd = new ButtonFont("+", f);
		ButtonFont btnDel = new ButtonFont("-", f);
		top.add(tfdSearch);
		top.add(btnAdd);
		top.add(btnDel);		
		listModel = new DefaultListModel<Contract>();
		try {
			freshListModel(conService.searchByName(""));
		} catch (Exception e1) {			
			e1.printStackTrace();
		}		
		phoneList = new ListFont(listModel, f);
		c.add(top, BorderLayout.NORTH);
		c.add(new JScrollPane(phoneList));
		this.pack();
		this.addWindowListener(new WindowAdapter() {	
			

			@Override
			public void windowClosing(WindowEvent e) {
				DBTool.closeConnection();
				
			}

			
			
		});
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tfdSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Contract> founded = null;
				try {
					founded = conService.searchByName(tfdSearch.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				freshListModel(founded);
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				PhoneBookGUI.this.setCurrentContract(null);
				new ContractDetailGUI(PhoneBookGUI.this);
				Contract c = getCurrentContract();
				if (c != null) {										
					try {
						conService.addContract(c);
						PhoneBookGUI.this.freshListModel(conService.searchByName(tfdSearch.getText().equals("搜索")?"":tfdSearch.getText()));
					} catch (Exception e1) {						
						e1.printStackTrace();
					}
				}
			}

		});
		
		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Contract delContract = getCurrentContract();
				Contract delContract = phoneList.getSelectedValue();
				if (null == delContract) {
					JOptionPane.showMessageDialog(PhoneBookGUI.this, "请选中待删除联系人", "删除",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(PhoneBookGUI.this, "确认删除联系人吗?")) {

					try {
						conService.deleteContractById(delContract.getId());
						PhoneBookGUI.this.freshListModel(conService.searchByName(tfdSearch.getText().equals("搜索")?"":tfdSearch.getText()));
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
					//listModel.removeElement(delContract);						
				}
			}
		});		
		
		phoneList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					PhoneBookGUI.this.setCurrentContract(phoneList.getSelectedValue());
					//int index = phoneList.getSelectedIndex();
					new ContractDetailGUI(PhoneBookGUI.this);
					Contract updatedContract = getCurrentContract();
					try {
						conService.updateContract(updatedContract);
						PhoneBookGUI.this.freshListModel(conService.searchByName(tfdSearch.getText().equals("搜索")?"":tfdSearch.getText()));
					} catch (Exception e) {						
						e.printStackTrace();
					}
					//listModel.set(index, updatedContract);
				}
			}

		});
		
	}

	public static void main(String[] args) {
		new PhoneBookGUI();

	}

	public Contract getCurrentContract() {
		return currentContract;
	}

	public void setCurrentContract(Contract c) {
		currentContract = c;
	}

	private void freshListModel(List<Contract> contracts) {
		listModel.removeAllElements();
		for (Contract p : contracts) {
			listModel.addElement(p);
		}
	}

}
