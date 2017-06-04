package com.shestakam.configuration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }
 
    /**
     * added to load spring security filter in root context (created in onStartup())
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebSecurityConfig.class};
    }
 
    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }
 
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }
}