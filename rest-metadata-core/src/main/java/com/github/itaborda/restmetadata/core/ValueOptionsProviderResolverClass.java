package com.github.itaborda.restmetadata.core;

import java.lang.reflect.Method;

import com.github.itaborda.restmetadata.OptionsProvider;
import com.github.itaborda.restmetadata.ValueOptionsProvider;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

public class ValueOptionsProviderResolverClass implements ValueOptionsProviderResolver {

	@Override
	public boolean canResolve(ProviderTypes providerType) {
		return providerType.equals(ProviderTypes.CLASS);
	}

	@Override
	public Object resolve(Object target, OptionsProvider optionsProvider) throws Exception {

		Object bean = optionsProvider.impl().newInstance();

		Method method;
		if (bean instanceof ValueOptionsProvider)
			method = bean.getClass().getMethod(ValueOptionsProvider.METHOD_NAME);
		else
			method = DefaultParserEngine.getMethodNameAnnotatedWithValueOptionsOrDefault(bean.getClass(), optionsProvider.id());

		return method.invoke(bean);
	}
}