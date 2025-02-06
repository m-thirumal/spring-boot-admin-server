/**
 * 
 */
package in.thirumal.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import de.codecentric.boot.admin.server.config.AdminServerProperties;

/**
 * @author Thirumal
 * @since 20/11/2018
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	private final AdminServerProperties adminServer;
//
//    public SecurityConfig(AdminServerProperties adminServer) {
//        this.adminServer = adminServer;
//    }

    @Bean
    InMemoryUserDetailsManager configAuthentication() {

       List<UserDetails> users = new ArrayList<>();
       List<GrantedAuthority> adminAuthority = new ArrayList<>();
       adminAuthority.add(new SimpleGrantedAuthority("ADMIN"));
       adminAuthority.add(new SimpleGrantedAuthority("ACTUATOR"));
       UserDetails admin= new User("thirumal", "$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q", adminAuthority);
       users.add(admin);

       List<GrantedAuthority> managerAuthority = new ArrayList<>();
       adminAuthority.add(new SimpleGrantedAuthority("MANAGER"));
       UserDetails manager= new User("t", "{noop}t", managerAuthority);
       users.add(manager);

       return new InMemoryUserDetailsManager(users);
    }

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//        successHandler.setTargetUrlParameter("redirectTo");
//        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");
//
//        http.authorizeHttpRequests()
//            .requestMatchers(this.adminServer.getContextPath() + "/assets/**")
//            .permitAll()
//            .requestMatchers(this.adminServer.getContextPath() + "/login")
//            .permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .loginPage(this.adminServer.getContextPath() + "/login")
//            .successHandler(successHandler)
//            .and()
//            .logout()
//            .logoutUrl(this.adminServer.getContextPath() + "/logout")
//            .and()
//            .httpBasic()
//            .and()
//            .csrf()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .ignoringRequestMatchers(new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances", HttpMethod.POST.toString()), new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances/*", HttpMethod.DELETE.toString()),
//                new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
//            .and()
//            .rememberMe()
//            .key(UUID.randomUUID()
//                .toString())
//            .tokenValiditySeconds(1209600);
//	}
    

    
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable() 
//        		.anonymous().disable()
//        		 .formLogin().permitAll()//.and()//.authorizeHttpRequests().requestMatchers("/", "/eureka/**").permitAll()
//        		 .and().authorizeHttpRequests()
//                         .anyRequest().authenticated();
//        return http.build();
//    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())  // Enable CORS with default settings
            .csrf(csrf -> csrf.disable())  // Disable CSRF (only if necessary)
            .anonymous(AbstractHttpConfigurer::disable) // Disable anonymous access
            .formLogin(form -> form.permitAll()) // Enable form login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/eureka/**").permitAll() // Allow public access to specific endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
            );

        return http.build();
    }


    @Bean
    WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(CorsUtils::isPreFlightRequest).requestMatchers("/actuator/**", "/eureka/**");
    }

    @Bean
    PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
