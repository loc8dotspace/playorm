package com.alvazan.orm.layer2.nosql;

import java.util.HashMap;
import java.util.Map;

import com.alvazan.orm.layer3.spi.Column;

public class Row {
	private byte[] key;
	private Map<String, Column> columns = new HashMap<String, Column>();
	public byte[] getKey() {
		return key;
	}
	public void setKey(byte[] key) {
		this.key = key;
	}
	public Map<String, Column> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

}
