package com.example.backend.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Optional;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.JWTService;
import org.springframework.security.config.Customizer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080/swagger-ui/index.html#"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeRequests(authorize -> authorize
    //             .requestMatchers("/users/login", "/users/register", "/users/oauth2/login/success").permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //         .csrf(csrf -> csrf.disable())
    //         .oauth2Login(oauth2 -> oauth2
    //             .successHandler((request, response, authentication) -> {
    //                 OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
    //                 String id = oauthUser.getAttribute("id");
    //                 String name = oauthUser.getAttribute("name"); // Extract the user's name or other attributes if needed

    //                 if (id != null) {
    //                     // Check if the user already exists in the database
    //                     User existingUser = userRepository.findByEmail(id);

    //                     if (existingUser == null) {
    //                         // Create a new user and save to the database
    //                         User newUser = new User();
    //                         newUser.setEmail(id);
    //                         newUser.setFirstName(name); // Set additional attributes as necessary
    //                         userRepository.save(newUser);
    //                         System.out.println("New user saved to the database: " + id);
    //                     }

    //                     // Generate JWT
    //                     String jwtToken = jwtService.generateToken(id);

    //                     // Add JWT to response header
    //                     response.addHeader("Authorization", "Bearer " + jwtToken);

    //                     // Redirect after login
    //                     response.sendRedirect("/users/oauth2/login/success");
    //                 } else {
    //                     response.sendError(400, "Email not found in OAuth2User attributes");
    //                 }
    //             })
    //         );
    //     return http.build();
    //}

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/users/login", "/users/register", "/users/oauth2/login", "/users/oauth2/login/fb","/swagger-ui/**","/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/users/oauth2/login") // Specify the custom OAuth2 login page
                .successHandler((request, response, authentication) -> {
                    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                    String id = oauthUser.getAttribute("id");
                    String name = oauthUser.getAttribute("name"); // Extract the user's name or other attributes if needed

                    if (id != null) {
                        // Check if the user already exists in the database
                        Optional<User> existingUserOptional = userRepository.findByEmail(id);

                        if (existingUserOptional.isEmpty()) {
                            // Create a new user and save to the database
                            User newUser = new User();
                            newUser.setEmail(id);
                            newUser.setFirstName(name); // Set additional attributes as necessary
                            userRepository.save(newUser);
                            System.out.println("New user saved to the database: " + id);
                        }

                        // Generate JWT
                        String jwtToken = jwtService.generateToken(id);

                        // Add JWT to response header
                        response.addHeader("Authorization", "Bearer " + jwtToken);

                        // Redirect after login
                        response.sendRedirect("/users/oauth2/login/fb");
                    } else {
                        response.sendError(400, "Email not found in OAuth2User attributes");
                    }
                })
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add custom JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
