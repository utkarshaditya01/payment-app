package finalgroup.controller;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import finalgroup.DTO.RequestServerTransactionDTO;
import finalgroup.DTO.TransactionRequestBodyDTO;
import finalgroup.entity.Transaction;
import finalgroup.service.TransactionServiceImpl;
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
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("/transactions")
    public List<Transaction> retriveAllTransaction(){
        return transactionService.getTransactions();
    }

    @GetMapping("/transactions/{id}")
    public Transaction retrieveTransaction(@PathVariable int id){
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/transactions/{id}")
    public ResponseEntity<Object> createTransaction(@PathVariable int id, @RequestBody TransactionRequestBodyDTO transactionRequestBodyDTO) throws JsonProcessingException{
        Transaction transaction = transactionService.saveTransactionDTO(id, transactionRequestBodyDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(transaction.getTransactionId()).toUri();
        logger.info("Transaction Created : " + transaction.getTransactionId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Object> updateTransactionData(@PathVariable int id, @RequestBody TransactionRequestBodyDTO transactionRequestBodyDTO){
        return new ResponseEntity<>(transactionService.updateTransactionDTO(id, transactionRequestBodyDTO), HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{id}")
    public String deleteTransactionData(@PathVariable int id){
        return transactionService.deleteTransaction(id);
    }

    @PostMapping("/approve-transaction")
    public void approveTransaction(@RequestBody RequestServerTransactionDTO requestServerTransactionDTO){
        transactionService.requestFromServer(requestServerTransactionDTO);
    }
}
