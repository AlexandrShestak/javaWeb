package com.shestakam.configuration;

import com.shestakam.user.authorization.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public MyUserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/bootstrap/**", "/styles/**", "/login*", "/language*",
                            "/registration*", "/output/**", "/lib/**", "/webjars/**").permitAll()
                    .antMatchers("/user*").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/j_spring_security_check")
                    .defaultSuccessUrl("/news", true)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureForwardUrl("/login?error")
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .and()
                .csrf()
                    .disable()
                .httpBasic();
    }
}
