package test;

import java.util.HashMap;
import java.util.Set;

public class mySet {
    HashMap map;
	private static Object PRESENT = new Object();
	
	public mySet(){
		map = new HashMap();
	}
	
	public void add(Object obj){
		map.put(obj, PRESENT);//set的不可重复就是利用了map里面键对象的不可重复
	}
	
	public int size(){
		return map.size();
	}
	
	public void remove(Object obj){
		map.remove(obj);
	}
	
	public Object get(int index) {
		Set key = map.keySet();
		return key.toArray()[index];
	}
	public static void main(String[] args){
		mySet set = new mySet();
		set.add("aaa");
		set.add("bbb");

		set.add("aaa");
//		set.remove("aaa");
//		System.out.println(set.size());
		for(int i=0;i<set.size();i++) {
			System.out.println((String)set.get(i));
		}

	}
}