package com.mutualfunds.backend.mutualfundapi.repositories;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o, f, fs FROM FundStrategy fs INNER JOIN fs.funds f INNER JOIN Order o ON f.id = o.productId WHERE o.userId = :userId AND o.status IN ('SUCCEEDED', 'FAILED')")
    public List<Object[]> findOrdersJoinFundsByUserId(Long userId);

    public List<Order> findByStatus(Order.TransactionStatus status);
}
