package finalgroup.temporal;

import finalgroup.DTO.SimpleResponseDTO;
import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.entity.Wallet;
import finalgroup.services.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityImpl implements Activity {

    @Autowired
    WalletServiceImpl walletService;

    @Override
    public SimpleResponseDTO createWalletActivity(WalletRequestBodyDTO walletDTO) {
        Wallet wallet = walletService.saveWalletDTO(walletDTO);
        SimpleResponseDTO responseDTO = new SimpleResponseDTO("Failed", wallet.getId());
//        if(responseDTO.getResponse().equals("Failed")){
//            throw new RuntimeException();
//        }
        sleep(5);
        return responseDTO;
    }

    @Override
    public Wallet getWalletActivity(Integer id) {
        sleep(5);
        return walletService.getWalletById(id);
    }

    @Override
    public void updateWalletActivity(Integer id, WalletRequestBodyDTO walletDTO) {
        sleep(5);
        walletService.updateWalletDTO(id, walletDTO);
    }

    @Override
    public void deleteWalletActivity(Integer id) {
        sleep(5);
        walletService.deleteWallet(id);
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ee) {
            // Empty
        }
    }
}
