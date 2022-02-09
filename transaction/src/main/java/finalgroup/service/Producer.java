package finalgroup.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import finalgroup.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    Logger logger = LoggerFactory.getLogger(Producer.class);

    public static final String topic = "transaction-requests";
    public static final String key = "test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic(String message){
        logger.info("Publishing to topic : " + topic);
        kafkaTemplate.send(topic, key, message);
    }

    public void publishTransaction(Transaction transaction) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(transaction);
        logger.info("Publishing to topic : " + topic);
        kafkaTemplate.send(topic, String.valueOf(transaction.getWallet().getId()), jsonString);
    }

}
