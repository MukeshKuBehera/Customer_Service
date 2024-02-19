package com.customer.config;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	/*
	 * // user creation
	 * 
	 * @Bean public InMemoryUserDetailsManager userDetailsService(PasswordEncoder
	 * passwordEncoder) { // in memory user details UserDetails user =
	 * User.withUsername("mukesh").password(passwordEncoder.encode("1234")).roles(
	 * "USER").build();
	 * 
	 * UserDetails admin =
	 * User.withUsername("admin").password(passwordEncoder.encode("admin")).roles(
	 * "USER", "ADMIN") .build();
	 * 
	 * return new InMemoryUserDetailsManager(user, admin); }
	 * 
	 * // configuring http security
	 * 
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception { http.csrf().disable().authorizeRequests().requestMatchers(
	 * "/customers/register").permitAll()
	 * .requestMatchers("/customers/updateCustomer/**").authenticated()
	 * .requestMatchers("/customers/deactivate/**").authenticated().anyRequest().
	 * permitAll().and().formLogin() .and().logout().and(); return http.build(); }
	 * 
	 * @Bean public PasswordEncoder passwordEncoder() { PasswordEncoder encoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); return encoder; }
	 */

}
