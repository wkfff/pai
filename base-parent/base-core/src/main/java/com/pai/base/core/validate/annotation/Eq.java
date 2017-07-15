package com.pai.base.core.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Eq 
 * @Description: TODO
 * @author: fuhao
 * @date: 2017年6月20日 下午5:41:33
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Eq {
	public String fieldName();
	public String value();
}