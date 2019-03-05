package com.github.itaborda.restmetadata;

import java.util.Map;

public interface ValueOptionsProvider {

	public static final String METHOD_NAME = "getValueOptions";

	Map<String, Object> getValueOptions();
}
