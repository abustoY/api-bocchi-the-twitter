package com.yotsuba.bocchi;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.TimeZone;

@SpringBootApplication
//@SpringBootApplication
//@Configuration,@ComponentScan,@EnableAutoConfigurationを組み合わせたアノテーションである。
// @EnableAutoConfigurationは、AutoConfigurationクラスを有効化するアノテーションである
@EnableJpaAuditing
@EnableWebSecurity(debug = true)
public class BocchiTheTwitter {

    public static void main(String[] args) {
        SpringApplication.run(BocchiTheTwitter.class, args);
    }
//    SpringApplication.run(AppConfig.class, args) の1行だけで、下記の処理すべてが行われる。
//    A.Java Configクラス(第一引数)の読み込み
//    B.コンポーネントスキャン(指定されたパッケージから @Component が付加されたクラスを見つけ出す)
//    C.Beanインスタンスの生成
//    D.BeanインスタンスをDIコンテナ内に保存

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("JST"));
    }


//    @ComponentScan に basePackages を指定しなかった場合、
//    「付加したクラスのパッケージ配下」がコンポーネントスキャン対象になる。

}
