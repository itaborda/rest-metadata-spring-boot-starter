package com.github.itaborda.restmetadata.springboot.mvc;

import com.github.itaborda.restmetadata.core.ParserEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

@Slf4j
public class MetadataBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType = RequestResponseBodyMethodProcessor.class;

    private ParserEngine parserEngine;

    public MetadataBeanFactoryPostProcessor(ParserEngine engine) {
        this.parserEngine = engine;
    }

    public MetadataBeanFactoryPostProcessor(
            Class<? extends HandlerMethodReturnValueHandler> handlerReturnValueType,
            ParserEngine engine) {
        this.parserEngine = engine;
        this.handlerReturnValueType = handlerReturnValueType;
    }

    //@Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // TODO: add log

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
                HandlerMethodReturnValueHandler decorator = new ValueOptionMethodReturnValueHandler(
                        new MetadataEntityMethodReturnValueHandler(handler, parserEngine));
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);

                log.info("@TODO: Metadata Injecting decorator wired up");
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
