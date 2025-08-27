package org.studyeasy.SpringStarter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.studyeasy.SpringStarter.util.constants.Privileges;

@EnableWebSecurity
@Configuration
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
    }; //list of url patterns that should be public

@Bean
public BCryptPasswordEncoder PasswordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(WHITELIST).permitAll()
        .requestMatchers("/post/**").permitAll()
        .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
        .requestMatchers("/profile/**").authenticated()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
        .requestMatchers("/test").hasAuthority(Privileges.ACCESS_ADMIN_PANEL.getPrivilege())
        )
        .csrf(csrf -> csrf
        .ignoringRequestMatchers("/db-console/**")       // H2 console does POSTs
        )
        .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin())       // H2 uses frames
        )

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
        .logoutSuccessUrl("/")
        .permitAll()
      );


    return http.build();
    
}


}