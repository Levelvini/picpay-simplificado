package com.levelvini.picpay_simplificado.service;


import com.levelvini.picpay_simplificado.dtos.TransactionDTO;
import com.levelvini.picpay_simplificado.exceptions.AuthorizationException;
import com.levelvini.picpay_simplificado.model.Transaction;
import com.levelvini.picpay_simplificado.model.User;
import com.levelvini.picpay_simplificado.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    TransactionRepository repository;

    UserService service;

    NotificationService notificationService;

    RestTemplate template;

    public TransactionService(TransactionRepository repository,UserService service,RestTemplate template, NotificationService notificationService) {
        this.repository = repository;
        this.service = service;
        this.template = template;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = (service.findUserById(transaction.senderId()));
        User receiver = (service.findUserById(transaction.receiverId()));
        service.validateTransaction(sender,transaction.value());

        boolean isAuthorized = authorizeTransaction(sender,transaction.value());
        if (!isAuthorized){
            throw new AuthorizationException("transaction not authorized");
        }
        Transaction authorizedTransaction = new Transaction();
        authorizedTransaction.setAmount(transaction.value());
        authorizedTransaction.setSender(sender);
        authorizedTransaction.setReceiver(receiver);
        authorizedTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        repository.save(authorizedTransaction);
        service.saveUser(sender);
        service.saveUser(receiver);

        notificationService.sendNotification(sender,"transaction successful");
        notificationService.sendNotification(receiver,"transaction are been received");
        return authorizedTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> response = template.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return false;
        }
        Map body= response.getBody();
        Map<String,Object> data = (Map<String, Object>) body.get("data");
        return (Boolean) data.get("authorization");
    }
}
