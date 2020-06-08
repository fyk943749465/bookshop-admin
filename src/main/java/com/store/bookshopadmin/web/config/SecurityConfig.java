package com.store.bookshopadmin.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    BookShopAuthenticationSuccessHandler bookShopAuthenticationSuccessHandler;

    @Autowired
    BookShopAuthenticationFailHandler bookShopAuthenticationFailHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/auth")
                .usernameParameter("user")
                .passwordParameter("pass")
                .successHandler(bookShopAuthenticationSuccessHandler)
                .failureHandler(bookShopAuthenticationFailHandler)
                .and()
            .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book", "/login.html", "/auth").permitAll()
                .anyRequest().authenticated();
    }
}
