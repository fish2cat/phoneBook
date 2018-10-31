package cn.edu.sdtbu.phoneBook.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import cn.edu.sdtbu.phoneBook.bean.*;
import cn.edu.sdtbu.phoneBook.dao.*;
import cn.edu.sdtbu.phoneBook.gui.customStyle.*;
import cn.edu.sdtbu.phoneBook.service.*;

public class ContractDetailGUI extends JDialog {
	private PhoneBookGUI owner;
	private TextFieldFont tfdName;
	private TextFieldFont tfdGender;
	private TextFieldFont tfdEmail;
	private java.util.List<TextFieldFont> tfdPhones;
	private Contract contract;
	private ButtonFont btnAdd;
	private CheckBoxFont chkFamily;
	private TextFieldFont tfdAddr;
	private TextFieldFont tfdBirth;
	private CheckBoxFont chkPartner;
	private TextFieldFont title;
	private ComboBoxFont companyName;
	private Vector<Company> companies;
	private TextFieldFont companyAddr;
	private TextFieldFont companyPhone;
	private TextFieldFont companyFax;
	private Font f;
	private Box palPhone;
	private int contractId;
	private int companyId;
	private String strCompanyName;

	public ContractDetailGUI(PhoneBookGUI father) {
		super(father, "", true);
		this.owner = father;
		this.contract = father.getCurrentContract();
		initComponent();
		Box base = new Box(BoxLayout.Y_AXIS);
		TitledBorder border = BorderFactory.createTitledBorder("基本信息");
		border.setTitleFont(f);
		base.setBorder(border);
		base.add(tfdName);
		base.add(tfdGender);
		base.add(tfdEmail);
		base.add(btnAdd);
		base.add(palPhone);

		Box family = new Box(BoxLayout.Y_AXIS);
		family.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		family.add(chkFamily);
		family.add(new LabelFont("地址", f));
		family.add(tfdAddr);
		family.add(new LabelFont("出生年月日", f));
		family.add(tfdBirth);

		Box partner = new Box(BoxLayout.Y_AXIS);
		partner.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		partner.add(chkPartner);
		partner.add(new LabelFont("职务/职称", f));
		partner.add(title);
		partner.add(new LabelFont("所在公司信息：", f));
		partner.add(new LabelFont("名称", f));
		partner.add(companyName);
		partner.add(new LabelFont("地址", f));
		partner.add(companyAddr);
		partner.add(new LabelFont("电话", f));
		partner.add(companyPhone);
		partner.add(new LabelFont("传真", f));
		partner.add(companyFax);
		JPanel palCmd = new JPanel();
		palCmd.setBorder(BorderFactory.createRaisedBevelBorder());
		ButtonFont btnSave = new ButtonFont("保存", f);
		ButtonFont btnClear = new ButtonFont("清空", f);
		ButtonFont btnRtn = new ButtonFont("返回", f);
		palCmd.add(btnSave);
		palCmd.add(btnClear);
		palCmd.add(btnRtn);
		Container c = this.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		c.add(base);
		c.add(family);
		c.add(partner);
		c.add(palCmd);
		

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				java.util.List<String> phones = new ArrayList<String>();
				for (TextFieldFont p : tfdPhones) {
					if (!p.getText().equals(""))
						phones.add(p.getText());
				}
				if (chkFamily.isSelected()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date birthday = null;
					if (tfdBirth.getText() != null && !tfdBirth.getText().equals("")) {
						try {
							birthday = sdf.parse(tfdBirth.getText());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					Family f = new Family(contractId, tfdName.getText(), tfdGender.getText(), tfdEmail.getText(),
							phones, birthday, tfdAddr.getText());
					ContractDetailGUI.this.owner.setCurrentContract(f);
					return;
				}
				if (chkPartner.isSelected()) {
					Partner p = new Partner(contractId, tfdName.getText(), tfdGender.getText(), tfdEmail.getText(),
							phones, title.getText(), new Company(companyId, strCompanyName, companyAddr.getText(),
									companyPhone.getText(), companyFax.getText()));
					ContractDetailGUI.this.owner.setCurrentContract(p);
					return;
				}
				Contract c = new Contract(contractId, tfdName.getText(), tfdGender.getText(), tfdEmail.getText(),
						phones);
				ContractDetailGUI.this.owner.setCurrentContract(c);
				return;
			}
		});

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contractId = 0;
				companyId = 0;
				tfdName.setText("");
				tfdGender.setText("");
				tfdEmail.setText("");
				tfdPhones.clear();
				tfdPhones.add(new TextFieldFont(12, f));
				palPhone.removeAll();
				palPhone.add(tfdPhones.get(0));
				ContractDetailGUI.this.validate();
				ContractDetailGUI.this.repaint();
				chkFamily.setSelected(false);
				chkPartner.setSelected(false);
				initCompany();
				strCompanyName = null;
			}
		});

		btnRtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(ContractDetailGUI.this, "确认返回操作吗？");
				if (JOptionPane.YES_OPTION == choice)
					ContractDetailGUI.this.dispose();
			}
		});
		companyName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("comboBoxEdited")) {
					// 输入新的
					strCompanyName = (String) companyName.getEditor().getItem();
					companyAddr.setText("");
					companyPhone.setText("");
					companyFax.setText("");
				}
				if (e.getActionCommand().equals("comboBoxChanged")) {
					if (companyName.getSelectedIndex() != -1) {
						// 选中
						Company c = (Company) companyName.getSelectedItem();
						strCompanyName = c.getName();
						companyAddr.setText(c.getAddress());
						companyPhone.setText(c.getPhone());
						companyFax.setText(c.getFax());
					}
				}
			}

		});
		this.setSize(300, 850);		
		this.setVisible(true);
	}
	public void setFamilyEnable(boolean isEnable) {
		tfdAddr.setEnabled(isEnable);
		tfdBirth.setEnabled(isEnable);
	}

	public void setPartnerEnable(boolean isEnable) {
		title.setEnabled(isEnable);
		companyName.setEnabled(isEnable);
		companyAddr.setEnabled(isEnable);
		companyPhone.setEnabled(isEnable);
		companyFax.setEnabled(isEnable);
	}
