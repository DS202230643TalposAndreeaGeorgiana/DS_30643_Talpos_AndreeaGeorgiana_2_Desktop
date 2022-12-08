package com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer.service;

import com.example.ds_30643_talpos_andreeageorgiana_2_desktop.producer.domain.Measure;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

//

@Service
public class RabbitMqSender implements Runnable {
    private final File file = new File("sensor.csv");
    private final File file2 = new File("configFile.txt");
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender() {
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;
    Scanner scan;

    public synchronized void send() {
        try {
            scan = new Scanner(file2);
            while (scan.hasNextLine()) {
                Thread thread = new Thread(this);
                thread.start();
                Thread.sleep(5000);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            int deviceId = scan.nextInt();
            int sleepTime = scan.nextInt();
            Scanner scan = new Scanner(file);
            AtomicInteger i = new AtomicInteger(0);
            while (scan.hasNextLine()) {

                Float value = scan.nextFloat();
                System.out.println(value);
                Measure measureToSend = new Measure();
                System.out.println("deviceid and sleeptime " + deviceId + " " + sleepTime);

                measureToSend.setDeviceId((long) deviceId);
                measureToSend.setTimestamp(new Timestamp(System.currentTimeMillis()));
                measureToSend.getTimestamp().setMinutes(measureToSend.getTimestamp().getMinutes() + i.get() * sleepTime);
                measureToSend.setEnergyConsumption(value);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

                String data = objectMapper.writeValueAsString(measureToSend);

                rabbitTemplate.convertAndSend(exchange, routingkey, data);
//                Thread.sleep(sleepTime*60*1000); // 600 000 milliseconds for 10 minutes
                Thread.sleep(5000); // 600 000 milliseconds for 10 minutes
                i.incrementAndGet();
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}