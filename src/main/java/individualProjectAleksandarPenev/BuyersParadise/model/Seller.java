package individualProjectAleksandarPenev.BuyersParadise.model;


import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Seller extends User implements ISeller {

    @Getter @Setter
    protected String firstName;

    @Getter @Setter
    protected String lastName;

    @Getter @Setter
    protected String phoneNumber;

    @Getter @Setter
    List<IOffer> offers;

    public Seller() {

    }

    public Seller(int id, String username, String email, String password, String role, String firstName, String lastName, String phoneNumber, List<IOffer> offers){
        super(id, username, email, password, role);

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.offers = offers;

    }

}
