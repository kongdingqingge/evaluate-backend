package com.lingxi;

import cn.afterturn.easypoi.configuration.EasyPoiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {EasyPoiAutoConfiguration.class})
@EnableScheduling
@Component("com.lingxi.myconfig")
@EnableWebMvc
//@SpringBootApplication
public class ElaluateMarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElaluateMarkApplication.class, args);
    }

}