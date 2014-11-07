package br.com.d4n.ui4entity;

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
public @interface Option {

	String value();

	String key() default "";
}
