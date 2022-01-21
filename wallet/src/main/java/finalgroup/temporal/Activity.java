package finalgroup.temporal;

import finalgroup.DTO.SimpleResponseDTO;
import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.entity.Wallet;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity {

    @ActivityMethod
    SimpleResponseDTO createWalletActivity(WalletRequestBodyDTO walletDTO);

    @ActivityMethod
    Wallet getWalletActivity(Integer id);

    @ActivityMethod
    void updateWalletActivity(Integer id, WalletRequestBodyDTO walletDTO);

    @ActivityMethod
    void deleteWalletActivity(Integer id);
}

