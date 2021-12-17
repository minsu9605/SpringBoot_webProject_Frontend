package com.example.CUSHProjectFront.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

/*    private final MemberService memberService;

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new MemberLoginFailService();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MemberLoginSuccessService();
    }*/

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
                    .antMatchers("/**").permitAll()
//                    .antMatchers("/","/kakao/**","/account/register","/login","/board/list","/api/**","/notice/list").permitAll()
                    .antMatchers("/admin/**","/notice/write").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/api/**")
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                 /*   .successHandler(successHandler())
                    .failureHandler(failureHandler())*/
                    .permitAll();
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }*/
}
