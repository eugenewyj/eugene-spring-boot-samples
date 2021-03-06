package com.eugenewyj.layout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @since 2017/8/27
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
//@SpringBootApplication    /*相当于上面三个注释的默认配置*/
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
