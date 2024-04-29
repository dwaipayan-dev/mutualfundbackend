package com.mutualfunds.backend.mutualfundapi.repositories;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.Payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findByUserIdAndStatusAndTransactionType(Long userId, Payment.TransactionStatus status, Payment.TransactionType type);
}
