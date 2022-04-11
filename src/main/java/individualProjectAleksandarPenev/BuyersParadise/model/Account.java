package individualProjectAleksandarPenev.BuyersParadise.model;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.model.User;
import lombok.Getter;
import lombok.Setter;

public class Account extends User implements IAccount {


    public Account() {

    }

    public Account(int id, String username, String email, String password, String role){
        super(id, username, email, password, role);

    }

}
