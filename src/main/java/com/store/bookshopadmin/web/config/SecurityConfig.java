package com.store.bookshopadmin.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    BookShopAuthenticationSuccessHandler bookShopAuthenticationSuccessHandler;

    @Autowired
    BookShopAuthenticationFailHandler bookShopAuthenticationFailHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository () {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

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
            .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(30 )
                .and()
//            .sessionManagement()
//                .invalidSessionUrl("/session.html")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .and()
//                .and()
            .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book", "/login.html", "/auth", "/session.html").permitAll()
                .anyRequest().authenticated();
    }
}
