package com.example.medical_record_system.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");

        return logoutSuccessHandler;
    }

    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = delegate.loadUser(userRequest);

                Set<GrantedAuthority> authorities = new HashSet<>(oidcUser.getAuthorities());

                Map<String, Object> realmAccess = oidcUser.getClaim("realm_access");

                if (realmAccess != null && realmAccess.containsKey("roles")) {
                    List<String> roles = (List<String>) realmAccess.get("roles");

                    roles.forEach(role ->
                            authorities.add(new SimpleGrantedAuthority(role))
                    );
                }

                return new DefaultOidcUser(
                        authorities,
                        oidcUser.getIdToken(),
                        oidcUser.getUserInfo(),
                        "preferred_username"
                );
            }
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "http://localhost:8080/realms/medical-record-system";
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakAuthorityConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/dashboard").authenticated()

                        .requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/doctor/**").hasAuthority("doctor")
                        .requestMatchers("/patient/**").hasAuthority("patient")

                        .requestMatchers("/doctors/**").hasAuthority("admin")

                        .requestMatchers("/patients/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/visits/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/sick-notes/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/reports", "/reports/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/api/doctors/**")
                        .hasAuthority("admin")

                        .requestMatchers("/api/patients/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/api/visits/**")
                        .hasAnyAuthority("admin", "doctor")

                        .requestMatchers("/api/sickNotes/**")
                        .hasAnyAuthority("admin", "doctor")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/unauthorized")
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtCustomizer -> jwtCustomizer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())
                        )
                );

        return http.build();
    }
}