package com.mutualfunds.backend.mutualfundapi.resource;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.FundStrategy;
import com.mutualfunds.backend.mutualfundapi.services.FundStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fund-strategy")
@RequiredArgsConstructor
public class FundStrategyResource {

    private final FundStrategyService fundStrategyService;

    @GetMapping("/fetch-all")
    public ResponseEntity<List<FundStrategy>> getAllFundStrategies(){
        return new ResponseEntity<>(fundStrategyService.getAllStrategies(), HttpStatus.OK);
    }
}
