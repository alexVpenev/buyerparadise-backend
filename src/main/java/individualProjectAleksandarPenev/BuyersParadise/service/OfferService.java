package individualProjectAleksandarPenev.BuyersParadise.service;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IOfferDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOfferService;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OfferService implements IOfferService {

    @Autowired
    IOfferDAL dal;

//    @Override
//    public ResponseEntity<List<IOffer>> ReturnAllOffers(){
//        if (dal.getAllOffers() == null){
//            return ResponseEntity.notFound().build();
//        }
//        else{
//            return ResponseEntity.ok().body(dal.getAllOffers());
//        }
//
//    }

    @Override
    public CompletableFuture<ResponseEntity> returnAllOffers(){
        CompletableFuture<List<IOffer>> offers = CompletableFuture.completedFuture(dal.getAllOffers());

        if(offers != null) {

            return offers.thenApply(ResponseEntity::ok);
        } else {

            return (CompletableFuture) ResponseEntity.notFound();
        }

    }

    @Override
    public ResponseEntity<IOffer> ReturnOfferByID(int id)
    {
        IOffer offer = dal.getOfferByID(id);
        if (offer == null)
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            return ResponseEntity.ok().body(offer);
        }
    }

    @Override
    public int CreateOffer(OfferCreateRequest offerCreateRequest, int id) {
        dal.CreateOffer(offerCreateRequest, id);
        int newOfferID = dal.getOfferIDByInfo(id, offerCreateRequest.getName(), offerCreateRequest.getDescription());

        return newOfferID;
    }

    @Override
    public void deleteOffer(int userId, int offerId) {

        dal.deleteOfferWithCheck(userId, offerId);

    }

}
