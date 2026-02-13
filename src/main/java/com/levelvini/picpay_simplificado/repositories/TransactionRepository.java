package com.levelvini.picpay_simplificado.repositories;

import com.levelvini.picpay_simplificado.model.enums.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
