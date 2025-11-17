package com.tamle.message_broker_kafka_consumer.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    
    public static final String TOPIC_NAME = "user-topic";
    public static final String GROUP_ID = "user-group-1";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void listen(User user) {
        // Tự động nhận được object User đã được parse từ JSON
        System.out.println("<- Received user: " + user.name() + " | Email: " + user.email());
    }
}
