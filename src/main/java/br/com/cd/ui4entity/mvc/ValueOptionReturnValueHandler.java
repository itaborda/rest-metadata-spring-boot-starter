package br.com.cd.ui4entity.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import br.com.cd.ui4entity.ValueOptions;
import br.com.cd.ui4entity.core.ValueOptionsProviderResolver;

public class ValueOptionReturnValueHandler implements HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;

	public ValueOptionReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {

		if (returnType.getMethodAnnotation(ValueOptions.class) != null) {

			Map<String, Object> map = new HashMap<String, Object>();
			if (returnValue instanceof List) {

				map.put("type", "list");
			} else if (returnValue instanceof Map) {

				returnValue = ((Map) returnValue).entrySet();
				map.put("type", "map");
			} else
				throw new ClassCastException("@ValueOptions method must be return Map or List");

			map.put(ValueOptionsProviderResolver.OPTIONS_ATTR_NAME, returnValue);
			returnValue = map;

			returnType = new UI4EntityMethodParameter(returnType, returnValue.getClass());
		}

		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}
}