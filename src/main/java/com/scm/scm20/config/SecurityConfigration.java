package com.scm.scm20.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm20.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfigration {
    // @Bean
    // public UserDetailsService userDetailsService() {

    // UserDetails user = User
    // .withDefaultPasswordEncoder()
    // .username("admin123")
    // .password("admin123")
    // .roles("ADMIN", "USER").build();
    //
    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    // return inMemoryUserDetailsManager;
    // }
    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenicationSuccessHandler handler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // url configration which is public and which is private

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login config here

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        // logout Functionality config..

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
            logoutForm.logoutSuccessUrl("/");

        });

        //Google Login Configration

        httpSecurity.oauth2Login(oauth2 -> {
            oauth2.loginPage("/login");
            oauth2.successHandler(handler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
