package com.pai.base.api.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface PAIValidate {
	public String key();

}