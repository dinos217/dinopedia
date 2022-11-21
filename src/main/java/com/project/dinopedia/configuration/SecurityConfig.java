package com.project.dinopedia.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().anyRequest().authenticated();
    }
}
