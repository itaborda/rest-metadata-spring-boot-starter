package br.com.d4n.ui4entity.core;

import java.lang.reflect.Method;

import br.com.d4n.ui4entity.OptionsProvider;
import br.com.d4n.ui4entity.OptionsProvider.ProviderTypes;

public class ValueOptionsProviderResolverController implements ValueOptionsProviderResolver {

	public static final String URL_ATTR_NAME = "URL:";

	private BeanFactoryDelegate beanFactory;

	public ValueOptionsProviderResolverController(BeanFactoryDelegate beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public boolean canResolve(ProviderTypes providerType) {
		return providerType.equals(ProviderTypes.CONTROLLER);
	}

	@Override
	public Object resolve(Object target, OptionsProvider optionsProvider) throws Exception {

		if (!optionsProvider.id().startsWith(URL_ATTR_NAME)) {

			String beanName = optionsProvider.id().replaceFirst("\\.[^.]*$", "");
			String methodName = optionsProvider.id().replaceFirst("^[^.]*\\.", "");

			Object bean = beanFactory.getBean(beanName);

			Method method = DefaultParserEngine.getMethodNameAnnotatedWithValueOptionsOrDefault(bean.getClass(), methodName);
			return beanFactory.resolveLink(method, optionsProvider.args());
		}
		return optionsProvider.id();
	}
}