package com.levelvini.picpay_simplificado.controller;

import com.levelvini.picpay_simplificado.dtos.TransactionDTO;
import com.levelvini.picpay_simplificado.exceptions.AuthorizationException;
import com.levelvini.picpay_simplificado.model.Transaction;
import com.levelvini.picpay_simplificado.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction newTransaction = service.createTransaction(transactionDTO);
        return ResponseEntity.ok(newTransaction);
    }
}
