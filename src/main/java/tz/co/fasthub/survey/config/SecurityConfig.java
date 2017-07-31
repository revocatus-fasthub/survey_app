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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;



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

/*
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .formLogin().loginPage("/login").successForwardUrl("/survey/index").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }

    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }


/*
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("john").password("pa55word").roles("USER");
        auth.inMemoryAuthentication().withUser("adcrdb").password("rootC123").roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin().loginPage("/survey/login")
                .usernameParameter("userId")
                .passwordParameter("password");

        httpSecurity.formLogin().defaultSuccessUrl("/survey/index")
                .failureUrl("/login?error");

        httpSecurity.logout().permitAll().logoutSuccessUrl("/survey/login");


        httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll();

        httpSecurity.csrf().disable();

    }*/

}