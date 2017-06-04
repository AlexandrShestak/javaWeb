package com.shestakam.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.Filter;
import java.util.Locale;
import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.shestakam.*" })
@Import({ WebSecurityConfig.class })
@PropertySource("classpath:configuration.properties")
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/bootstrap/**")
                .addResourceLocations("/bootstrap/");
        registry
                .addResourceHandler("/styles/**")
                .addResourceLocations("/styles/");
        registry
                .addResourceHandler("/output/**")
                .addResourceLocations("classpath:/static/output/");
        registry
                .addResourceHandler("/lib/**")
                .addResourceLocations("classpath:/static/lib/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/javaWeb");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("12345678");
        return basicDataSource;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setMappingResources("hibernate.cfg/Comments.hbm.xml",
                "hibernate.cfg/News.hbm.xml",
                "hibernate.cfg/Tag.hbm.xml",
                "hibernate.cfg/User.hbm.xml",
                "hibernate.cfg/Role.hbm.xml");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);

        return localSessionFactoryBean;
    }

    @Bean
    public ViewResolver jspViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(1);
        return viewResolver;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return new SessionLocaleResolver();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    //need to enable @Value annotation for property injection
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}