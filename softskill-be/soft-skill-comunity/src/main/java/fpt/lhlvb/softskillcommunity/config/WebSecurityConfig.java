package fpt.lhlvb.softskillcommunity.config;

import fpt.lhlvb.softskillcommunity.securities.UserDetailsServiceImpl;
import fpt.lhlvb.softskillcommunity.securities.jwt.AuthTokenFilter;
import fpt.lhlvb.softskillcommunity.securities.jwt.JwtConfigurer;
import fpt.lhlvb.softskillcommunity.securities.oauth2.CustomOAuth2UserService;
import fpt.lhlvb.softskillcommunity.securities.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.BeanIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfigurer jwtConfigurer;

    @Autowired
    private OAuth2SuccessHandler oauthSuccessHandler;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
//                        .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .maxAge(3600);
            }
        };
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/auth/**",
                        "/api/mail/**",
                        "/api/user/**",
                        "/api/rooms/**",
                        "/api/softSkill/**",
                        "/api/tasks/**",
                        "/api/**/**",
                        "/api/**/**/**",
                        "/websocket",
                        "/websocket/**",
                        "/img/**",
                        "/static/**").permitAll()
                .antMatchers("/api/roomTasks/**","/api/manager/**","/auth/**", "/oauth2/**", "/**/*swagger*/**", "/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .and()
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(oauthSuccessHandler)
                .and()
                .apply(jwtConfigurer);
    }
}
