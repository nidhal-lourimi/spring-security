package com.nidhallourimi.springsecurity.security;


import com.nidhallourimi.springsecurity.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.nidhallourimi.springsecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class applicationSecurityConfig extends WebSecurityConfigurerAdapter {

    public final ApplicationUserService applicationUserService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public applicationSecurityConfig(ApplicationUserService applicationUserService, PasswordEncoder passwordEncoder) {
        this.applicationUserService = applicationUserService;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http
            .csrf().disable()//cross site request forgery
            /* .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
             .and()*/
             .authorizeHttpRequests()
             .antMatchers("/","/css/*","/jss/*","/login").permitAll()//*
             .antMatchers("/api/**").hasRole(STUDENT.name())
//             .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//             .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//             .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//             .antMatchers("/management/api/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name()) //priority sensitive
             .anyRequest()
             .authenticated()
             .and()
             //.httpBasic();
             .formLogin()
                 .loginPage("/login") // * or .permitAll() //this is gives me  an error but it should work
                 .defaultSuccessUrl("/courses",true)
                .passwordParameter("password")// "*"name of my input in html
                .usernameParameter("username")//
             .and()
             .rememberMe()
                 .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)) //tokenRepository() you own repo (postgress,mysql) //defaults to 2 weeks
                 .key("somethingVerySecured") //best practice using the default  one  provided by spring security
             .rememberMeParameter("remember-me")
             .and()
             .logout()
                 .logoutUrl("/logout")
                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout",  "GET"))
                 .clearAuthentication(true)
                 .invalidateHttpSession(true)
                 .deleteCookies("JSESSIONID","remember-me","JSESSIONID")
                 .logoutSuccessUrl("/login");


    }

    /*@Override
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
    }*/


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
