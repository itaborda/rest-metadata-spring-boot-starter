package com.github.itaborda.restmetadata.client.model;

public class DoubleValue {

	private Integer id;
	private String group;
	private String value;

	public DoubleValue(Integer id, String group, String value) {
		this.id = id;
		this.group = group;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}