package com.yotsuba.bocchi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@EnableWebSecurity
@Configuration
public class MySecurityConfig {
//    @Value("${Client.origin}")
//    private String ORIGIN;
    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.formLogin(Customizer.withDefaults());
//        デフォルトでは、認証成功後に、ユーザーを前回のリクエストにリダイレクトさせる。
//        loginProcessingUrlを指定しないと、UsernamePasswordAuthenticationFilterに、loginPageに指定したURLへのPOSTを要求される。
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(formLogin -> formLogin.loginPage("/login-form").loginProcessingUrl("/hoge").usernameParameter("login-id").passwordParameter("login-password").defaultSuccessUrl("/my-page",true));
//        http.cors(customizer -> customizer.configurationSource(corsConfigurationSource()));
//        http.httpBasic(Customizer.withDefaults());
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/authentication/status","/api/authentication/signup","/login-form","/index.html","/js/**","/css/**","/favicon.ico").permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
//        // CORSの設定を行うためのオブジェクトを生成
//        CorsConfiguration configuration = new CorsConfiguration();
//        // クレデンシャル（資格情報（CookieやHTTP認証情報））を含むリクエストを許可する
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin(ORIGIN);
//        configuration.addAllowedOrigin("http://127.0.0.1:5500");
//        // 任意のヘッダーを許可
//        configuration.addAllowedHeader("*");
//        // 任意のHTTPメソッド（GET, POSTなど）を許可
//        configuration.addAllowedMethod("*");
//        // CORS設定をURLベースで行うためのオブジェクトを生成
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // 全てのURLパスにこのCORS設定を適用
//        source.registerCorsConfiguration("/**",configuration);
//        return source;
//    }
}
