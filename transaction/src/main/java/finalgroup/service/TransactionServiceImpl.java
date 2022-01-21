package finalgroup.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import finalgroup.DTO.RequestServerTransactionDTO;
import finalgroup.DTO.TransactionRequestBodyDTO;
import finalgroup.entity.Transaction;
import finalgroup.entity.Wallet;
import finalgroup.enums.State;
import finalgroup.enums.Type;
import finalgroup.exception.TransactionDeclinedException;
import finalgroup.exception.TransactionNotFoundException;
import finalgroup.repository.TransactionRepository;
import finalgroup.services.WalletServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    Producer producer;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletServiceImpl walletService;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(int id) {
        System.out.println("Database accessed");
        Optional<Transaction> optOldTransaction = transactionRepository.findById(id);
        if(!optOldTransaction.isPresent()){
            throw new TransactionNotFoundException("id:"+id);
        }
        return optOldTransaction.get();
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public String deleteTransaction(int id) {
        transactionRepository.deleteById(id);
        return "Transaction Deleted : " + id;
    }

    @Override
    public Transaction updateTransaction(int id, Transaction newTransaction) {
        Optional<Transaction> optOldTransaction = transactionRepository.findById(id);
        if(!optOldTransaction.isPresent()){
            throw new TransactionNotFoundException("id:"+id);
        }
        Transaction oldTransaction = optOldTransaction.get();

        // Allow changes only before the transaction is accepted.
        if(oldTransaction.getState() != State.INITIATED){
            throw new TransactionDeclinedException("Transaction is already " + oldTransaction.getState());
        }

        // Transaction only updated if balance is more than withdraw amount.
        if(newTransaction.getType() == Type.WITHDRAW &&
                oldTransaction.getWallet().getBalance() < newTransaction.getAmount()){
            throw new TransactionDeclinedException("Balance : " + oldTransaction.getWallet().getBalance() + " | Amount: " +
                    newTransaction.getAmount() + " | Transaction didn't update.");
        }

        oldTransaction.setAmount(newTransaction.getAmount());
        oldTransaction.setType(newTransaction.getType());
        return transactionRepository.save(oldTransaction);
    }

    public Transaction saveTransactionDTO(int id, TransactionRequestBodyDTO transactionRequestBodyDTO) throws JsonProcessingException{
        Wallet wallet = walletService.getWalletById(id);

        // Transaction only created if balance is more than withdrawal amount.
        if(transactionRequestBodyDTO.getType() == Type.WITHDRAW && wallet.getBalance()<transactionRequestBodyDTO.getAmount()){
            throw new TransactionDeclinedException("Balance : " + wallet.getBalance() + " | Amount: " + transactionRequestBodyDTO.getAmount() + " | Transaction Failed.");
        }
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(transactionRequestBodyDTO.getAmount());
        transaction.setState(State.INITIATED);
        transaction.setType(transactionRequestBodyDTO.getType());
        Transaction transactionKafka = saveTransaction(transaction);
        producer.publishTransaction(transactionKafka);
        return transactionKafka;
    }

    public Transaction updateTransactionDTO(int id, TransactionRequestBodyDTO transactionRequestBodyDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestBodyDTO.getAmount());
        transaction.setType(transactionRequestBodyDTO.getType());
        return updateTransaction(id, transaction);
    }

    public void requestFromServer(RequestServerTransactionDTO requestServerTransactionDTO){
        Transaction transaction = getTransactionById(requestServerTransactionDTO.getTransactionId());

        if(transaction.getState() != State.INITIATED){
            throw new TransactionDeclinedException("Transaction is already " + transaction.getState());
        }

        if(requestServerTransactionDTO.getState() == State.COMPLETED){

            if(transaction.getType() == Type.DEPOSIT){
                transaction.getWallet().setBalance(
                        transaction.getWallet().getBalance() + transaction.getAmount()
                );
            }

            if(transaction.getType() == Type.WITHDRAW){
                transaction.getWallet().setBalance(
                        transaction.getWallet().getBalance() - transaction.getAmount()
                );
            }

            //cached this
            walletService.updateBalance(transaction.getWallet());
            transaction.setState(State.COMPLETED);
        }

        if(requestServerTransactionDTO.getState() == State.FAILED){
            transaction.setState(State.FAILED);
        }

        transactionRepository.save(transaction);
    }

}

