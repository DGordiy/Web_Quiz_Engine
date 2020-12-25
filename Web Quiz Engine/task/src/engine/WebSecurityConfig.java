package engine;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //final static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //logger.info("Configuring HttpSecurity...");
        http.csrf().disable().httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/quizzes/**", "/api/quizzes").hasAuthority("ROLE_USER")
                .antMatchers("/", "/actuator/shutdown", "/h2-console/**", "/api/register").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().disable();
        //logger.info(String.format("Configured HttpSecurity: %s", http.toString()));
    }

    /*
    The following two beans are completely enough to configure http basic authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //logger.info("Setting passwordEncoder...");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailService userDetailsService() {
        //logger.info("Setting userDetailsService...");
        return new UserDetailService();
    }
}