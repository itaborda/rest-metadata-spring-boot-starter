package br.com.cd.ui4entity.core;

import java.lang.reflect.Method;

import br.com.cd.ui4entity.OptionsProvider;
import br.com.cd.ui4entity.ValueOptionsProvider;
import br.com.cd.ui4entity.OptionsProvider.ProviderTypes;

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