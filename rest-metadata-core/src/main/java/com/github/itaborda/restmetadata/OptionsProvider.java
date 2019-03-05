package com.github.itaborda.restmetadata;

import java.lang.annotation.*;

@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionsProvider {

    public enum ProviderTypes {

        LOCAL_METHOD, BEAN, CLASS, REST_RESOURCE

    }

    @Hidden
    ProviderTypes type() default ProviderTypes.LOCAL_METHOD;

    String id() default "";

    @Hidden
    Class<?> impl() default Void.class;

    String[] args() default {};

    String bindLabel() default "";

    String bindValue() default "";

    String groupBy() default "";

    String oderBy() default "";

}
