package com.mutualfunds.backend.mutualfundapi.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mutualfunds.backend.mutualfundapi.dto.HoldingsDTO;
import com.mutualfunds.backend.mutualfundapi.dto.MarketValueDTO;
import com.mutualfunds.backend.mutualfundapi.constants.JsonConstants;
import com.mutualfunds.backend.mutualfundapi.dto.FundDTO;
import com.mutualfunds.backend.mutualfundapi.pojo.joins.OrderFundJoin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HoldingsService {
    
    private final UserService userService;

    private final OrderService orderService;

    private final RTAService rtaManagerService;

    public HoldingsDTO calculateHoldingsDTO() throws Exception{
        //Get current user id
        Long userId = userService.currentUser().getId();
        //Get all orders by user id 
        List<OrderFundJoin> allOrdersByUserId = orderService.getAllOrdersByUserId(userId);
        //Make strategy Map
        HoldingsDTO result = generateHoldingsDTO(allOrdersByUserId);
        
        return result;
    }

    private HoldingsDTO generateHoldingsDTO(List<OrderFundJoin> allOrdersByUserId)
            throws IOException {
        Map<String, Map<String, FundDTO>> strategyMap = new HashMap<>();
        Double totalInvestedValue = 0.0;
        Double totalMarketValue = 0.0;
        for(OrderFundJoin item: allOrdersByUserId) {
            String strategyName = item.getFundStrategy();
            Map<String, FundDTO> fundMap = strategyMap.getOrDefault(strategyName, new HashMap<>());
            String fundName = item.getFundName();
            FundDTO tempFund = fundMap.getOrDefault(fundName, new FundDTO());
            tempFund.setName(item.getFundName());
            if(item.getUnits() < 1 || item.getStatus() == "FAILED")  {
                tempFund.setFailedCount(tempFund.getFailedCount() + 1);
            } else {
                tempFund.setCount(tempFund.getCount() + 1);
            }

            if(tempFund.getInvestedValue() == null) {
                tempFund.setInvestedValue(0.0);
            }
            tempFund.setInvestedValue(tempFund.getInvestedValue() + item.getInvestedValue());
            totalInvestedValue += tempFund.getInvestedValue();
            //Calculate current market value of fund
            if(tempFund.getMarketValue() == null) {
                tempFund.setMarketValue(0.0);
            }
            String jsonResponse = rtaManagerService.fetchFundMarketValue(item.getFundName());
            MarketValueDTO response = JsonConstants.OBJECT_MAPPER.readValue(jsonResponse, MarketValueDTO.class);
            Double marketValue = response.getMarketValue();
            tempFund.setMarketValue(tempFund.getMarketValue() + (marketValue * item.getUnits()));
            totalMarketValue += tempFund.getMarketValue();
            fundMap.put(fundName, tempFund);
            strategyMap.put(strategyName, fundMap);
        }
        return HoldingsDTO
                .builder()
                .strategyMap(strategyMap)
                .totalInvestedAmount(totalInvestedValue)
                .totalMarketValue(totalMarketValue)
                .success(true)
                .build();
    }
}
