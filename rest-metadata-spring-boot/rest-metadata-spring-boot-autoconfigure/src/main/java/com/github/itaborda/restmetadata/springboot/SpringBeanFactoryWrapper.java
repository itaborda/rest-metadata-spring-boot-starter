package com.github.itaborda.restmetadata.springboot;

import com.github.itaborda.restmetadata.core.MetadataBeanFactory;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class SpringBeanFactoryWrapper implements MetadataBeanFactory {

    private org.springframework.beans.factory.BeanFactory beanFactory;

    public SpringBeanFactoryWrapper(org.springframework.beans.factory.BeanFactory beanFactory) {
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