//初始化公司名称等公司信息，公司可编辑
	public void initCompany() {
		CompanyDao dao = new CompanyDaoImpl();
		CompanyService service = new CompanyServImpl(dao);
		companies = new Vector<Company>();
		companies.add(new Company(0, "请选择内容", "", "", ""));
		try {
			companies.addAll(service.searchAll());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (companyName == null) {
			companyName = new ComboBoxFont(companies, f);
		} else {
			companyName.removeAllItems();
			for (Company p : companies) {
				companyName.addItem(p);
			}
		}
		companyName.setEditable(true);
		if (companyPhone == null)
			companyPhone = new TextFieldFont(companies.get(0).getPhone(), f);
		else
			companyPhone.setText(companies.get(0).getPhone());
		if (companyAddr == null)
			companyAddr = new TextFieldFont(companies.get(0).getAddress(), f);
		else
			companyAddr.setText(companies.get(0).getAddress());
		if (companyFax == null)
			companyFax = new TextFieldFont(companies.get(0).getAddress(), f);
		else
			companyFax.setText(companies.get(0).getAddress());
	}
//创建各组件，根据通讯录窗口传递的当前联系人更新组件当前值
	public void initComponent() {
		f = new Font(StyleArgument.FONTNAME, StyleArgument.FONTSTYLE, StyleArgument.FONTSIZE);
		btnAdd = new ButtonFont("添加电话", f);
		tfdName = new TextFieldFont("姓名", 10, f);
		tfdGender = new TextFieldFont("性别", 10, f);
		tfdEmail = new TextFieldFont("电子邮件", 15, f);
		tfdPhones = new ArrayList<TextFieldFont>();
		chkFamily = new CheckBoxFont("家人", false, f);
		chkPartner = new CheckBoxFont("工作伙伴", false, f);
		tfdAddr = new TextFieldFont(20, f);
		tfdBirth = new TextFieldFont(10, f);
		setFamilyEnable(false);
		title = new TextFieldFont(5, f);
		initCompany();
		setPartnerEnable(false);
		if (contract != null) {
			contractId = contract.getId();
			tfdName.setText(contract.getName());
			if (contract.getGender() != null || !contract.getGender().equals(""))
				tfdGender.setText(contract.getGender());
			if (contract.getEmail() != null || !contract.getEmail().equals(""))
				tfdEmail.setText(contract.getEmail());
			for (String p : contract.getPhones())
				tfdPhones.add(new TextFieldFont(p, 12, f));
			if (contract instanceof Family) {
				chkFamily.setSelected(true);
				if (((Family) contract).getAddress() != null && !((Family) contract).getAddress().equals(""))
					tfdAddr.setText(((Family) contract).getAddress());
				if (((Family) contract).getBirthday() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					tfdBirth.setText(sdf.format(((Family) contract).getBirthday()));
				}
				setFamilyEnable(true);
			}
			if (contract instanceof Partner) {
				strCompanyName = ((Partner) contract).getCompany().getName();
				companyId = ((Partner) contract).getCompany().getId();
				chkPartner.setSelected(true);
				if (((Partner) contract).getTitle() != null && !((Partner) contract).getTitle().equals(""))
					title.setText(((Partner) contract).getTitle());
				if (((Partner) contract).getCompany().getName() != null
						&& !((Partner) contract).getCompany().getName().equals(""))
					companyName.setSelectedItem(((Partner) contract).getCompany());
				if (((Partner) contract).getCompany().getPhone() != null
						&& !((Partner) contract).getCompany().getPhone().equals(""))
					companyPhone.setText(((Partner) contract).getCompany().getPhone());
				if (((Partner) contract).getCompany().getAddress() != null
						&& !((Partner) contract).getCompany().getAddress().equals(""))
					companyAddr.setText(((Partner) contract).getCompany().getAddress());
				if (((Partner) contract).getCompany().getFax() != null
						&& !((Partner) contract).getCompany().getFax().equals(""))
					companyFax.setText(((Partner) contract).getCompany().getFax());
				setPartnerEnable(true);
			}
		} else
			tfdPhones.add(new TextFieldFont(12, f));
		palPhone = new Box(BoxLayout.Y_AXIS);
		for (TextFieldFont phone : tfdPhones)
			palPhone.add(phone);
		chkFamily.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chkFamily.isSelected()) {
					companyId = 0;
					chkPartner.setSelected(false);
					setFamilyEnable(true);
				} else {
					tfdAddr.setText("");
					tfdBirth.setText("");
					setFamilyEnable(false);
				}
			}
		});

		chkPartner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (chkPartner.isSelected()) {
					chkFamily.setSelected(false);
					setPartnerEnable(true);
				} else {
					title.setText("");
					initCompany();
					setPartnerEnable(false);
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TextFieldFont newPhone = new TextFieldFont(12, f);
				tfdPhones.add(newPhone);
				palPhone.add(newPhone);
				ContractDetailGUI.this.validate();
				ContractDetailGUI.this.repaint();
			}
		});
	}
}
