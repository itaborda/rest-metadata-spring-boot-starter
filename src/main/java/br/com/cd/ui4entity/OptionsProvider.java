package br.com.cd.ui4entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionsProvider {

	public enum ProviderTypes {

		LOCAL_METHOD, BEAN, CLASS, CONTROLLER

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
