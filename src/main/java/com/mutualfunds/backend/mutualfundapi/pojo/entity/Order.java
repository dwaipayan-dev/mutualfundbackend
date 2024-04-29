package com.mutualfunds.backend.mutualfundapi.pojo.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends AuditingEntity{

    public enum TransactionStatus{
        SUCCEEDED,
        FAILED,
        SUBMITTED;
    }

    @Column(name = "amount")
    private Double amount;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "units")
    private Double units;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
