package com.naldojesse.loginreg.config;

import com.naldojesse.loginreg.services.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    public WebSecurityConfig(UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
    }

    //add Bcrypt bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //this will override the pop up authentication and add configuration for FORM authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                //Allows restricting access based upon the HttpServletRequest
                authorizeRequests()
                    //PathMatcher implementation for Ant-style path patterns
                    //we are allowing everything in that matches these to everyone
                    .antMatchers("/css/**", "/js/**", "/registration").permitAll()
                //anyRequest: Maps any request
                //authenticated: specify URLs that are allowed by authenticated users only
                .anyRequest().authenticated()
                .and()
                //specifies to support form based authentication
                // now users are going to be authenticated via a FORM
                .formLogin()
                //specifies URL to send users to if login is required
                .loginPage("/login")
                .permitAll()
                .and()
                //provides logout support
                //default accessing URL "/logout" will log user out
                //invalidate Session, clean up any authentication, redirect to login?success
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImplementation).passwordEncoder(bCryptPasswordEncoder());
    }
}
