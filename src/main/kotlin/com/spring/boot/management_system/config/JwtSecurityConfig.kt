package com.spring.boot.management_system.config

import com.spring.boot.management_system.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
class JwtSecurityConfig : WebSecurityConfigurerAdapter() {


    @Autowired
    private lateinit var studentService: StudentService

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(studentService)
    }

    override fun configure(http: HttpSecurity?) {
        http!!
            .csrf()
            .disable()
            .cors()
            .disable()
            .authorizeRequests()
            .antMatchers("/token").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }


    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}