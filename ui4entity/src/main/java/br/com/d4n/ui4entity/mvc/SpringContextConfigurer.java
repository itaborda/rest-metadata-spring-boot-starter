package br.com.d4n.ui4entity.mvc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import br.com.d4n.ui4entity.core.DefaultParserEngine;
import br.com.d4n.ui4entity.core.ParserEngine;
import br.com.d4n.ui4entity.core.SpringBeanFactoryDelegate;

public class SpringContextConfigurer implements BeanFactoryPostProcessor {

	Logger logger = LoggerFactory.getLogger(SpringContextConfigurer.class);

	private Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType = RequestResponseBodyMethodProcessor.class;

	private ParserEngine parserEngine = new DefaultParserEngine();

	public SpringContextConfigurer() {
	}

	public SpringContextConfigurer(
			Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType) {
		this.handlerReturnValueType = handlerReturnValueType;
	}

	public SpringContextConfigurer(ParserEngine engine) {
		this.parserEngine = engine;
	}

	public SpringContextConfigurer(
			Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType,
			ParserEngine engine) {
		this.parserEngine = engine;
		this.handlerReturnValueType = handlerReturnValueType;
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

		// TODO: add log

		parserEngine.setValidationBeanFactory(new SpringBeanFactoryDelegate(
				beanFactory));

		RequestMappingHandlerAdapter handlerAdapter = beanFactory
				.getBean(RequestMappingHandlerAdapter.class);

		// List<HandlerMethodReturnValueHandler> handlers = new
		// LinkedList<HandlerMethodReturnValueHandler>(handlerAdapter.getReturnValueHandlers().getHandlers());
		List<HandlerMethodReturnValueHandler> handlers = handlerAdapter
				.getReturnValueHandlers();

		decorateHandlers(handlers);
		handlerAdapter.setReturnValueHandlers(handlers);

		// handlerAdapter.afterPropertiesSet();
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {

			if (handlerReturnValueType.equals(handler.getClass())) {
				HandlerMethodReturnValueHandler decorator = new ValueOptionReturnValueHandler(
						new ControllerReturnValueHandler(handler, parserEngine));
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);

				logger.info("@TODO: Metadata Injecting decorator wired up");
			}
		}
	}

	public void setHandlerReturnValueType(
			Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType) {
		this.handlerReturnValueType = handlerReturnValueType;
	}

	public void setParserEngine(ParserEngine parserEngine) {
		this.parserEngine = parserEngine;
	}

}
