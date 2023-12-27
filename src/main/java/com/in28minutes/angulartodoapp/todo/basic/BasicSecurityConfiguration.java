package com.in28minutes.angulartodoapp.todo.basic;

import static org.springframework.security.config.Customizer.withDefaults;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.*;

@Configuration
public class BasicSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		http.httpBasic(withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();   //enabling frames to access H2 DB
		return http.build();
	}
	
//	@Bean   //storing multiple users in memory
//	public UserDetailsService userDetailsService() {
//		UserDetails user1 = User.withUsername("in28minutes").password("{noop}dummy").roles("USER").build();
//		UserDetails user2 = User.withUsername("arijit").password("{noop}tummy").roles("USER").build();
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript(DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}
	
	@Bean   //storing multiple users in DB using spring JDBC
	public UserDetailsService userDetailsService(DataSource dataSource) {
		UserDetails user1 = User.withUsername("in28minutes").password("dummy").passwordEncoder(str -> passwordEncoder().encode(str)).roles("USER").build();
//		UserDetails user2 = User.withUsername("arijit").password("{noop}tummy").roles("USER").build();
		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user1);
//		jdbcUserDetailsManager.createUser(user2);
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
