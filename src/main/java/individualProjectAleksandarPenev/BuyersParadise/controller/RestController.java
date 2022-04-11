package individualProjectAleksandarPenev.BuyersParadise.controller;

import individualProjectAleksandarPenev.BuyersParadise.model.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping(value = "/api")
@CrossOrigin("*")
public class RestController {

    private final SimpMessagingTemplate msgTemplate;

    @Autowired
    public RestController(SimpMessagingTemplate msgTemplate){
        this.msgTemplate = msgTemplate;
    }

    @GetMapping("/broadcast")
    public String hi(@RequestBody String message) {
        Greeting greeting = new Greeting(message);
        msgTemplate.convertAndSend("/topic/broadcast", greeting);
        return "message sent ...";
    }
}