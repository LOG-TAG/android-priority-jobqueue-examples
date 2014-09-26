package com.apjq.todolist.events;

public class SyncDataPojoSaved {
	
	public SyncDataPojoSaved(String str) {
		
		this.str = str;
	}

	String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
