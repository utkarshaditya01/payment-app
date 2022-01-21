package finalgroup.controller;

import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.services.TemporalWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/temporal")
@RestController
public class WalletTemporalController {

    Logger logger = LoggerFactory.getLogger(WalletTemporalController.class);

    @Autowired
    TemporalWalletService walletService;

    @GetMapping("/wallets/{id}")
    public String retrieveWallet(@PathVariable int id){
        walletService.getWalletWorkflow(id);
        return "Wallet get";
    }

    @PostMapping("/wallets")
    public String createWallet(@RequestBody WalletRequestBodyDTO walletDTO){
        walletService.createWalletWorkFlow(walletDTO);
        return "Wallet Created";
    }

    @PutMapping("/wallets/{id}")
    public String updateWalletData(@PathVariable int id, @RequestBody WalletRequestBodyDTO walletDTO){
        walletService.updateWalletWorkflow(id, walletDTO);
        return "Wallet updated";
    }

    @DeleteMapping("/wallets/{id}")
    public String deleteWalletData(@PathVariable int id){
        walletService.deleteWalletWorkflow(id);
        return "Wallet Deleted";
    }
}
