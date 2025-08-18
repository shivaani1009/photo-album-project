package org.studyeasy.SpringStarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {
    private static final String[] WHITELIST = {
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/css/**", 
        "/fonts/**",
        "/images/**",
        "/css/**"
    };

@Bean
public static BCryptPasswordEncoder PasswordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(WHITELIST).permitAll()
        .anyRequest().authenticated())

        .formLogin(form -> form
        .loginPage("/login")                 // GET /login returns your page
        .loginProcessingUrl("/login")        // POST /login is handled by Spring Security
        .usernameParameter("email")          // your input name
        .passwordParameter("password")       // your input name
        .defaultSuccessUrl("/", true)
        .failureUrl("/login?error")
        .permitAll()
      )

      .logout(logout -> logout
        .logoutUrl("/logout")                // default method is POST
        .logoutSuccessUrl("/logout?success")
        .permitAll()
      );


    return http.build();
    
}


}