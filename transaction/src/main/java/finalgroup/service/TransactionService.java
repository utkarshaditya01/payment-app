package finalgroup.service;

import finalgroup.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    Transaction getTransactionById(int id);
    Transaction saveTransaction(Transaction transaction);
    String deleteTransaction(int id);
    Transaction updateTransaction(int id, Transaction transaction);
}

