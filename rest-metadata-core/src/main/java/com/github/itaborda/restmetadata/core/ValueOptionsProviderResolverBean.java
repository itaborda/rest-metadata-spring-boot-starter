package com.github.itaborda.restmetadata.core;

import java.lang.reflect.Method;

import com.github.itaborda.restmetadata.OptionsProvider;
import com.github.itaborda.restmetadata.ValueOptionsProvider;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

public class ValueOptionsProviderResolverBean implements ValueOptionsProviderResolver {

	private MetadataBeanFactory beanFactory;

	public ValueOptionsProviderResolverBean(MetadataBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public boolean canResolve(ProviderTypes providerType) {
		return providerType.equals(ProviderTypes.BEAN);
	}

	@Override
	public Object resolve(Object target, OptionsProvider optionsProvider) throws Exception {

		String beanName = optionsProvider.id().replaceFirst("\\.[^.]*$", "");
		String methodName = optionsProvider.id().replaceFirst("^[^.]*\\.", "");

		Object bean = beanFactory.getBean(beanName);

		Method method;
		if (bean instanceof ValueOptionsProvider)
			method = bean.getClass().getMethod(ValueOptionsProvider.METHOD_NAME);
		else
			method = DefaultParserEngine.getMethodNameAnnotatedWithValueOptionsOrDefault(bean.getClass(), methodName);

		return method.invoke(bean);
	}
}