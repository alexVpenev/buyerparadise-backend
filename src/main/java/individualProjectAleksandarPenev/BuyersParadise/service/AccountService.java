package individualProjectAleksandarPenev.BuyersParadise.service;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IAccountDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccountService;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import individualProjectAleksandarPenev.BuyersParadise.model.Account;
import individualProjectAleksandarPenev.BuyersParadise.model.request.AccountCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.BecomeSellerRequest;
import individualProjectAleksandarPenev.BuyersParadise.repository.AccountDalJDBC;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    @Autowired
    private final IAccountDAL dal;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public IAccount GetAccountByUsername(String username) {
        return dal.getAccountByUsername(username);
    }

    @Override
    public int getAccountIDByUsername(String username) {
        return dal.getAccountIDByUsername(username);
    }

    @Override
    public ResponseEntity UserRegistration(AccountCreateRequest accountCreateRequest) {
        IAccount account;
        Optional<IAccount> byUsername = Optional.ofNullable(dal.getAccountByUsername(accountCreateRequest.getUsername()));
        if (byUsername.isPresent()) {

            throw new RuntimeException("User already registered. Please use different username.");

        }



        //im not sure if this will constantly work NB!!!
        boolean bool = dal.addAccount(accountCreateRequest.getUsername(), accountCreateRequest.getEmail(), passwordEncoder.encode(accountCreateRequest.getPassword()));

        if(bool) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity becomeSeller(int id, BecomeSellerRequest becomeSellerRequest) {

        dal.becomeSeller(id, becomeSellerRequest.getFirstName(), becomeSellerRequest.getLastName(), becomeSellerRequest.getPhoneNumber());

        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<ISeller> getSellerInfo(int id) {
        ISeller seller = dal.getAllSellersInfo(id);

        if(seller != null) {
            return ResponseEntity.ok().body(seller);
        }else {
            return ResponseEntity.notFound().build();
        }


    }

}
