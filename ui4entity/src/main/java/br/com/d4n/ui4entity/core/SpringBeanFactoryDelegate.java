package br.com.d4n.ui4entity.core;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class SpringBeanFactoryDelegate implements BeanFactoryDelegate {

	private ConfigurableListableBeanFactory beanFactory;

	public SpringBeanFactoryDelegate(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}

	@Override
	public <T> T getBean(String beanName, Class<T> beanType) {
		return beanFactory.getBean(beanName, beanType);
	}

	@Override
	public String resolveLink(Method method, Object... args) {

		return linkTo(method, args).withSelfRel().getHref();
	}

}
