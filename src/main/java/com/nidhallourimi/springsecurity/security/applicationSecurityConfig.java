package com.nidhallourimi.springsecurity.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class applicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http
             .authorizeHttpRequests()
             .antMatchers("/","/css/*","/jss/*")
             .permitAll()
             .anyRequest()
             .authenticated()
             .and()
             .httpBasic();
    }
}
