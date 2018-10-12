package cn.edu.sdtbu.phoneBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PhoneBookTest {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PhoneBook book = new PhoneBook();
		List<String> list1 = new ArrayList<String>();
		list1.add("13602344578");
		list1.add("13506334789");
		list1.add("010-34567913");
		book.add(new Contract("������",list1));
		List<String> list2 = new ArrayList<String>();
		list2.add("13612344578");
		list2.add("13507894789");
		list2.add("0535-3456793");
		book.add(new Family("�Ÿ�ɽ", "��","",list2,sdf.parse("1980-4-25"),"ɽ����̨��ɽ��"));
		List<String> list3 = new ArrayList<String>();
		list3.add("13602344578");
		list3.add("13206334789");
		list3.add("0535-34544913");
		book.add(new Family("������", "��","qqq@163.com",list3,sdf.parse("1966-4-25"),"ɽ����̨��ɽ��"));
		List<String> list4 = new ArrayList<String>();
		list4.add("13612344234");
		list4.add("13507894555");
		list4.add("0531-3456993");
		book.add(new Partner("������", "��","www@163.com",list4,
				"����", new Company("α��ѶQQ","�й�����","010-12345678","010-23456789")));
		List<String> list5 = new ArrayList<String>();
		list5.add("13612344234");
		list5.add("13507894555");
		list5.add("0531-3456993");
		book.add(new Contract("Tommy", list5));
		List<String> list6 = new ArrayList<String>();
		list6.add("13612344234");
		list6.add("13507894555");
		list6.add("0531-3456993");
		book.add(new Contract("Jackson", list6));
		book.display();
		for(Contract c:  book.findNameContains("��"))
			c.display();
		List<String> list7 = new ArrayList<String>();
		list7.add("13612344234");
		list7.add("13507894555");
		list7.add("0531-3456993");
		if(book.delete(new Partner("������", "��","www@163.com",list7,
				"����", new Company("α��ѶQQ","�й�����","010-12345678","010-23456789"))))
			System.out.println("del success.");
		else
			System.out.println("del fail.");
		book.display();
		List<String> list8 = new ArrayList<String>();
		list8.add("13602344578");
		list8.add("13506334789");
		list8.add("010-34567913");
		book.updateContract("������", "��","123@163.com",list8);
		book.display();
	}

}
