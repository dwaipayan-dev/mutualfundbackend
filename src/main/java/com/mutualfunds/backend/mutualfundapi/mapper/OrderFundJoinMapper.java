package com.mutualfunds.backend.mutualfundapi.mapper;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.Fund;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.FundStrategy;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Order;
import com.mutualfunds.backend.mutualfundapi.pojo.joins.OrderFundJoin;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class OrderFundJoinMapper {

    private OrderFundJoinMapper(){}

    public static List<OrderFundJoin> objectListToOrderFundJoinList(List<Object[]> objectList){
        return objectList
                .stream()
                .map(OrderFundJoinMapper::objectArrToOrderFundJoin)
                .collect(Collectors.toList());
    }

    public static OrderFundJoin objectArrToOrderFundJoin(Object[] arr){
        if(arr[0] instanceof Order && arr[1] instanceof Fund && arr[2] instanceof FundStrategy){
            OrderFundJoin orderFundJoin = new OrderFundJoin();
            Order order = (Order) arr[0];
            Fund fund = (Fund) arr[1];
            FundStrategy fundStrategy = (FundStrategy) arr[2];
            orderFundJoin.setFundName(fund.getName());
            orderFundJoin.setFundStrategy(fundStrategy.getName());
            orderFundJoin.setInvestedValue(order.getAmount());
            orderFundJoin.setUnits(order.getUnits());
            orderFundJoin.setStatus(order.getStatus().toString());
            return orderFundJoin;
        }
        log.error("Unable to map this instance.");
        return null;
    }
}
