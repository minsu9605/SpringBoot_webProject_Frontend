package com.example.CUSHProjectFront.config;

import com.example.CUSHProjectFront.service.MemberLoginFailService;
import com.example.CUSHProjectFront.service.MemberLoginService;
import com.example.CUSHProjectFront.service.MemberLoginSuccessService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberLoginService memberLoginService;

    @Bean
    public AuthenticationSuccessHandler successService() {
        return new MemberLoginSuccessService();
    }
    @Bean
    public AuthenticationFailureHandler failureService() {
        return new MemberLoginFailService();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                    .antMatchers("/**").permitAll()D
                    .antMatchers("/","/kakao/**","/account/register","/login","/board/list","/api/**","/notice/list").permitAll()
                    .antMatchers("/admin/**","/notice/write").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/api/**")
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    .successHandler(successService())
                    .failureHandler(failureService())
                    .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberLoginService).passwordEncoder(passwordEncoder());
    }
}
