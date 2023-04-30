package com.pm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//암호화

@SuppressWarnings("deprecation") //경고무시
@Configuration
@EnableWebSecurity  
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

@Override
protected void configure(HttpSecurity http) throws Exception {
	http
		.cors().disable()
		.csrf().disable()
		.formLogin().disable()  //기본 로그인 페이지 없애기
		.headers().frameOptions().disable();
	}
}
//configure(HttpSecurity http) : 스프링 시큐리티가 http 요청을 처리할 때 어떻게 동작해야 하는지.
