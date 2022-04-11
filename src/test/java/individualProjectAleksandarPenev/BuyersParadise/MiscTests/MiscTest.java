package individualProjectAleksandarPenev.BuyersParadise.MiscTests;

import individualProjectAleksandarPenev.BuyersParadise.controller.ChatController;
import individualProjectAleksandarPenev.BuyersParadise.controller.RestController;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.*;
import individualProjectAleksandarPenev.BuyersParadise.model.request.CreateEntryPathRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiscTest {

    @Autowired
    ChatController controller;

    @Test
    void CreateEntryPathRequestTest() {

        CreateEntryPathRequest createEntryPathRequest = new CreateEntryPathRequest();

        createEntryPathRequest.setOfferID(1);

        Assertions.assertEquals(1, createEntryPathRequest.getOfferID());
    }

    @Test
    void MessageTest() {

        Message msg = new Message();

        Message message = new Message("hello");

        message.setName("yahoo");

        Assertions.assertEquals("yahoo", message.getName());
    }

    @Test
    void GreetingTest() {

        Greeting grt = new Greeting();

        Greeting greeting = new Greeting("greeting");

        Assertions.assertEquals("greeting", greeting.getContent());
    }

    @Test
    void OfferTest() {

        Offer offer = new Offer(1, 1, "name", 1.0, "desc", "sellerName");

        String msg = "Student{id=1, name='name', price=1.0, description=desc}";

        Assertions.assertEquals(msg, offer.toString());
    }

    @Test
    void SellerTest() {

        Seller seller = new Seller();

        seller.setFirstName("sadsa");
        seller.setLastName("sadsa");
        seller.setPhoneNumber("sadsa");
        seller.setOffers(new ArrayList<IOffer>());

        Assertions.assertEquals("sadsa", seller.getFirstName());
    }

    @Test
    void UserTest() {

        Account account = new Account(1,"name","jsd","hshja", "USER");


        Assertions.assertEquals("USER", account.getRole());
    }

    @Test
    void ChatControllerTest() throws Exception {

        Message message = new Message("hello");

        Assertions.assertNotEquals(new Greeting("Hello, hello!"), controller.greeting(message));

    }




}
