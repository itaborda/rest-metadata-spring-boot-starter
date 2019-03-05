package com.github.itaborda.restmetadata.core;

import com.github.itaborda.restmetadata.OptionsProvider;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

public interface ValueOptionsProviderResolver {

	public static final String SELECT_ATTR_NAME = "select";
	public static final String OPTIONS_ATTR_NAME = "options";

	boolean canResolve(ProviderTypes providerType);

	Object resolve(Object target, OptionsProvider inputOptions) throws Exception;

}