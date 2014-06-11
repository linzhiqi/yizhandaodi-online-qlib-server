package util;

import java.util.Iterator;
import java.util.List;

public class ListToString {

	public static String listtoString( List<String> list){
		StringBuilder builder = new StringBuilder();
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			builder.append(it.next());
			if(it.hasNext()){
				builder.append(',');
			}
		}
		return builder.toString();
	}
}
