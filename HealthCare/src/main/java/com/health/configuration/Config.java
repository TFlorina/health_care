package com.health.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorize -> {
                    try {
                        authorize.requestMatchers("/**")
                                .permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .formLogin(formLogin -> {
                                    try{
                                        formLogin.init(http);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .httpBasic(HttpBasic -> HttpBasic.init(http));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
