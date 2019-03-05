package com.github.itaborda.restmetadata.springboot.mvc;

import org.springframework.core.MethodParameter;

public class MetadataMethodParameter extends MethodParameter {

    private Class<?> parameterType;

    public MetadataMethodParameter(MethodParameter original, Class<?> parameterType) {
        super(original);
        this.parameterType = parameterType;
    }

    @Override
    public Class<?> getParameterType() {
        return this.parameterType;
    }
}