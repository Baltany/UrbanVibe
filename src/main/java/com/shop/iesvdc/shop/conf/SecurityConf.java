package com.shop.iesvdc.shop.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Configuro la Seguridad de la App con esta clase
 * 
 * @author Balbino Moyano Lopez
 */
@Configuration
@EnableWebSecurity
public class SecurityConf {
    @Autowired
    private DataSource dataSource;

    /*
     * Consultas que realizo para hacer el login correcto
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        //En esta consulta selecciono el nombre de usuario la contraseña y si está habilitado o no el usuario en la tabla
        .usersByUsernameQuery("select username, password, enable "
        + "from user "
        + "where username = ?")
        //Una vez recojo los campos necesarios de la tabla,ahora busco que tipo de rol desempeña ese usuario para redirigirlo a un endpoint o a otro
        .authoritiesByUsernameQuery("select u.username, r.rol "
        + "from user u, user_rol_list url, user_rol r "
        + "where u.id = url.user_id "
        + "and url.rol_list_id = r.id "
        + "and u.username = ?");
    }


    /*
     * Usamos el Bcrypt para que una vez el usuario se registre pues se codifique la credencial.
     */
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /*
     * Definimos todos y cada uno de los endpoints a los que se pueden acceder y le indicamos quien puede acceder a que endpoint
     */
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/webjars/**", "/img/**", "/js/**", "/register/**", "/ayuda/**", "/acerca/**", "/login", "/denegado", "/inicio",
                        "/menu","/users/signup","/index","/clothes/**","/clothes/details/**","/clothes/men","/clothes/women","clothes/orders",
                        "/thnku")
                        .permitAll()
                        .requestMatchers("/users/**", "/users/*/**", "/users/*/*/**","/orders/**","/orders/*/**","/orders/*/*/**")
                        .hasAuthority("Admin")
                        .anyRequest().authenticated()) // Requiere autenticación para todas las demás solicitudes
                .exceptionHandling(exception -> exception.accessDeniedPage("/denegado"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/menu", true)
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority authority : authentication.getAuthorities()) {
                                if (authority.getAuthority().equals("Admin")) {
                                    response.sendRedirect("/inicio");
                                } else if (authority.getAuthority().equals("Customer")) {
                                    response.sendRedirect("/menu");
                                } else {
                                    throw new IllegalStateException();
                                }
                            }
                        })
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(86400)) // 24 horas de duración de la cookie de recordar sesión
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/inicio")
                        .permitAll())
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF por simplicidad
                .httpBasic().disable() // Deshabilita autenticación HTTP básica
                .build();
    }
    
}