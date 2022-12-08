package com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer.controller;

import com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ProducerController implements CommandLineRunner {
    private final RabbitMqSender rabbitMqSender;

    @Autowired
    public ProducerController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @Value("${app.message}")
    private String message;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(message);
        rabbitMqSender.send();
    }

}

