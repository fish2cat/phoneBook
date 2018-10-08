package cn.edu.sdtbu.phoneBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PhoneBookTest {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PhoneBook book = new PhoneBook();
		book.add(new Contract("王新明", 
				new String[] {"13602344578","13506334789","010-34567913"}));
		book.add(new Family("张高山", "男","",
				new String[] {"13612344578","13507894789","0535-3456793"},sdf.parse("1980-4-25"),"山东烟台莱山区"));
		book.add(new Family("于天明", "男","qqq@163.com",
				new String[] {"13302344534","13206334789","010-34544913"},
				sdf.parse("1966-4-25"),"山东烟台莱山区"));
		book.add(new Partner("张流星", "男","www@163.com",
				new String[] {"13612344234","13507894555","0531-3456993"},
				"经理", new Company("伪腾讯QQ","中国北京","010-12345678","010-23456789")));
		book.add(new Contract("Tommy", 
				new String[] {"13612344234","13507894555","0531-3456993"}));
		book.add(new Contract("Jackson", 
				new String[] {"13612344234","13507894555","0531-3456993"}));
		book.display();
		for(Contract c:  book.findNameContains("张"))
			c.display();
		book.delete(new Contract("张流星", 
				new String[] {"13612344234","13507894555","0531-3456993"}));
		book.display();
		book.updateContract("王新明", "男","123@163.com",
				new String[] {"13602344578","13506334789","010-34567913"});
		book.display();
	}

}
