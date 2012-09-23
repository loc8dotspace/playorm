package com.alvazan.orm.api.base;

import java.util.HashMap;
import java.util.Map;

public enum DbTypeEnum {
	CASSANDRA("cassandra"), IN_MEMORY("inmemory"); 

	private static final Map<String, DbTypeEnum> lookup = new HashMap<String, DbTypeEnum>();
	
	//HADOOP
	static {
		for(DbTypeEnum type : DbTypeEnum.values()) {
			lookup.put(type.getValue(), type);
		}
	}
	
	private String value;
	
	private DbTypeEnum(String val) {
		this.value = val;
	}

	private String getValue() {
		return value;
	}
	
	public static DbTypeEnum lookup(String val) {
		return lookup.get(val);
	}
	
}
