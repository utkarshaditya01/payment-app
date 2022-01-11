package finalgroup.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import finalgroup.DTO.WalletRequestBodyDTO;
import finalgroup.entity.Wallet;
import finalgroup.exception.WalletNotFoundException;
import finalgroup.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{

    Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final String hashReference= "walletDb";

    @Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
    private HashOperations<String, Integer, Wallet> hashOperations;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    @Cacheable(value="wallet")
    public List<Wallet> getWallets(){
        logger.info("Database Accessed");
        System.out.println("REDIS" + hashOperations.entries(hashReference));
        return walletRepository.findAll();
    }

    @Override
    @Cacheable(value = "wallet", key = "#id")
    public Wallet getWalletById(int id){
        logger.info("Database Accessed");
        Optional<Wallet> optOldWallet = walletRepository.findById(id);
        if(!optOldWallet.isPresent()){
            throw new WalletNotFoundException("id:"+id);
        }
        System.out.println("REDIS" + hashOperations.get(hashReference, id));
        return optOldWallet.get();
    }

    @Override
    public Wallet saveWallet(Wallet wallet){
        Wallet savedWallet = walletRepository.save(wallet);
        hashOperations.putIfAbsent(hashReference, savedWallet.getId(), savedWallet);
        return savedWallet;
    }

    @Override
    @CacheEvict(value="wallet", key="#id")
    public String deleteWallet(int id){
        walletRepository.deleteById(id);
        hashOperations.delete(hashReference, id);
        return "Wallet deleted : " + id;
    }

    @Override
    public Wallet updateWallet(int id, Wallet newWallet){
        Optional<Wallet> optOldWallet = walletRepository.findById(id);
        if(!optOldWallet.isPresent()){
            throw new WalletNotFoundException("id:"+id);
        }
        Wallet oldWallet = optOldWallet.get();
        oldWallet.setUsername(newWallet.getUsername());
        hashOperations.put(hashReference, oldWallet.getId(), oldWallet);
        return walletRepository.save(oldWallet);
    }

    @CacheEvict(value="wallet", allEntries=true)
    public Wallet saveWalletDTO(WalletRequestBodyDTO createWalletDTO){
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setUsername(createWalletDTO.getUsername());
        return saveWallet(wallet);
    }

    @CachePut(value = "wallet", key = "#id")
    public Wallet updateWalletDTO(int id, WalletRequestBodyDTO createWalletDTO){
        Wallet wallet = new Wallet();
        wallet.setUsername(createWalletDTO.getUsername());
        return updateWallet(id, wallet);
    }

    @CachePut(value = "wallet", key = "#wallet.getId()")
    public Wallet updateBalance(Wallet wallet){
        return walletRepository.save(wallet);
    }
}

