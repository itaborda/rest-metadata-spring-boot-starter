package com.github.itaborda.restmetadata.core;

import com.github.itaborda.restmetadata.*;
import com.github.itaborda.restmetadata.OptionsProvider.ProviderTypes;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class DefaultParserEngine implements ParserEngine {

    private MetadataBeanFactory beanFactory;

    private List<ValueOptionsProviderResolver> valueOptionsProviderResolvers = new ArrayList<ValueOptionsProviderResolver>();

    public DefaultParserEngine(MetadataBeanFactory metadataBeanFactory) {

        this.setMetadataBeanFactory(metadataBeanFactory);
    }

    @Override
    public Object parse(Object target) throws Exception {

        if (beanFactory == null)
            throw new RuntimeException("ValidationBeanFactory must be not null! Call setMetadataBeanFactory method fisrt.");

        Result result = new Result(target);

        for (Field field : Arrays.asList(target.getClass().getDeclaredFields())) {
            if (field.getAnnotation(InputField.class) != null)
                result.addField(getInputField(target, field));
        }

        return result;
    }

    private InputFieldData getInputField(Object target, Field field) throws Exception {

        Method readMethod = new PropertyDescriptor(field.getName(), target.getClass()).getReadMethod();

        Object fieldValue = readMethod.invoke(target);
        InputFieldData inputData = new InputFieldData(field.getName(), fieldValue);

        Validation validation = field.getAnnotation(Validation.class);
        if (validation != null) {
            inputData.setValidations(getAnnotationAttributes(validation));
        }

        InputField inputField = field.getAnnotation(InputField.class);
        if (inputField == null)
            return inputData;

        inputData.setMetaData(getAnnotationAttributes(inputField));

        switch (inputField.inputType()) {
            case InputField.Types.SELECT:
                putSelectField(target, field, inputData, true);
                break;
            case InputField.Types.CHECKBOX:
                putSelectField(target, field, inputData, false);
                break;
            case InputField.Types.RADIO:
                putSelectField(target, field, inputData, true);
                break;
        }

        return inputData;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void putSelectField(Object target, Field field, InputFieldData inputData, boolean raiseExceptionIfNullAnnotation)
            throws Exception {

        SelectField selectField = field.getAnnotation(SelectField.class);
        if (selectField == null) {
            if (!raiseExceptionIfNullAnnotation)
                return;

            throw new IllegalArgumentException("input type select must be used with @SelectFiled in " + field.getDeclaringClass().getName()
                    + "." + field.getName());
        }

        inputData.addMetaData(ValueOptionsProviderResolver.SELECT_ATTR_NAME, getAnnotationAttributes(selectField));

        if (!selectField.provider().id().isEmpty()) {

            ValueOptionsProviderResolver optionsProvider = getValueOptionProvider(target, selectField.provider().type());

            Object options = optionsProvider.resolve(target, selectField.provider());

            Map map = (Map) inputData.getMetaData(ValueOptionsProviderResolver.SELECT_ATTR_NAME);

            options = convertOptionsMap(options, selectField.provider(), map);
        }

        if (Map.class.isAssignableFrom(field.getType()) || Collection.class.isAssignableFrom(field.getType())
                || Array.class.isAssignableFrom(field.getType())) {

            inputData.addMetaData("type", "list");
        } else {
            inputData.addMetaData("type", "single");
        }
    }

    public void addValueOptionsProviderResolver(ValueOptionsProviderResolver resolver) {
        this.valueOptionsProviderResolvers.add(resolver);
    }

    private ValueOptionsProviderResolver getValueOptionProvider(Object target, ProviderTypes providerType) {

        for (ValueOptionsProviderResolver resolvers : valueOptionsProviderResolvers) {
            if (resolvers.canResolve(providerType))
                return resolvers;
        }
        throw new ValueOptionsProviderResolverNotFoundException("ValueOptionsProviderResolver not found for type '" + providerType + "'");
    }

    public void setMetadataBeanFactory(MetadataBeanFactory beanFactory) {
        this.beanFactory = beanFactory;

        valueOptionsProviderResolvers.add(new ValueOptionsProviderResolverBean(beanFactory));
        valueOptionsProviderResolvers.add(new ValueOptionsProviderResolverClass());
        valueOptionsProviderResolvers.add(new ValueOptionsProviderResolverLocalMethod());
        valueOptionsProviderResolvers.add(new ValueOptionsProviderResolverRestResource(beanFactory));
    }

    @SuppressWarnings("rawtypes")
    private static Object convertOptionsMap(Object options, OptionsProvider optionsProvider, Map<String, Object> map) {

        if (options instanceof Collection) {
            if (optionsProvider.bindLabel().isEmpty())
                throw new IllegalArgumentException("@ValueOptions.bindLabel must be not empty for method returning List");

            map.put("type", "list");
        } else if (options instanceof Map) {

            options = ((Map) options).entrySet();

            map.put("type", "map");
        } else if (!ProviderTypes.REST_RESOURCE.equals(optionsProvider.type()))
            throw new ClassCastException("@ValueOptions method must be return Map or List");

        map.put(ValueOptionsProviderResolver.OPTIONS_ATTR_NAME, options);

        return map;
    }

    public static Method getMethodNameAnnotatedWithValueOptionsOrDefault(Class<?> classType, String defaultMethodName)
            throws NoSuchMethodException, SecurityException {
        for (Method method : classType.getSuperclass().getMethods()) {
            ValueOptions annotation = getAnnotation(method, ValueOptions.class);
            if (annotation != null && defaultMethodName.equals(annotation.value()))
                return method;
        }
        for (Method method : classType.getMethods()) {
            ValueOptions annotation = getAnnotation(method, ValueOptions.class);
            if (annotation != null && defaultMethodName.equals(annotation.value()))
                return method;
        }
        return classType.getMethod(defaultMethodName);
    }

    public static Map<String, Object> getAnnotationAttributes(Annotation annotation) {

        Map<String, Object> attrs = new LinkedHashMap<String, Object>();
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        for (Method method : methods) {

            if (method.getParameterTypes().length > 0 || method.getReturnType() == void.class || method.isAnnotationPresent(Hidden.class))
                continue;

            try {

                Object value = method.invoke(annotation);

                if (value instanceof Class) {
                    value = ((Class<?>) value).getName();
                } else if (value instanceof Class[]) {
                    Class<?>[] clazzArray = (Class[]) value;
                    String[] newValue = new String[clazzArray.length];
                    for (int i = 0; i < clazzArray.length; i++) {
                        newValue[i] = clazzArray[i].getName();
                    }
                    value = newValue;
                } else if (value instanceof Annotation) {
                    attrs.put(method.getName(), getAnnotationAttributes((Annotation) value));
                } else if (value instanceof Annotation[]) {
                    Annotation[] realAnnotations = (Annotation[]) value;
                    List<Map<String, Object>> mappedAnnotations = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < realAnnotations.length; i++) {
                        mappedAnnotations.add(getAnnotationAttributes(realAnnotations[i]));
                    }
                    attrs.put(method.getName(), mappedAnnotations);
                } else {
                    attrs.put(method.getName(), value);
                }
            } catch (Exception ex) {
                throw new IllegalStateException("Could not obtain annotation attribute values", ex);
            }
        }
        return attrs;
    }

    private static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationType) {

        T annotation = method.getAnnotation(annotationType);
        if (annotation != null)
            return annotation;

        for (Class<?> ifc : method.getDeclaringClass().getInterfaces()) {

            for (Method m : ifc.getDeclaredMethods()) {
                if (m.getName().equals(method.getName()) && m.getReturnType().equals(method.getReturnType())) {
                    if (method.getParameterTypes().length == m.getParameterTypes().length) {
                        if (method.getParameterTypes().length == 0)
                            return m.getAnnotation(annotationType);

                        for (int i = 0; i < m.getParameterTypes().length; i++) {
                            Class<?> p = m.getParameterTypes()[i];
                            if (p.equals(method.getParameterTypes()[i]))
                                return m.getAnnotation(annotationType);
                        }
                    }
                }
            }
        }
        return null;
    }

}