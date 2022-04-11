package individualProjectAleksandarPenev.BuyersParadise.interfaces;

import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public interface IOfferService {

    //ResponseEntity<List<IOffer>> ReturnAllOffers();
    CompletableFuture<ResponseEntity> returnAllOffers();
    ResponseEntity<IOffer> ReturnOfferByID(int id);
    int CreateOffer(OfferCreateRequest offerCreateRequest, int id);
    void deleteOffer(int userId, int offerId);
}
