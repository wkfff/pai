package com.pai.app.web.core.framework.web.binding;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class CustomDataBinding implements WebBindingInitializer {
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		dateFormat.setLenient(false);		

		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				datetimeFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new CustomTimestampEditor(datetimeFormat, true));
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new CustomTimestampEditor(dateFormat, true));		
	}
}
