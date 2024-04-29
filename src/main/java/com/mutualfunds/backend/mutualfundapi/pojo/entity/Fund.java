package com.mutualfunds.backend.mutualfundapi.pojo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "fund")
@NoArgsConstructor 
@Getter
@Setter
public class Fund extends AuditingEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "percentage")
    private Integer percentage;
}
