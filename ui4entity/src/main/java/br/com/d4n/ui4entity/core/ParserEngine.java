package br.com.d4n.ui4entity.core;

public interface ParserEngine {

	Object parse(Object target) throws Exception;

	void setValidationBeanFactory(BeanFactoryDelegate beanFactory);
}