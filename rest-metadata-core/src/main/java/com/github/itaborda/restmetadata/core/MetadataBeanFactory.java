package com.github.itaborda.restmetadata.core;

import java.lang.reflect.Method;

public interface MetadataBeanFactory {

	Object getBean(String beanName);

	<T> T getBean(String beanName, Class<T> beanType);

	String resolveLink(Method method, Object... args);
}
