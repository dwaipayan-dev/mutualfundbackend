package com.mutualfunds.backend.mutualfundapi.pojo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fund_strategy")
@NoArgsConstructor
@Getter
@Setter
public class FundStrategy extends AuditingEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fund_strategy_id")
    private List<Fund> funds;
}
