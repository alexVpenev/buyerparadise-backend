package individualProjectAleksandarPenev.BuyersParadise.DALInterfaces;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;

import java.util.List;

public interface IOfferDAL {

    List<IOffer> getAllOffers();
    IOffer getOfferByID(int id);
    void CreateOffer(OfferCreateRequest offer, int id);
    int getOfferIDByInfo(int seller_id, String offerName, String description);
    void deleteOfferWithCheck(int userId, int offerID);

}
