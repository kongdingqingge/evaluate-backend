package com.lingxi.myConfig;

import com.lingxi.filters.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends GlobalMethodSecurityConfiguration {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 5; i++) {
            String encode = passwordEncoder.encode("123456");
            System.out.println(encode);
        }
    }

    /**
     * 1.过滤器链集中配置
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()// 基于 token，不需要 csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于 token，不需要 session
                .and()
                // 请求认证的配置
                .authorizeRequests(authorize -> authorize
                        .antMatchers(
                                "/gUser/login/**",
                                "/gUmpires/login/**",
                                "/gUmpires/mainLogin/**",
                                "/chat/**"
                        ).anonymous()//允许匿名用户访问,不允许已登入用户访问
                        .anyRequest().authenticated()//其他地址的访问均需验证权限
                )
                // 添加 JWT 过滤器，(处排除外的,其他所有请求都会经过)过滤器在用户名密码认证过滤器之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //配置认证授权异常处理器
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .cors()//允许跨域
                .and()
                .build();
    }

    /**
     * 2.密码明文加密方式配置,随机自动加盐
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 3.获取AuthenticationManager（认证管理器），登录时认证提供 authentic方法
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * 4.配置跨源访问(CORS)
     *
     * @return
     */

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://81.69.43.25:81", "https://evaluate-app.knowskf.com/api", "https://evaluate-pc.knowskf.com/api", "https://evaluate-ma.knowskf.com/api"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);


        return source;
    }

    /**
     * 4. 注入使用自己定义DaoAuthenticationProviderCustom来代替框架的DaoAuthenticationProvider
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProviderCustom); // 使用自定义的 DaoAuthenticationProviderCustom
    }

    /**
     * 5.配置跨源访问(CORS)
     *
     * @return
     */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }


    /**
     * 6.资源放行配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/images/**", "/webjars/**");
    }


}