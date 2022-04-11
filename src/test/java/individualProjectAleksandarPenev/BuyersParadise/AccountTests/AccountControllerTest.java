package individualProjectAleksandarPenev.BuyersParadise.AccountTests;

import individualProjectAleksandarPenev.BuyersParadise.controller.AccountController;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import individualProjectAleksandarPenev.BuyersParadise.model.Account;
import individualProjectAleksandarPenev.BuyersParadise.model.Seller;
import individualProjectAleksandarPenev.BuyersParadise.model.request.AccountCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.BecomeSellerRequest;
import individualProjectAleksandarPenev.BuyersParadise.repository.AccountDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @MockBean
    AccountService accountService;


    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void getAllPersonalInfoTest() throws NullPointerException {

        ISeller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        when(accountService.getAccountIDByUsername("name"))
                .thenReturn(seller.getId());

        when(accountService.getSellerInfo(1))
                .thenReturn(ResponseEntity.ok().body(seller));

        Assertions.assertEquals(ResponseEntity.ok().body(seller), accountController.getAllPersonalInfo());

    }

    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void getAllPersonalInfoFailTest() throws NullPointerException {

        ISeller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        when(accountService.getAccountIDByUsername("name"))
                .thenReturn(seller.getId());

        when(accountService.getSellerInfo(1))
                .thenReturn(ResponseEntity.badRequest().build());

        Assertions.assertEquals(ResponseEntity.badRequest().build(), accountController.getAllPersonalInfo());

    }

    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void getSellerInfoTest() throws NullPointerException {

        ISeller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());


        when(accountService.getSellerInfo(1))
                .thenReturn(ResponseEntity.ok().body(seller));

        Assertions.assertEquals(ResponseEntity.ok().body(seller), accountController.getSellerInfo(1));

    }

    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void becomeSellerTest() throws NullPointerException {

        ISeller seller = new Seller(1,"name","jsd","hshja", "USER", "baba ti", "shapkata", "asdasd", new ArrayList<IOffer>());

        Account user = new Account(1,"name","jsd","hshja", "USER");

        BecomeSellerRequest request = new BecomeSellerRequest();
        request.setFirstName("baba ti");
        request.setLastName("shapkata");
        request.setPhoneNumber("asdasd");

        when(accountService.getAccountIDByUsername("name"))
                .thenReturn(seller.getId());

        when(accountService.becomeSeller(1, request))
                .thenReturn(ResponseEntity.ok().build());

        Assertions.assertEquals(ResponseEntity.ok().build(), accountController.becomeSeller(request));

    }

    @Test
    @WithMockUser(username = "name", roles = {"USER"})
    void checkIfRoleIsUserTest() throws NullPointerException {

        Assertions.assertNotEquals(ResponseEntity.ok().build(), accountController.checkIfRoleIsUSER());

    }

    @Test
    @WithMockUser(username = "name", roles = {"SELLER"})
    void checkIfRoleIsUserFailTest() throws NullPointerException {

        Assertions.assertEquals(ResponseEntity.badRequest().build(), accountController.checkIfRoleIsUSER());

    }

    @Test
    void userRegistrationTest() throws NullPointerException {

        IAccount account = new Account();

        account.setId(1);
        account.setUsername("baubau");
        account.setEmail("baubau");
        account.setPassword("baubau");

        AccountCreateRequest userCreateRequest = new AccountCreateRequest();
        userCreateRequest.setUsername(account.getUsername());
        userCreateRequest.setEmail(account.getEmail());
        userCreateRequest.setPassword(account.getPassword());

        when(accountService.UserRegistration(userCreateRequest)).thenReturn(ResponseEntity.ok().build());

        Assertions.assertEquals(ResponseEntity.ok().build(), accountController.userRegistration(userCreateRequest));

    }



}
