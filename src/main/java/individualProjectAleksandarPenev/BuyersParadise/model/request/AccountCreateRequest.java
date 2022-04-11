package individualProjectAleksandarPenev.BuyersParadise.model.request;

import lombok.Getter;
import lombok.Setter;

public class AccountCreateRequest {

    @Getter @Setter
    protected String username;

    @Getter @Setter
    protected String email;

    @Getter @Setter
    protected String password;

}
