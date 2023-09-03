package io.sharing.server.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/docs/**").permitAll()
                    .requestMatchers("/users/**").permitAll()
                    .anyRequest().authenticated()
            }.build()
    }

    @Bean
    fun ignoringCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { it.ignoring().requestMatchers("/static") }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
