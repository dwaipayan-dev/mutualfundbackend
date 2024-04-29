package com.mutualfunds.backend.mutualfundapi.services;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.FundStrategy;
import com.mutualfunds.backend.mutualfundapi.repositories.FundStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundStrategyService {

    private final FundStrategyRepository fundStrategyRepository;

    public List<FundStrategy> getAllStrategies(){
        return fundStrategyRepository.findAll();
    }

    public List<FundStrategy> addStrategies(List<FundStrategy> strategies){
        return fundStrategyRepository.saveAll(strategies);
    }
}
