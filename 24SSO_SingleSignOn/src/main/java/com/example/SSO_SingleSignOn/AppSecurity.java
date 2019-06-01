package com.example.SSO_SingleSignOn;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //you cant comment above line bcoz /log POST method are not working

        http.authorizeRequests().antMatchers("/","/facebooklogin","/auth/facebook").permitAll();

        //http.authorizeRequests().antMatchers("/dashboard")
          //      .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')");

        http.authorizeRequests().anyRequest().authenticated().and().formLogin()
                .loginPage("/mylogin").loginProcessingUrl("/log").usernameParameter("name")
                .passwordParameter("pass").defaultSuccessUrl("/dashboard").failureUrl("/myerror")
                .permitAll();
        http.authorizeRequests().and().logout().logoutUrl("/mylogout").logoutSuccessUrl("/")
                ;//.deleteCookies("JSESSIONID").and().rememberMe().rememberMeParameter("remember-me")
                //.key("uniqueAndSecret")
                //.tokenValiditySeconds(5000);

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/access");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("super").password("super").roles("SUPER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

    @Bean
    public NoOpPasswordEncoder noOpPasswordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


}
