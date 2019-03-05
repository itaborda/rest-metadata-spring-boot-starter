package com.github.itaborda.restmetadata.core;

import com.github.itaborda.restmetadata.OptionsProvider;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

import java.lang.reflect.Method;

public class ValueOptionsProviderResolverRestResource implements ValueOptionsProviderResolver {

    public static final String URL_ATTR_NAME = "url:/";

    private MetadataBeanFactory beanFactory;

    public ValueOptionsProviderResolverRestResource(MetadataBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean canResolve(ProviderTypes providerType) {
        return providerType.equals(ProviderTypes.REST_RESOURCE);
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
        return optionsProvider.id().replaceFirst(URL_ATTR_NAME, "");
    }
}