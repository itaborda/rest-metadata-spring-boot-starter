package br.com.d4n.ui4entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

	int minLength() default -1;

	int maxLength() default -1;

	String minValue() default "";

	String maxValue() default "";

	String pattern() default "";

	boolean required() default true;

	String message() default "";

}
