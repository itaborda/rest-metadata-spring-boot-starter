package com.github.itaborda.restmetadata.springboot;

import com.github.itaborda.restmetadata.core.DefaultParserEngine;
import com.github.itaborda.restmetadata.core.MetadataBeanFactory;
import com.github.itaborda.restmetadata.core.ParserEngine;
import com.github.itaborda.restmetadata.springboot.mvc.MetadataEntityMethodReturnValueHandler;
import com.github.itaborda.restmetadata.springboot.mvc.ValueOptionMethodReturnValueHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass({MetadataBeanFactory.class})
@ConditionalOnWebApplication
@Slf4j
public class RestMetadataAutoConfiguration {

    @Autowired
    ApplicationContext applicationContext;


    private void decorateHandlers(ParserEngine parserEngine, RequestMappingHandlerAdapter handlerAdapter) {

        List<HandlerMethodReturnValueHandler> handlers = handlerAdapter
                .getReturnValueHandlers();

        handlerAdapter.setReturnValueHandlers(getDecoretedHandlers(parserEngine, handlers));
    }

    private List<HandlerMethodReturnValueHandler> getDecoretedHandlers(ParserEngine parserEngine, List<HandlerMethodReturnValueHandler> handlers) {

        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();

        for (HandlerMethodReturnValueHandler handler : handlers) {

            HandlerMethodReturnValueHandler currentHandler = handler;

            if (RequestResponseBodyMethodProcessor.class.equals(handler.getClass())) {

                HandlerMethodReturnValueHandler decorator = new ValueOptionMethodReturnValueHandler(
                        new MetadataEntityMethodReturnValueHandler(handler, parserEngine));

                currentHandler = decorator;

                log.info("@TODO: Metadata Injecting decorator wired up");
            }

            newHandlers.add(currentHandler);
        }

        return newHandlers;
    }


    @Bean
    MetadataBeanFactory metadataBeanFactory() {

        return new SpringBeanFactoryWrapper(applicationContext);
    }

    @Bean
    ParserEngine parserEngine(RequestMappingHandlerAdapter handlerAdapter) {

        ParserEngine parserEngine = new DefaultParserEngine(metadataBeanFactory());

        decorateHandlers(parserEngine, handlerAdapter);

        return parserEngine;
    }

/*
    @Bean
    MetadataBeanFactoryPostProcessor springContextConfigurer() {

        return new MetadataBeanFactoryPostProcessor(parserEngine());
    }*/

}
