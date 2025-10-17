package com.library.config;

import com.library.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/uploads/**").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/books/new", "/books/edit/**", "/books/update/**", "/books/delete/**").hasAnyAuthority("ADMIN", "LIBRARIAN")
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .permitAll()
            .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
            .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
