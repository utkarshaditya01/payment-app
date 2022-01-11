package finalgroup.service;

import finalgroup.entity.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getTransactions();
    public Transaction getTransactionById(int id);
    public Transaction saveTransaction(Transaction transaction);
    public String deleteTransaction(int id);
    public Transaction updateTransaction(int id, Transaction transaction);
}

