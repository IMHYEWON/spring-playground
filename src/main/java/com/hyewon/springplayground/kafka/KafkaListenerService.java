package com.hyewon.springplayground.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaListenerService {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;


    public void stopListener() {
        MessageListenerContainer listenerContainer = registry.getListenerContainer("myListener");
        if (listenerContainer != null) {
            listenerContainer.stop();
        }
    }

    public void startListener() {
        MessageListenerContainer listenerContainer = registry.getListenerContainer("myListener");
        if (listenerContainer != null) {
            listenerContainer.start();
        }
    }

    public void startListener(String offsetResetPolicy) {
        MessageListenerContainer listenerContainer = registry.getListenerContainer("myListener");
        if (listenerContainer != null) {
            // 리스너를 중지합니다.
            listenerContainer.stop();

            // 컨슈머 설정을 업데이트합니다.
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetResetPolicy);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            kafkaListenerContainerFactory.getConsumerFactory().updateConfigs(props);

            // 리스너를 다시 시작합니다.
            listenerContainer.start();
        }
    }
}
