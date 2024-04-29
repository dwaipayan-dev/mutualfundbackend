package com.mutualfunds.backend.mutualfundapi.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.mutualfunds.backend.mutualfundapi.mapper.OrderFundJoinMapper;
import org.springframework.stereotype.Service;

import com.mutualfunds.backend.mutualfundapi.constants.JsonConstants;
import com.mutualfunds.backend.mutualfundapi.daos.CreateOrderDAO;
import com.mutualfunds.backend.mutualfundapi.dto.CreateOrderDTO;
import com.mutualfunds.backend.mutualfundapi.dto.OrderDTO;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Fund;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Order;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Payment;
import com.mutualfunds.backend.mutualfundapi.pojo.joins.OrderFundJoin;
import com.mutualfunds.backend.mutualfundapi.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final RTAService rtaManagerService;

    public List<OrderFundJoin> getAllOrdersByUserId(Long userId) throws SQLException{
        try {
            return OrderFundJoinMapper.objectListToOrderFundJoinList(orderRepository.findOrdersJoinFundsByUserId(userId));
        } catch (Exception e) {
            log.error("Failed to fetch  orders by user id", e);
            throw new SQLException("Failed to fetch orders");
        }
    }

    public void placeOrders(Long userId, Payment item, List<Fund> fundList) {
        for(Fund fund: fundList) {
            try {
                Integer percentage = fund.getPercentage();
                Double totalAmountInvested = item.getAmount();
                CreateOrderDAO createOrder = CreateOrderDAO.builder().amount(totalAmountInvested * percentage * 0.01).paymentID(item.getTransactionId()).fund(fund.getName()).build();
                String jsonBody = JsonConstants.OBJECT_MAPPER.writeValueAsString(createOrder);
                String response = rtaManagerService.createOrder(jsonBody);
                CreateOrderDTO createdOrder= JsonConstants.OBJECT_MAPPER.readValue(response, CreateOrderDTO.class);
                saveOrder(userId, fund, createdOrder);
            } catch (IOException e) {
                log.error("Error while creating order. Message {}", e.getMessage());
            }
        }
    }

    private void saveOrder(Long userId, Fund fund, CreateOrderDTO createdOrder) {
        orderRepository.save(Order.builder().amount(createdOrder.getData().getAmount())
                .orderId(createdOrder.getData().getId())
                .productId(fund.getId())
                .units(createdOrder.getData().getUnits())
                .userId(userId)
                .status(Order.TransactionStatus.SUBMITTED)
                .build());
    }

    public void updateOrder(Order order, OrderDTO fetchedOrder, Order.TransactionStatus newStatus) {
        order.setUnits(fetchedOrder.getUnits());
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    public List<Order> fetchOrdersByStatus(Order.TransactionStatus status){
        return orderRepository.findByStatus(status);
    }
}
