package cn.edu.sdtbu.phoneBook.gui.customStyle;

import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import cn.edu.sdtbu.phoneBook.bean.Contract;

public class ListFont extends JList<Contract> {
	public ListFont(DefaultListModel<Contract> listModel,Font f){
		super(listModel);
		this.setFont(f);
	}
}
