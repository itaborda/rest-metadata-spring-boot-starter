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
public @interface InputField {

	public static class Types {

		public static final String CHECKBOX = "checkbox";
		public static final String COLOR = "color";
		public static final String CURRENCY = "currency";
		public static final String DATE = "date";
		public static final String DATETIME = "datetime";
		public static final String DATETIME_LOCAL = "datetime-local";
		public static final String EMAIL = "email";
		public static final String FILE = "file";
		public static final String HIDDEN = "hidden";
		public static final String HOUR = "hour";
		public static final String IMAGE = "image";
		public static final String MONTH = "month";
		public static final String NUMBER = "number";
		public static final String PASSWORD = "password";
		public static final String RADIO = "radio";
		public static final String RANGE = "range";
		public static final String SEARCH = "search";
		public static final String SELECT = "select";
		public static final String TEXT = "text";
		public static final String TEXT_AREA = "textarea";
		public static final String TEL = "tel";
		public static final String TIME = "time";
		public static final String URL = "url";
		public static final String WEEK = "week";

	}

	Group group() default @Group;

	String label() default "";

	String alt() default "";

	String help() default "";

	String placeholder() default "";

	String readonly() default "";

	String inputType() default Types.TEXT;

	String mask() default "";

	Attr[] attrs() default {};
}
