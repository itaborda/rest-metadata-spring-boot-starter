package br.com.d4n.ui4entity.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import br.com.d4n.ui4entity.UI4Entity;
import br.com.d4n.ui4entity.core.ParserEngine;

public class ControllerReturnValueHandler implements HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;
	private final ParserEngine engine;

	public ControllerReturnValueHandler(HandlerMethodReturnValueHandler delegate, ParserEngine engine) {
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

		if (returnType.getMethodAnnotation(UI4Entity.class) != null || returnValue.getClass().getAnnotation(UI4Entity.class) != null) {

			returnValue = engine.parse(returnValue);
			returnType = new UI4EntityMethodParameter(returnType, returnValue.getClass());
		}

		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}
}