package individualProjectAleksandarPenev.BuyersParadise.controller;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IAccountDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccountService;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOfferService;
import individualProjectAleksandarPenev.BuyersParadise.model.request.DeleteOfferRequest;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import individualProjectAleksandarPenev.BuyersParadise.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/offer")
public class OffersController {

    @Autowired
    IOfferService offerService;
    @Autowired
    IAccountService accountService;

//    @GetMapping
//    public ResponseEntity<List<IOffer>> GetAllOffers()
//    {
//        return offerService.ReturnAllOffers();
//    }

    @GetMapping
    public CompletableFuture<ResponseEntity> GetAllOffers()
    {
        return offerService.returnAllOffers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IOffer> GetOfferByID(@PathVariable(value = "id") int id)
    {
        return offerService.ReturnOfferByID(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> CreateOffer(@RequestBody OfferCreateRequest offerCreateRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        int accountId = accountService.getAccountIDByUsername(currentPrincipalName);

        int newOfferID = offerService.CreateOffer(offerCreateRequest, accountId);

        return ResponseEntity.ok().body(newOfferID);

    }

//    @PostMapping("/edit")
//    public ResponseEntity editOffer() {
//
//
//
//        return ResponseEntity.ok().build();
//    }


    @PostMapping(value="/delete")
    public ResponseEntity deleteOffer(@RequestBody DeleteOfferRequest deleteOfferRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        int accountId = accountService.getAccountIDByUsername(currentPrincipalName);

        offerService.deleteOffer(accountId, deleteOfferRequest.getOfferID());

        return ResponseEntity.ok().build();

    }

}
