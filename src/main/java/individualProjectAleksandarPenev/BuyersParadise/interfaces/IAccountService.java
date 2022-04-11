package individualProjectAleksandarPenev.BuyersParadise.interfaces;

import individualProjectAleksandarPenev.BuyersParadise.model.request.AccountCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.BecomeSellerRequest;
import org.springframework.http.ResponseEntity;

public interface IAccountService {

    IAccount GetAccountByUsername(String username);
    int getAccountIDByUsername(String username);
    ResponseEntity UserRegistration(AccountCreateRequest accountCreateRequest);
    ResponseEntity becomeSeller(int id, BecomeSellerRequest becomeSellerRequest);
    ResponseEntity<ISeller> getSellerInfo(int id);

}
