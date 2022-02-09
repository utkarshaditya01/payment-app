package finalgroup.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import finalgroup.DTO.RequestServerTransactionDTO;
import finalgroup.entity.Transaction;
import finalgroup.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    RestTemplate restTemplate;

    @KafkaListener(topics = "transaction-requests", groupId = "mygroup")
    public void consumeFromTopic(String message) {
        logger.info("Consumed Message : " + message);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Transaction transaction = mapper.readValue(message, Transaction.class);
            logger.info("New : " + transaction);

            // calling bank api
            final String URI_APPROVE = "http://localhost:8080/approve-transaction";
            RequestServerTransactionDTO request = new RequestServerTransactionDTO(transaction.getTransactionId(),
                    State.COMPLETED);
            restTemplate.postForEntity(URI_APPROVE, request, Void.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
