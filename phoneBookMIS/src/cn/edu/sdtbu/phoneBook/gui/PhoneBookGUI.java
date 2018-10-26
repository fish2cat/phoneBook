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

import cn.edu.sdtbu.phoneBook.bean.Company;
import cn.edu.sdtbu.phoneBook.bean.Contract;
import cn.edu.sdtbu.phoneBook.bean.Family;
import cn.edu.sdtbu.phoneBook.bean.Partner;
import cn.edu.sdtbu.phoneBook.gui.customStyle.ButtonFont;
import cn.edu.sdtbu.phoneBook.gui.customStyle.ListFont;
import cn.edu.sdtbu.phoneBook.gui.customStyle.StyleArgument;
import cn.edu.sdtbu.phoneBook.gui.customStyle.TextFieldFont;
import cn.edu.sdtbu.phoneBook.service.PhoneBook;

public class PhoneBookGUI extends JFrame {
	private PhoneBook phoneService;
	private Contract currentContract;
	private ListFont phoneList;
	private DefaultListModel<Contract> listModel;
	{
		Vector<Contract> c = new Vector<Contract>();
		List<String> list1 = new ArrayList<String>();
		list1.add("13602344578");
		list1.add("13506334789");
		list1.add("010-34567913");
		c.add(new Contract("������",list1));
		List<String> list2 = new ArrayList<String>();
		list2.add("13612344578");
		list2.add("13507894789");
		list2.add("0535-3456793");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.add(new Family("�Ÿ�ɽ", "��","",list2,sdf.parse("1980-4-25"),"ɽ����̨��ɽ��"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> list3 = new ArrayList<String>();
		list3.add("13602344578");
		list3.add("13206334789");
		list3.add("0535-34544913");
		try {
			c.add(new Family("����", "��","qqq@163.com",list3,sdf.parse("1966-4-25"),"ɽ����̨��ɽ��"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> list4 = new ArrayList<String>();
		list4.add("13612344234");
		list4.add("13507894555");
		list4.add("0531-3456993");
		c.add(new Partner("������", "��","www@163.com",list4,
				"����", new Company("α��ѶQQ","�й�����","010-12345678","010-23456789")));
		List<String> list5 = new ArrayList<String>();
		list5.add("13612344234");
		list5.add("13507894555");
		list5.add("0531-3456993");
		c.add(new Contract("Tommy", list5));
		List<String> list6 = new ArrayList<String>();
		list6.add("13612344234");
		list6.add("13507894555");
		list6.add("0531-3456993");
		c.add(new Contract("Jackson", list6));
		List<String> list7 = new ArrayList<String>();
		list7.add("13602344578");
		list7.add("13506334789");
		list7.add("010-34567913");
		c.add(new Contract("����",list7));
		List<String> list8 = new ArrayList<String>();
		list8.add("13602344578");		
		c.add(new Contract("������",list8));
		List<String> list9 = new ArrayList<String>();
		list9.add("13602344578");		
		c.add(new Contract("�μѳ�",list9));
		List<String> list10 = new ArrayList<String>();
		list10.add("13602344578");		
		c.add(new Contract("������",list10));
		phoneService = new PhoneBook(c);
	}
	public PhoneBookGUI() {
		super("ͨѶ¼");
		Container c = this.getContentPane();
		JPanel top = new JPanel();
		Font f = new Font(StyleArgument.FONTNAME,StyleArgument.FONTSTYLE,StyleArgument.FONTSIZE);
		TextFieldFont tfdSearch = new TextFieldFont("����",20,f);
		tfdSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tfdSearch.getText().equals("")) {
					List<Contract> founded = phoneService.findNameContains(tfdSearch.getText());
					freshListModel(founded);
					}else
						freshListModel(phoneService.getContracts());
			}
		});
		ButtonFont btnAdd = new ButtonFont("+",f);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//PhoneBookGUI.this.setCurrentContract(phoneData.getContracts().get(3));
				PhoneBookGUI.this.setCurrentContract(null);
				new ContractDetailGUI(PhoneBookGUI.this);
				Contract c = getCurrentContract();
				if(c != null) {
				phoneService.add(c);
				//Ҫ�����������򣬲���ֱ��listModel.addElement()����
				PhoneBookGUI.this.freshListModel(phoneService.getContracts());
				}
			}
			
		});
		ButtonFont btnDel = new ButtonFont("-",f);
		btnDel.addActionListener(new ActionListener() {			

			@Override
			public void actionPerformed(ActionEvent e) {
				//Contract delContract = getCurrentContract();
				Contract delContract = phoneList.getSelectedValue();
				if(null==delContract) {
					JOptionPane.showMessageDialog(PhoneBookGUI.this, "��ѡ�д�ɾ����ϵ��", "ɾ��", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(PhoneBookGUI.this, "ȷ��ɾ����ϵ����?")) {
				
				phoneService.delete(delContract);
				listModel.removeElement(delContract);
				//setCurrentContract(null);
				}
			}			
		});
		top.add(tfdSearch);
		top.add(btnAdd);
		top.add(btnDel);
		listModel = new DefaultListModel<Contract>();
		freshListModel(phoneService.getContracts());		
		phoneList = new ListFont(listModel,f);		
		phoneList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2) {
					int index = phoneList.getSelectedIndex();
					new ContractDetailGUI(PhoneBookGUI.this);
					Contract updatedContract = getCurrentContract();
				try {
					phoneService.updateContract(updatedContract);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				listModel.set(index, updatedContract);
				}				
			}		
			
		});
		c.add(top, BorderLayout.NORTH);
		c.add(new JScrollPane(phoneList));
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
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
		for(Contract p :contracts) {
			listModel.addElement(p);
		}
	}	
	
}
