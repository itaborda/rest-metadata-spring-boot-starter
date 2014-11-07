package br.com.d4n.ui4entity.core;

import java.lang.reflect.Method;

public interface BeanFactoryDelegate {

	Object getBean(String beanName);

	<T> T getBean(String beanName, Class<T> beanType);

	String resolveLink(Method method, Object... args);
}
