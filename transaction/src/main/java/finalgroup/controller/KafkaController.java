package finalgroup.controller;

import finalgroup.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    Producer producer;

    @GetMapping("/kafka/{name}")
    public void sendMessage(@PathVariable String name){
        producer.publishToTopic(name);
        return;
    }


}

