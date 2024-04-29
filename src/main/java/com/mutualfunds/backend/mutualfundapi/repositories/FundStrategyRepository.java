package com.mutualfunds.backend.mutualfundapi.repositories;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.Fund;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.FundStrategy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FundStrategyRepository extends JpaRepository<FundStrategy, Long> {

    @Query("SELECT f FROM FundStrategy fs INNER JOIN fs.funds f WHERE fs.id = :strategyId")
    public List<Fund> findFundsByStrategyId(Long strategyId);
}
