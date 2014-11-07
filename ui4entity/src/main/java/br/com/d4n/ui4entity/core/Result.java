package br.com.d4n.ui4entity.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

	private final Map<String, List<InputFieldData>> fields = new HashMap<String, List<InputFieldData>>();
	private final Map<String, Map<String, Object>> groups = new HashMap<String, Map<String, Object>>();
	private final Object target;

	public Result(Object target) {
		this.target = target;
	}

	public Map<String, Map<String, Object>> getGroups() {
		return groups;
	}

	public Collection<Map.Entry<String, List<InputFieldData>>> getGroupFields() {

		return Collections.unmodifiableCollection(fields.entrySet());
	}

	public Object getTarget() {
		return target;
	}

	public void addField(InputFieldData inputFieldData) {
		String groupName = (String) inputFieldData.getGroup().get("value");
		Map<String, Object> groupList = this.groups.get(groupName);
		if (groupList == null) {
			groupList = new HashMap<String, Object>(inputFieldData.getGroup());
			this.groups.put(groupName, groupList);
		} else {
			for (Map.Entry<String, Object> entry : inputFieldData.getGroup().entrySet()) {
				if (entry.getValue() != null && !entry.getValue().toString().isEmpty()) {
					Object groupValue = groupList.get(entry.getKey());
					if (groupValue == null)
						groupList.put(entry.getKey(), entry.getValue());
					else if (groupValue instanceof List && entry.getValue() instanceof List) {
						((List) groupValue).addAll((Collection) entry.getValue());
					}
				}
			}
		}

		List<InputFieldData> inputFieldDataList = this.fields.get(groupName);
		if (inputFieldDataList == null) {
			inputFieldDataList = new ArrayList<InputFieldData>();
			this.fields.put(groupName, inputFieldDataList);
		}
		inputFieldDataList.add(inputFieldData);
	}
}
