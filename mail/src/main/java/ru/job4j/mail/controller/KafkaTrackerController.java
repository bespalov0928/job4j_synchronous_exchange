package ru.job4j.mail.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaTrackerController {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @KafkaListener(topics = {"unavaliabe"})
    public void onApiCall(ConsumerRecord<Integer, String> input) {
        String value = input.value();
        System.out.println(value);
    }

}
