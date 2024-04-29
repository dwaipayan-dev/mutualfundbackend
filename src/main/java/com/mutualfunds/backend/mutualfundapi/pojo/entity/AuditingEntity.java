package com.mutualfunds.backend.mutualfundapi.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;


    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;
}
