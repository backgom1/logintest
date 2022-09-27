package com.ssw.fssw.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {

    private final DataSource dataSource;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/assets/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/community/**", "/findteam/**", "/mypage").hasAnyRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login") // 시큐리티가 낚아채서 로그인을 진행한다.
                .defaultSuccessUrl("/") // 나중에 그 전 페이지로 redirect하기
                .failureUrl("/login.fail")
                .usernameParameter("email")//name변경하는 값을 이야기함.
                .passwordParameter("pw")
                .and()
                .build();
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder()) // 스프링에서 알아서 빈에서 관리를 한다.
//                .usersByUsernameQuery("select USER_EMAIL_ID,USER_NICKNAME,USER_PASSWORD "
//                        + "from User "
//                        + "where USER_EMAIL_ID = ?")
//                .authoritiesByUsernameQuery("select Role_code,Role_name "
//                        + "from Role "
//                        + "where email = ?");
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
