package com.tamle.message_broker_kafka_producer.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableScheduling // Bật tính năng chạy định kỳ
public class ProducerService {

    private final KafkaTemplate<String, User> kafkaTemplate;
    private final AtomicInteger counter = new AtomicInteger(0);

    // Topic chúng ta sẽ gửi message tới
    public static final String TOPIC_NAME = "user-topic";

    public ProducerService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Chạy mỗi 3 giây
    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        int id = counter.incrementAndGet();
        User user = new User(id, "User " + id, "user" + id + "@example.com");

        // Gửi message
        kafkaTemplate.send(TOPIC_NAME, user);
        
        System.out.println("-> Sent user: " + user.name());
    }
}
