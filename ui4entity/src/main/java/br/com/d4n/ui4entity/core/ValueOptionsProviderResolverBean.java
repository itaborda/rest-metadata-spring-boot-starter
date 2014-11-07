package br.com.d4n.ui4entity.core;

import java.lang.reflect.Method;

import br.com.d4n.ui4entity.OptionsProvider;
import br.com.d4n.ui4entity.ValueOptionsProvider;
import br.com.d4n.ui4entity.OptionsProvider.ProviderTypes;

public class ValueOptionsProviderResolverBean implements ValueOptionsProviderResolver {

	private BeanFactoryDelegate beanFactory;

	public ValueOptionsProviderResolverBean(BeanFactoryDelegate beanFactory) {
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