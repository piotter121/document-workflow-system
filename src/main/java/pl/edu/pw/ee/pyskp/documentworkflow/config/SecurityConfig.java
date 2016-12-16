package pl.edu.pw.ee.pyskp.documentworkflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by piotr on 16.12.16.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/resources/**", "/signup", "/about", "/").permitAll()
                .anyRequest().authenticated()

                .and()

                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/projects")
                .failureUrl("/loginfailed")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()

                .logout().logoutSuccessUrl("/logout")

                .and()

                .csrf();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root").password("root").roles("USER");
    }
}
