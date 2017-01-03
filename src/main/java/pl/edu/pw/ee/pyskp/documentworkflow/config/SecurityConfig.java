package pl.edu.pw.ee.pyskp.documentworkflow.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * Created by piotr on 16.12.16.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = Logger.getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().fullyAuthenticated()

                .and()

                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/loginfailed")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()

                .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")

                .and()

                .csrf();
        logger.info("Skonfigurowano zabezpieczenia HTTP");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        Assert.notNull(userDetailsService, "Nie wstrzyknięto userDetailsService do SecurityConfig");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
