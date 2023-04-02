package io.sharing.server.core.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf().disable()
            .cors().disable()
            .authorizeHttpRequests()
            .requestMatchers("/users").permitAll()
            .requestMatchers("/host/**").hasRole("HOST")
            .anyRequest().authenticated()
            .and()
            .formLogin(withDefaults())
            .httpBasic(withDefaults())
            .build()
    }
}
