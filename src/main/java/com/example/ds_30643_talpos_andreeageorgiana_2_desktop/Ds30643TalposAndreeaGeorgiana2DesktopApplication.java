package com.example.ds_30643_talpos_andreeageorgiana_2_desktop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer")
public class Ds30643TalposAndreeaGeorgiana2DesktopApplication {

    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;

    public static void main(String[] args) {

        SpringApplication.run(Ds30643TalposAndreeaGeorgiana2DesktopApplication.class, args);
    }

}
