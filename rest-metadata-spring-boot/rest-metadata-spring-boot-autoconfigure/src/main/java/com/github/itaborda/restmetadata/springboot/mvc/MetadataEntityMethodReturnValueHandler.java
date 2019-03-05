package com.github.itaborda.restmetadata.springboot.mvc;

import com.github.itaborda.restmetadata.MetadataEntity;
import com.github.itaborda.restmetadata.core.ParserEngine;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MetadataEntityMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;
    private final ParserEngine engine;

    public MetadataEntityMethodReturnValueHandler(HandlerMethodReturnValueHandler delegate, ParserEngine engine) {
        this.delegate = delegate;
        this.engine = engine;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {

        if (returnType.getMethodAnnotation(MetadataEntity.class) != null || returnValue.getClass().getAnnotation(MetadataEntity.class) != null) {

            returnValue = engine.parse(returnValue);
            returnType = new MetadataMethodParameter(returnType, returnValue.getClass());
        }

        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}