package com.levelvini.picpay_simplificado.service;

import com.levelvini.picpay_simplificado.dtos.UserDTO;
import com.levelvini.picpay_simplificado.exceptions.InvalidUserTypeException;
import com.levelvini.picpay_simplificado.exceptions.ResourceNotFoundException;
import com.levelvini.picpay_simplificado.model.User;
import com.levelvini.picpay_simplificado.model.enums.UserType;
import com.levelvini.picpay_simplificado.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void validateTransaction(User sender, BigDecimal amount){
        if (sender.getUserType() == UserType.MERCHANTS){
            throw new InvalidUserTypeException("Usuario do tipo logista não está autorizado a realizar transações");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new ArithmeticException("saldo insuficiente");
        }
    }
    @Transactional
    public User findUserById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }

    @Transactional
    public List<User> findAllUsers(){
        return repository.findAll();
    }

    @Transactional
    public User createUser(UserDTO data){
        User saveUser = new User(data);
        return repository.save(saveUser);
    }

    @Transactional
    public void saveUser(User user){
        repository.save(user);
    }
}
