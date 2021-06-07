package com.dancemind.springmvc.bicycleshop.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation
        .authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web
        .builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/bicycles")
                        .access("hasRole('ROLE_USER')")
                    .antMatchers("/adminpanel")
                        .access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/", "/**")
                        .access("permitAll")


                .and()
                .formLogin()
                .loginPage("/login")
                //            .usernameParameter("email")
                //            .passwordParameter("password")
                //            .defaultSuccessUrl("/default")
                //            .failureUrl("/login?error").permitAll()
                .failureUrl("/login-error")

                .and()
                .logout()
                .logoutSuccessUrl("/")

                // Make H2-Console non-secured; for debug purposes
                .and()
                .csrf()
                .ignoringAntMatchers("/h2/**")

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("B1k3Sh0p");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }
}
