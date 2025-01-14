package cz.tieto.simcakry.notes.security.config;

import cz.tieto.simcakry.notes.model.enums.Role;
import cz.tieto.simcakry.notes.security.JwtAccessFilter;
import cz.tieto.simcakry.notes.security.JwtAuthenticationEntryPoint;
import cz.tieto.simcakry.notes.security.config.csrf.SpaCsrfTokenRequestHandler;
import cz.tieto.simcakry.notes.security.config.csrf.CsrfCookieFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessFilter jwtAccessFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.csrf(request -> request
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()));
        http.csrf(AbstractHttpConfigurer::disable);
//        http.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests((requests) -> {
            requests
                    .requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/users/authenticate").permitAll()
                    .requestMatchers(HttpMethod.GET,"/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/v1/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString())
                    .requestMatchers(HttpMethod.POST, "/v1/**").hasRole(Role.ADMIN.toString())
                    .requestMatchers(HttpMethod.PUT, "/v1/**").hasRole(Role.ADMIN.toString())
                    .requestMatchers(HttpMethod.DELETE, "/v1/**").hasRole(Role.ADMIN.toString())
                    .anyRequest().authenticated();
        }).addFilterBefore(jwtAccessFilter, UsernamePasswordAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
