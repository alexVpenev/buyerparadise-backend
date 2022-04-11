package individualProjectAleksandarPenev.BuyersParadise.OfferTests;


import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.Account;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import individualProjectAleksandarPenev.BuyersParadise.model.request.DeleteOfferRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.repository.OfferDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceUnitTest {

    @Autowired
    OfferService offerService;

    @MockBean
    OfferDalJDBC offerDalJDBC;



    @Test
    public void getOfferByIdTest() {
        int id = 1;
        Offer offer = new Offer();

        when(offerDalJDBC.getOfferByID(id))
                .thenReturn(offer);

        Assertions.assertEquals(new ResponseEntity(offer, HttpStatus.OK), offerService.ReturnOfferByID(id));
    }

    @Test
    public void getOfferByIdFailTest() {
        int id = 1;
        Offer offer = new Offer();

        when(offerDalJDBC.getOfferByID(id))
                .thenReturn(null);

        Assertions.assertEquals(new ResponseEntity(HttpStatus.NOT_FOUND), offerService.ReturnOfferByID(id));
    }

    @Test
    public void getAllOffersTest(){
        List<IOffer> offers = new ArrayList<>();
        Offer offer = new Offer();
        offers.add(offer);

        when(offerDalJDBC.getAllOffers())
                .thenReturn(Stream.of(offer)
                        .collect(Collectors.toList()));

        Assertions.assertNotEquals(CompletableFuture.completedFuture(ResponseEntity.ok().body(offers)),offerService.returnAllOffers());

    }

    @Test
    public void getAllOffersFailTest()  {

        when(offerDalJDBC.getAllOffers()).thenReturn(null);
        Assertions.assertNotEquals( CompletableFuture.completedFuture(ResponseEntity.notFound()) ,offerService.returnAllOffers());

    }

    @Test
    public void getAllOffersAsyncTest() throws ExecutionException, InterruptedException{

        when(offerDalJDBC.getAllOffers()).thenReturn(null);

        CompletableFuture<ResponseEntity> result = offerService.returnAllOffers();
        await().atMost(1000, TimeUnit.MILLISECONDS).until(result::isDone);

        Assertions.assertEquals(HttpStatus.OK, offerService.returnAllOffers().get().getStatusCode());

    }

    @Test
    public void getOfferIdByInfoTest()  {

        Offer offer = new Offer();
        offer.setId(1);
        offer.setDescription("baba");
        offer.setName("baba");
        offer.setPrice(1);
        offer.setSeller_id(1);
        offer.setSeller_name("baba");

        OfferCreateRequest request = new OfferCreateRequest();

        request.setName(offer.getName());
        request.setDescription(offer.getDescription());
        request.setPrice(offer.getPrice());


        when(offerDalJDBC.getOfferIDByInfo(1, request.getName(), request.getDescription())).thenReturn(1);

        Assertions.assertEquals( offer.getId() ,
                offerService.CreateOffer(request, 1));

    }


    @Test
    public void deleteOfferTest()  {


        Offer offer = new Offer();
        offer.setId(1);
        offer.setDescription("baba");
        offer.setName("baba");
        offer.setPrice(1);
        offer.setSeller_id(1);
        offer.setSeller_name("baba");

        DeleteOfferRequest request = new DeleteOfferRequest();

        request.setOfferID(offer.getSeller_id());

        offerDalJDBC.deleteOfferWithCheck(1, request.getOfferID());

        when(offerDalJDBC.getOfferByID(1)).thenReturn(null);

        Assertions.assertEquals( ResponseEntity.notFound().build() ,
                offerService.ReturnOfferByID(1));

    }





}
