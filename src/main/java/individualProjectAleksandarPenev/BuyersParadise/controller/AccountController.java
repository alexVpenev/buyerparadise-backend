package individualProjectAleksandarPenev.BuyersParadise.controller;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccountService;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import individualProjectAleksandarPenev.BuyersParadise.model.request.AccountCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.BecomeSellerRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.GetSellerInfoRequest;
import individualProjectAleksandarPenev.BuyersParadise.repository.AccountDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping("/register")
    public ResponseEntity userRegistration(@RequestBody AccountCreateRequest accountCreateRequest) {

        return accountService.UserRegistration(accountCreateRequest);

    }

    @GetMapping("/checkRole")
    public ResponseEntity checkIfRoleIsUSER() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalRole = authentication.getAuthorities().iterator().next().getAuthority();


        if(currentPrincipalRole.length() == 4) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }


    }

    @PostMapping("/upgrade")
    public ResponseEntity becomeSeller(@RequestBody BecomeSellerRequest becomeSellerRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        int accountId = accountService.getAccountIDByUsername(currentPrincipalName);

        System.out.println(accountId);

        return accountService.becomeSeller(accountId, becomeSellerRequest);

    }

    @GetMapping("/personalInfo")
    public ResponseEntity<ISeller> getAllPersonalInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        int accountId = accountService.getAccountIDByUsername(currentPrincipalName);

        return accountService.getSellerInfo(accountId);
    }

    @GetMapping("/getSeller/{id}")
    public ResponseEntity<ISeller> getSellerInfo(@PathVariable(value = "id") int id) {

        return accountService.getSellerInfo(id);
    }

}
