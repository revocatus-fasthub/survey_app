package tz.co.fasthub.survey.config;

/**
 * Created by root on 4/5/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("john").password("pa55word").roles("USER");
            auth.inMemoryAuthentication().withUser("adcrdb").password("rootC123").roles("USER", "ADMIN");
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.formLogin().loginPage("/login")
                    .usernameParameter("userId")
                    .passwordParameter("password");

            httpSecurity.formLogin().defaultSuccessUrl("/survey/index")
                    .failureUrl("/login?error");

            httpSecurity.logout().logoutSuccessUrl("/login? logout");


            httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

            httpSecurity.authorizeRequests()
                    .antMatchers("/").permitAll()

                    .antMatchers("/**/survey/index").access("hasRole('ADMIN')")
                    .antMatchers("/**/addQuestion").access("hasRole('ADMIN')")
                    .antMatchers("/**/customerTransactions").access("hasRole('ADMIN')")
                    .antMatchers("/**/customers").access("hasRole('ADMIN')")
                    .antMatchers("/**/questions").access("hasRole('USER')");

            httpSecurity.csrf().disable();

        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
