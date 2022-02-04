/**
 * 
 */
package in.thirumal.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

/**
 * @author Thirumal
 * @since 20/11/2018
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final AdminServerProperties adminServer;

    public SecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
	    	.withUser("thirumal").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("ADMIN").roles("ACTUATOR");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http.authorizeRequests()
            .antMatchers(this.adminServer.getContextPath() + "/assets/**")
            .permitAll()
            .antMatchers(this.adminServer.getContextPath() + "/login")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage(this.adminServer.getContextPath() + "/login")
            .successHandler(successHandler)
            .and()
            .logout()
            .logoutUrl(this.adminServer.getContextPath() + "/logout")
            .and()
            .httpBasic()
            .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances", HttpMethod.POST.toString()), new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances/*", HttpMethod.DELETE.toString()),
                new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
            .and()
            .rememberMe()
            .key(UUID.randomUUID()
                .toString())
            .tokenValiditySeconds(1209600);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/**");
		//web.debug(true);
		web.ignoring()
		.requestMatchers(CorsUtils::isPreFlightRequest)
			.antMatchers("/login.html");
    }
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
}
