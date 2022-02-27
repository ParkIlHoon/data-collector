package io.hoon.datacollector.config;

import io.hoon.datacollector.common.filter.KeyAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final KeyAuthenticationFilter keyAuthenticationFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers(
            "/v3/api-docs/**",
            "/swagger-ui.html"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // form 로그인 비활성화
            .httpBasic().disable()
            // csrf 비활성화
            .csrf().disable()
            // STATELESS 처리
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .authorizeRequests()
                    .antMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()

            .and()
                .addFilterBefore(keyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            ;
    }
}
