package br.com.cd.ui4entity.core;

import java.util.HashMap;
import java.util.Map;

public class InputFieldData {

	private static final String ATTR_GROUP = "group";

	private String name;

	private Object value;

	public InputFieldData(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	private Map<String, Object> validations = new HashMap<String, Object>();
	private Map<String, Object> metaData = new HashMap<String, Object>();

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getGroup() {
		Object group = metaData.get(ATTR_GROUP);

		return group != null && group instanceof Map ? (Map<String, Object>) group : new HashMap<String, Object>() {
			{
				this.put("value", "default");
				this.put("label", "");
				this.put("attrs", new Object[] {});
			}
		};
	}

	public Map<String, Object> getValidations() {
		return validations;
	}

	public void setValidations(Map<String, Object> validations) {
		this.validations = validations;
	}

	public Map<String, Object> getMetaData() {
		return metaData;
	}

	public Object getMetaData(String key) {
		return metaData.get(key);
	}

	public void setMetaData(Map<String, Object> metaData) {
		this.metaData = metaData;
	}

	public void addMetaData(String key, Object value) {
		metaData.put(key, value);
	}

}
