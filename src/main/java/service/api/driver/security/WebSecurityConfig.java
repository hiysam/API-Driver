package service.api.driver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN")
                .and()
                .withUser("driver").password("{noop}password").roles("DRIVER");

    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/aktifasiDriver").hasRole("DRIVER")
                .antMatchers(HttpMethod.POST, "/api/assignDriver").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/api/deassignDriver").hasAnyRole("ADMIN","DRIVER")
                .antMatchers(HttpMethod.POST, "/api/deaktifasiDriver").hasRole("DRIVER")
                .antMatchers(HttpMethod.POST, "/api/daftarDriver").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
