package com.project.dinopedia.configuration;

import com.project.dinopedia.services.DinopediaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DinopediaUserDetailsService dinopediaUserDetailsService;

    @Autowired
    public SecurityConfig(DinopediaUserDetailsService dinopediaUserDetailsService) {
        this.dinopediaUserDetailsService = dinopediaUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/dinosaur").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/dinosaur/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/image/add-to-dinosaur").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/image/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(dinopediaUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

