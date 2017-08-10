package tz.co.fasthub.survey.config;

/**
 * Created by root on 4/5/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;

    private AccessDeniedHandler accessDeniedHandler;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().and()
                .authorizeRequests().antMatchers("/**").access("hasRole('ADMIN')")
                .antMatchers("/survey/registration/new", "/resources/static/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/crdb/login").permitAll().loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/survey/index")
                .failureUrl("/login?error")
                .and()
                .logout().logoutUrl("/survey/logout").logoutSuccessUrl("/survey/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);


    }

    public void configureWeb(WebSecurity web) throws Exception {
        //Web resources
        web.ignoring().antMatchers("/css/**");
      //  web.ignoring().antMatchers("/scripts/**");
        web.ignoring().antMatchers("/images/**");
    }

    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }

}