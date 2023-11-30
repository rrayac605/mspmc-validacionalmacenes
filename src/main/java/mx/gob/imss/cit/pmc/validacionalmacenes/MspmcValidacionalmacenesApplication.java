package mx.gob.imss.cit.pmc.validacionalmacenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.pmc.validacionalmacenes.security.JWTAuthorizationFilter;
import mx.gob.imss.cit.pmc.validacionalmacenes.security.service.TokenValidateService;

@SpringBootApplication
public class MspmcValidacionalmacenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MspmcValidacionalmacenesApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Bean
		public TokenValidateService tokenPmcValidateService() {
			return new TokenValidateService();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(tokenPmcValidateService()), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/msvalidacionAlmacenes/v1/validar**").permitAll()
				.antMatchers(HttpMethod.GET, "/msvalidacionAlmacenes/v1/buscar**").permitAll().
				 antMatchers(HttpMethod.GET, "**").permitAll()
				.antMatchers(HttpMethod.POST, "**").permitAll()
				.antMatchers(HttpMethod.PUT, "**").permitAll()
				.antMatchers(HttpMethod.DELETE, "**").permitAll()
				.anyRequest().authenticated();
		}
		
		@Override
		public void configure(WebSecurity webSecurity) {
			webSecurity.ignoring().antMatchers(
					"/swagger-resources/**",
					"/swagger-ui.html",
					"/v2/api-docs",
					"/webjars/**"
			);
		}
	}

}
