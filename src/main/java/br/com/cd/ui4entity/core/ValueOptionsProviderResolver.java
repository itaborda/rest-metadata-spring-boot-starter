package br.com.cd.ui4entity.core;

import br.com.cd.ui4entity.OptionsProvider;
import br.com.cd.ui4entity.OptionsProvider.ProviderTypes;

public interface ValueOptionsProviderResolver {

	public static final String SELECT_ATTR_NAME = "select";
	public static final String OPTIONS_ATTR_NAME = "options";

	boolean canResolve(ProviderTypes providerType);

	Object resolve(Object target, OptionsProvider inputOptions) throws Exception;

}