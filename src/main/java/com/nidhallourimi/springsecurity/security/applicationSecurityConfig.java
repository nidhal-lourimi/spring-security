package com.nidhallourimi.springsecurity.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.nidhallourimi.springsecurity.security.ApplicationUserPermission.*;
import static com.nidhallourimi.springsecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class applicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public applicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http
             .csrf().disable()
             .authorizeHttpRequests()
             .antMatchers("/","/css/*","/jss/*").permitAll()
             .antMatchers("/api/**").hasRole(STUDENT.name())
             .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
             .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
             .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
             .antMatchers("/management/api/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name())
             .anyRequest()
             .authenticated()
             .and()
             .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails normalUser = User.builder()
                .username("raoul")
                .password(passwordEncoder.encode("password"))
                //.roles(STUDENT.name())//ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder().username("linda")
                .password(passwordEncoder.encode("admin"))
               // .roles(ADMIN.name())//ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails trainee = User.builder().username("tom")
                .password(passwordEncoder.encode("notadmin"))
                //.roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(normalUser,adminUser,trainee);
    }
}
