package com.udemy;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class MvcConfig  implements WebMvcConfigurer{
	
	private static final Log LOG = LogFactory.getLog(MvcConfig.class);
	
	
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String recoursePath= Paths.get("uploads").toAbsolutePath().toUri().toString();
		LOG.info(recoursePath);
		
		registry.addResourceHandler("/uploads/**").addResourceLocations(recoursePath);
	}*/

	
	
}
