package com.springboot.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.springboot.springboot.security.jwt.AuthEntryPointJwt;
import com.springboot.springboot.security.jwt.AuthTokenFilter;
import com.springboot.springboot.security.services.UserDetailsServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors((cors) -> cors
            .configurationSource(apiConfigurationSource())
            )
            .csrf(csrf -> csrf.disable())
            .authorizeRequests()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/worker/**").hasRole("WORKER")
            .requestMatchers("/api/client/**").hasRole("CLIENT")
            .requestMatchers("/api/user/profile").authenticated()
            .requestMatchers("/api/user/logout").permitAll()
            .requestMatchers("/api/user/**").permitAll()
            .requestMatchers("/api/**").permitAll()
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public CorsConfigurationSource apiConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.applyPermitDefaultValues();
    corsConfig.addAllowedMethod("GET");
    corsConfig.addAllowedMethod("PUT");
    corsConfig.addAllowedMethod("PATCH");
    corsConfig.addAllowedMethod("POST");
    corsConfig.addAllowedMethod("DELETE");
    corsConfig.addAllowedMethod("OPTIONS");
    corsConfig.setAllowedOrigins(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }

}



//package com.springboot.springboot.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.springboot.springboot.security.jwt.AuthEntryPointJwt;
//import com.springboot.springboot.security.jwt.AuthTokenFilter;
//import com.springboot.springboot.security.services.UserDetailsServiceImpl;

//@Configuration
//@EnableWebSecurity
//
//public class WebSecurityConfig {
////  @Autowired
////  UserDetailsServiceImpl userDetailsService;
////
////  @Autowired
////  private AuthEntryPointJwt unauthorizedHandler;
////
////  @Bean
////  public AuthTokenFilter authenticationJwtTokenFilter() {
////    return new AuthTokenFilter();
////  }
////
////  @Bean
////  public DaoAuthenticationProvider authenticationProvider() {
////    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////
////    authProvider.setUserDetailsService(userDetailsService);
////    authProvider.setPasswordEncoder(passwordEncoder());
////
////    return authProvider;
////  }
////
//  @Bean
//  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//    return authConfig.getAuthenticationManager();
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////      http
////          .authorizeHttpRequests((authorize) -> authorize
//////            .requestMatchers("/api/admin/**").hasRole("ADMIN")
//////            .requestMatchers("/api/worker/**").hasRole("WORKER")
//////            .requestMatchers("/api/client/**").hasRole("CLIENT")
//////            .requestMatchers("/api/user/profile").authenticated()
////            .requestMatchers("/api/**").permitAll()
////            .anyRequest().denyAll()
////          )
////          .csrf(csrf -> csrf.disable());
//    http.authorizeRequests().anyRequest().permitAll();
////    http.authorizeHttpRequests((authorize) -> authorize
//////            .requestMatchers("/api/admin/**").hasRole("ADMIN")
//////            .requestMatchers("/api/worker/**").hasRole("WORKER")
//////            .requestMatchers("/api/client/**").hasRole("CLIENT")
//////            .requestMatchers("/api/user/profile").authenticated()
//////            .requestMatchers("/api/**")
////            .anyRequest().permitAll()
//////            .anyRequest().denyAll()
////    );
////    http.cors().and().csrf().disable()
////        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
////        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
////        .authorizeRequests().antMatchers("/api/**").permitAll();
////    // .antMatchers("/api/test/**").permitAll()
////    // .anyRequest().authenticated();
//
////    http.authenticationProvider(authenticationProvider());
////
////    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }
//}

