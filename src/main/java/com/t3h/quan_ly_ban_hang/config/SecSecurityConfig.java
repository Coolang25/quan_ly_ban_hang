package com.t3h.quan_ly_ban_hang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests((authz) -> authz
                        // cách 1
//                        .antMatchers("/backend/user").hasAnyAuthority("ROLE_ADMIN")


                        // đường đãn bắt đầu bằng t/backend thì bắt buộc phải đăng nhập
                        // /backend/** đệ quy (/backend/user, /backend/user/new)
                        // /backend* chặn 1 cập chỉ chặng /backend/user, /backend/product
                        //.antMatchers("/backend/**").authenticated()
                        .antMatchers("/backend/**").hasRole("ADMIN")


                        // các đường dẫn còn lại thì truy cập không bị chặn
                        .anyRequest().permitAll()

                )
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin( (form) -> {
                    form.loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .loginProcessingUrl("/doLogin")
                            .defaultSuccessUrl("/home", true)
                            .failureUrl("/login?error=true");
                })

//                .failureHandler(authenticationFailureHandler())
                .logout((logout) -> {
                    logout.logoutUrl("/logout")
                            .deleteCookies("JSESSIONID");
                })


//                .logoutSuccessHandler(logoutSuccessHandler())
        ;
        return http.build();
    }
}
