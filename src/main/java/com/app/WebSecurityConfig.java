package com.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.security.JWTRequestFilter;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	private JWTRequestFilter filter;
	

//	@Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//         
//        return authProvider;
//    }
//	
//	
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
	
	@Bean
	public AuthenticationManager authenticatonMgr(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
	    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	}

	
    @Bean
	public SecurityFilterChain configureAuthorization(HttpSecurity http) throws Exception{
    	http.cors().and().csrf().disable()
    	.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());})
    	.and()
    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	.and()
    	.authorizeRequests().antMatchers("/user/signup/**","/v3/**","/swagger-ui/**",
    			"/user/signin/**","/item/**","/itemImage/**","/cart/updateCartStatus/**",
    			"/feedback/addFeedback", "/user/resetPassword","/user/newPassword")
    	.permitAll()
    	.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    	.anyRequest().authenticated()
//        .and()
//        .logout()
//        .invalidateHttpSession(true)
//        .deleteCookies("token")
//        .logoutSuccessHandler(new LogoutSuccessHandler.onLogoutSuccess(request, response, authentication))
        .and()
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        	
		return http.build();
		
	}

}
