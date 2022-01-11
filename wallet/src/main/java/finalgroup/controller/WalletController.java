package finalgroup.controller;

import java.net.URI;
import java.util.List;

import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.entity.Wallet;
import finalgroup.services.WalletServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class WalletController {

    Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletServiceImpl walletService;

    @GetMapping("/wallets")
    public List<Wallet> retrieveAllWallets(){
        return walletService.getWallets();
    }

    @GetMapping("/wallets/{id}")
    public Wallet retrieveWallet(@PathVariable int id){
        return walletService.getWalletById(id);
    }

    @PostMapping("/wallets")
    public ResponseEntity<Object> createWallet(@RequestBody WalletRequestBodyDTO walletDTO){
        Wallet wallet = walletService.saveWalletDTO(walletDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(wallet.getId()).toUri();
        logger.info("Wallet Created : " + wallet.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<Object> updateWalletData(@PathVariable int id, @RequestBody WalletRequestBodyDTO walletDTO){
        return new ResponseEntity<>(walletService.updateWalletDTO(id, walletDTO), HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public String deleteWalletData(@PathVariable int id){
        return walletService.deleteWallet(id);
    }
}
