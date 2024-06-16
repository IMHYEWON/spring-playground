package com.hyewon.springplayground.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

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
}
