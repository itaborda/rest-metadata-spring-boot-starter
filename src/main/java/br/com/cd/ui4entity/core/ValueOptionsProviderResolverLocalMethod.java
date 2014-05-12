package br.com.cd.ui4entity.core;

import java.lang.reflect.Method;

import br.com.cd.ui4entity.OptionsProvider;
import br.com.cd.ui4entity.OptionsProvider.ProviderTypes;

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