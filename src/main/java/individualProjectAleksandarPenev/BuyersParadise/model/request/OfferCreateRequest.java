package individualProjectAleksandarPenev.BuyersParadise.model.request;

import lombok.Getter;
import lombok.Setter;

public class OfferCreateRequest {

    @Getter @Setter
    protected String name;

    @Getter @Setter
    protected double price;

    @Getter @Setter
    protected String description;

}
