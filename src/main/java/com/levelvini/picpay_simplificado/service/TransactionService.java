package com.levelvini.picpay_simplificado.service;


import com.levelvini.picpay_simplificado.dtos.TransactionDTO;
import com.levelvini.picpay_simplificado.model.User;
import com.levelvini.picpay_simplificado.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    TransactionRepository repository;

    UserService service;

    RestTemplate template = new RestTemplate();

    public TransactionService(TransactionRepository repository,UserService service) {
        this.repository = repository;
        this.service = service;
    }

    public void createTransaction(TransactionDTO transaction){
        User sender = (service.findUserById(transaction.senderId()));
        User receiver = (service.findUserById(transaction.receiverId()));
        service.validateTransaction(sender,transaction.value());
    }

    public boolean autorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> response = template.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return false;
        }
        Map body= response.getBody();
        Map<String,Object> data = (Map<String, Object>) body.get("data");
        return (Boolean) data.get("authorization");
    }
}
