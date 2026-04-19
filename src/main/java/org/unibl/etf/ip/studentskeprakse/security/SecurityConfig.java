package org.unibl.etf.ip.studentskeprakse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/work-log/create/**").hasRole("STUDENT")

                        .requestMatchers(HttpMethod.POST,"/api/work-log/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.POST,"/api/cv/create").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.PUT,"/api/cv/update").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/cv/delete").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/internships/**").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.POST,"/api/internships/").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.POST,"/api/applications/**").hasAnyRole("COMPANY","STUDENT")

                        .requestMatchers("/api/companies/**").hasRole("FACULTY")
                        .requestMatchers("/api/students/**").hasRole("FACULTY")
                        .requestMatchers(HttpMethod.GET,"/api/internships/my-internships").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET,"/api/internships/my-unapplied").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/cv/**").hasAnyRole("STUDENT","COMPANY")
                        .requestMatchers(HttpMethod.GET, "/api/application/company-applications").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.GET, "/api/recommendations/**").hasRole("STUDENT")


                        .requestMatchers("/api/internships/prijavi-se").hasRole("STUDENT")
                        .requestMatchers("/api/internships/dodaj").hasRole("COMPANY")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/rss/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/internships/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/internships/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
  /*  @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())// Obavezno isključiti za REST/JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Nema sesija!
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()// Tvoj login i registracija su javni
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/internships/**").permitAll()
                        .requestMatchers("/api/internships/**").authenticated()
                        .anyRequest().authenticated()

                );
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }*/


}
