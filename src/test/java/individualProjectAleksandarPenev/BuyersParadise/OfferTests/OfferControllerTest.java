package individualProjectAleksandarPenev.BuyersParadise.OfferTests;

import individualProjectAleksandarPenev.BuyersParadise.controller.AccountController;
import individualProjectAleksandarPenev.BuyersParadise.controller.OffersController;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import individualProjectAleksandarPenev.BuyersParadise.model.Seller;
import individualProjectAleksandarPenev.BuyersParadise.model.request.DeleteOfferRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.service.AccountService;
import individualProjectAleksandarPenev.BuyersParadise.service.OfferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferControllerTest {

    @Autowired
    OffersController offersController;

    @MockBean
    OfferService offerService;

    @MockBean
    AccountService accountService;


    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void getAllOffersTest() throws NullPointerException {

        CompletableFuture<List<IOffer>> offers = CompletableFuture.completedFuture(new ArrayList<IOffer>());

        when(offerService.returnAllOffers())
                .thenReturn(offers.thenApply(ResponseEntity::ok));

        Assertions.assertNotEquals(offers.thenApply(ResponseEntity::ok), offersController.GetAllOffers());

    }

    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void getOfferByIdTest() throws NullPointerException {

        Offer offer = new Offer();

        when(offerService.ReturnOfferByID(1))
                .thenReturn(ResponseEntity.notFound().build());

        Assertions.assertEquals(ResponseEntity.notFound().build(), offersController.GetOfferByID(1));

    }

    @Test
    @WithMockUser(username = "name", roles = {"SELLER"})
    void createOfferTest() throws NullPointerException {

        OfferCreateRequest request = new OfferCreateRequest();

        request.setName("1");
        request.setDescription("1");
        request.setPrice(1);

        when(accountService.getAccountIDByUsername("1"))
                .thenReturn(1);

        when(offerService.CreateOffer(request, 1))
                .thenReturn(1);

        Assertions.assertEquals(ResponseEntity.ok().body(0), offersController.CreateOffer(request));

    }

    @Test
    @WithMockUser(username = "name", roles = {"SELLER"})
    void deleteOfferTest() throws NullPointerException {

        DeleteOfferRequest request = new DeleteOfferRequest();

        request.setOfferID(1);

        when(accountService.getAccountIDByUsername("1"))
                .thenReturn(1);


        Assertions.assertEquals(ResponseEntity.ok().build(), offersController.deleteOffer(request));

    }


}
