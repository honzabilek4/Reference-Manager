package cz.muni.fi.pa165.referenceManager.rest.auth;

import cz.muni.fi.pa165.referenceManager.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.inject.Inject;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService = new UserDetailsServiceImpl();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("adam@user.cz").password("adamPassword")
            .roles("USER").roles("ADMIN")
            .and()
            .withUser("kaja@user.cz").password("kajaPassword")
            .roles("USER")
            .and()
            .withUser("anna@user.cz").password("annaPassword")
            .roles("USER");
    }
}
