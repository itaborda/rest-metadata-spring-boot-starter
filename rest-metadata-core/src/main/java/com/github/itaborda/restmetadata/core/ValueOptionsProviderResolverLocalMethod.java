package com.github.itaborda.restmetadata.core;

import java.lang.reflect.Method;

import com.github.itaborda.restmetadata.OptionsProvider;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

public class ValueOptionsProviderResolverLocalMethod implements ValueOptionsProviderResolver {

	@Override
	public boolean canResolve(ProviderTypes providerType) {
		return providerType.equals(ProviderTypes.LOCAL_METHOD);
	}

	@Override
	public Object resolve(Object target, OptionsProvider optionsProvider) throws Exception {

		Method method = DefaultParserEngine.getMethodNameAnnotatedWithValueOptionsOrDefault(target.getClass(), optionsProvider.id());

		return method.invoke(target);
	}
}