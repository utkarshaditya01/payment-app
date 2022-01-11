package finalgroup.services;

import finalgroup.entity.Wallet;

import java.util.List;

public interface WalletService {

    public List<Wallet> getWallets();
    public Wallet getWalletById(int id);
    public Wallet saveWallet(Wallet wallet);
    public String deleteWallet(int id);
    public Wallet updateWallet(int id, Wallet newWallet);
}
