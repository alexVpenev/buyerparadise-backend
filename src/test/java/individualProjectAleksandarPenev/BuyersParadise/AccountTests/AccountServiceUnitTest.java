package individualProjectAleksandarPenev.BuyersParadise.AccountTests;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.Account;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import individualProjectAleksandarPenev.BuyersParadise.model.Seller;
import individualProjectAleksandarPenev.BuyersParadise.model.request.AccountCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.BecomeSellerRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.GetSellerInfoRequest;
import individualProjectAleksandarPenev.BuyersParadise.repository.AccountDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.repository.OfferDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.service.AccountService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceUnitTest {

    @Autowired
    AccountService accountService;

    @MockBean
    AccountDalJDBC accountDalJDBC;

    @Test
    void getAccountByUsernameEqualsTest(){

        String username = "name";
        Account user = new Account(10,"name","jsd","hshja", "USER");

        when(accountDalJDBC.getAccountByUsername(username))
                .thenReturn(user);

        Assertions.assertEquals(user.getId(),accountService.GetAccountByUsername(username).getId());

    }

    @Test
    void getAccountByUsernameNotEqualsTest(){

        String username = "name";
        Account user = new Account(10,"name","jsd","hshja", "USER");

        when(accountDalJDBC.getAccountByUsername(username))
                .thenReturn(user);

        Assertions.assertNotEquals(9,accountService.GetAccountByUsername(username).getId());

    }

    @Test
    void getAccountIDByUsernameEqualsTest(){

        String username = "name";
        Account user = new Account(10,"name","jsd","hshja", "USER");

        when(accountDalJDBC.getAccountIDByUsername(username))
                .thenReturn(user.getId());

        Assertions.assertEquals(user.getId(),accountService.getAccountIDByUsername(username));

    }

    @Test
    void getAccountIDByUsernameNotEqualsTest(){

        String username = "name";
        Account user = new Account(10,"name","jsd","hshja", "USER");

        when(accountDalJDBC.getAccountIDByUsername(username))
                .thenReturn(user.getId());

        Assertions.assertNotEquals(9,accountService.getAccountIDByUsername(username));

    }


    @Test
    void UserRegistrationEqualsTest(){

        IAccount account = new Account();

        account.setId(1);
        account.setUsername("baubau");
        account.setEmail("baubau");
        account.setPassword("baubau");



        AccountCreateRequest userCreateRequest = new AccountCreateRequest();
        userCreateRequest.setUsername(account.getUsername());
        userCreateRequest.setEmail(account.getEmail());
        userCreateRequest.setPassword(account.getPassword());

        when(accountDalJDBC.getAccountByUsername(userCreateRequest.getUsername())).thenReturn(null);

        when(accountDalJDBC.addAccount(userCreateRequest.getUsername(), userCreateRequest.getEmail(), userCreateRequest.getPassword())).thenReturn(false);

        Assertions.assertNotEquals(ResponseEntity.ok().build() ,accountService.UserRegistration(userCreateRequest));
    }

    @Test
    void UserRegistrationFailsTest(){

        IAccount account = new Account();

        account.setId(1);
        account.setUsername("baubau");
        account.setEmail("baubau");
        account.setPassword("baubau");



        AccountCreateRequest userCreateRequest = new AccountCreateRequest();
        userCreateRequest.setUsername(account.getUsername());
        userCreateRequest.setEmail(account.getEmail());
        userCreateRequest.setPassword(account.getPassword());

        when(accountDalJDBC.getAccountByUsername(userCreateRequest.getUsername())).thenReturn(null);

        when(accountDalJDBC.addAccount(userCreateRequest.getUsername(), userCreateRequest.getEmail(), userCreateRequest.getPassword())).thenReturn(false);

        Assertions.assertEquals(ResponseEntity.badRequest().build() ,accountService.UserRegistration(userCreateRequest));
    }

    @Test
    void becomeSellerEqualsTest (){

        Seller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        Account user = new Account(1,"name","jsd","hshja", "USER");

        BecomeSellerRequest request = new BecomeSellerRequest();
        request.setFirstName("baba ti");
        request.setLastName("shapkata");
        request.setPhoneNumber("asdasd");

        accountDalJDBC.becomeSeller(1,  "USER", "baba ti", "shapkata");

        when(accountDalJDBC.addAccount("name","jsd","hshja")).thenReturn(true);


        Assertions.assertNotEquals(ResponseEntity.ok().body(seller),accountService.getSellerInfo(1));

    }

    @Test
    void getSellerInfoTest() {

        Seller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        GetSellerInfoRequest request = new GetSellerInfoRequest();
        request.setAccountId(1);

        when(accountDalJDBC.getAllSellersInfo(request.getAccountId())).thenReturn(seller);

        Assertions.assertEquals(ResponseEntity.ok().body(seller),accountService.getSellerInfo(1));
    }

    @Test
    void getSellerInfoFailTest() {

        Seller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        GetSellerInfoRequest request = new GetSellerInfoRequest();
        request.setAccountId(1);

        when(accountDalJDBC.getAllSellersInfo(request.getAccountId())).thenReturn(null);

        Assertions.assertEquals(ResponseEntity.notFound().build(),accountService.getSellerInfo(1));
    }


}
