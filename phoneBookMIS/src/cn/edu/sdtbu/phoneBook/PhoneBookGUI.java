package cn.edu.sdtbu.phoneBook;

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

import cn.edu.sdtbu.phoneBook.customStyle.ButtonFont;
import cn.edu.sdtbu.phoneBook.customStyle.ListFont;
import cn.edu.sdtbu.phoneBook.customStyle.StyleArgument;
import cn.edu.sdtbu.phoneBook.customStyle.TextFieldFont;

public class PhoneBookGUI extends JFrame {
	private PhoneBook phoneData;
	private Contract currentContract;
	private DefaultListModel<Contract> listModel;

	{
		Vector<Contract> c = new Vector<Contract>();
		List<String> list1 = new ArrayList<String>();
		list1.add("13602344578");
		list1.add("13506334789");
		list1.add("010-34567913");
		c.add(new Contract("王新明",list1));
		List<String> list2 = new ArrayList<String>();
		list2.add("13612344578");
		list2.add("13507894789");
		list2.add("0535-3456793");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.add(new Family("张高山", "男","",list2,sdf.parse("1980-4-25"),"山东烟台莱山区"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> list3 = new ArrayList<String>();
		list3.add("13602344578");
		list3.add("13206334789");
		list3.add("0535-34544913");
		try {
			c.add(new Family("王新", "男","qqq@163.com",list3,sdf.parse("1966-4-25"),"山东烟台莱山区"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> list4 = new ArrayList<String>();
		list4.add("13612344234");
		list4.add("13507894555");
		list4.add("0531-3456993");
		c.add(new Partner("张流星", "男","www@163.com",list4,
				"经理", new Company("伪腾讯QQ","中国北京","010-12345678","010-23456789")));
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
		phoneData = new PhoneBook(c);
	}
	public PhoneBookGUI() {
		super("通讯录");
		Container c = this.getContentPane();
		JPanel top = new JPanel();
		Font f = new Font(StyleArgument.FONTNAME,StyleArgument.FONTSTYLE,StyleArgument.FONTSIZE);
		TextFieldFont tfdSearch = new TextFieldFont("搜索",20,f);
		tfdSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tfdSearch.getText().equals("")) {
					List<Contract> founded = phoneData.findNameContains(tfdSearch.getText());
					freshListModel(founded);
					}else
						freshListModel(phoneData.getContracts());
			}
		});
		ButtonFont btnAdd = new ButtonFont("+",f);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//PhoneBookGUI.this.setCurrentContract(phoneData.getContracts().get(3));
				PhoneBookGUI.this.setCurrentContract(null);
				new ContractDetailGUI(PhoneBookGUI.this);
				phoneData.add(getCurrentContract());		
				listModel.addElement(getCurrentContract());
			}
			
		});
		ButtonFont btnDel = new ButtonFont("-",f);
		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Contract delContract = getCurrentContract();
				if(null==delContract) {
					JOptionPane.showMessageDialog(PhoneBookGUI.this, "请选中待删除联系人", "删除", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				phoneData.delete(delContract);
				listModel.removeElement(delContract);
				setCurrentContract(null);
			}
			
		});
		top.add(tfdSearch);
		top.add(btnAdd);
		top.add(btnDel);
		listModel = new DefaultListModel<Contract>();
		freshListModel(phoneData.getContracts());		
		ListFont phoneList = new ListFont(listModel,f);
		phoneList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				//选中的进行操作
				if(phoneList.getValueIsAdjusting())
					setCurrentContract(phoneList.getSelectedValue());				
			}			
		});
		phoneList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2) {
					int index = phoneList.getSelectedIndex();
					new ContractDetailGUI(PhoneBookGUI.this);
					Contract updatedContract = getCurrentContract();
				try {
					phoneData.updateContract(updatedContract);
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
