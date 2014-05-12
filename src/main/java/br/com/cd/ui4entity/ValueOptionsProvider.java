package br.com.cd.ui4entity;

import java.util.Map;

public interface ValueOptionsProvider {

	public static final String METHOD_NAME = "getValueOptions";

	Map<String, Object> getValueOptions();
}
