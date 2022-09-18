package com.momentum.invest.transactservice.security;

import com.momentum.invest.transactservice.jwt.JwtTokenVerifier;
import com.momentum.invest.transactservice.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.momentum.invest.transactservice.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        var adminUser = User.builder()
                .username("bobsmith")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name()) // ROLE_ADMIN
                .build();

        var investorUser = User.builder()
                .username("joeroot")
                .password(passwordEncoder.encode("password"))
                .roles(INVESTOR.name()) //ROLE_INVESTOR
                .build();

        return new InMemoryUserDetailsManager(adminUser,investorUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/swagger-ui.html")
                .permitAll()
                .antMatchers("/investor/*").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated();
    }
}
