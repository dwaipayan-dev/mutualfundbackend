package com.mutualfunds.backend.mutualfundapi.services;

import com.mutualfunds.backend.mutualfundapi.constants.AppProperties;
import com.mutualfunds.backend.mutualfundapi.constants.JsonConstants;
import com.mutualfunds.backend.mutualfundapi.daos.PaymentDAO;
import com.mutualfunds.backend.mutualfundapi.dto.CheckPaymentDTO;
import com.mutualfunds.backend.mutualfundapi.dto.PaymentResponseDTO;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Fund;
import com.mutualfunds.backend.mutualfundapi.pojo.entity.Payment;
import com.mutualfunds.backend.mutualfundapi.repositories.FundStrategyRepository;
import com.mutualfunds.backend.mutualfundapi.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserService userService;

    private final PaymentManager paymentManager;

    private final FundStrategyRepository fundStrategyRepository;

    private final OrderService orderService;

    private final AppProperties appProperties;

    public PaymentResponseDTO getPaymentLink(PaymentDAO paymentInfo) {
        try {
            PaymentResponseDTO paymentResponse = paymentManager.createPaymentCall(paymentInfo);
            savePayment(paymentResponse, paymentInfo);
            return paymentResponse;
        } catch (Exception e) {
            log.error("Error in getting payment link");
            return PaymentResponseDTO.genericFailureResponse("ERROR: " + e.getMessage());
        }
    }

    private Payment savePayment(PaymentResponseDTO paymentResponse, PaymentDAO paymentInfo){
        String link = paymentResponse.getPaymentLink();
        String[] tokens = link.split("/");
        return paymentRepository.save(Payment
                .builder()
                .userId(userService.currentUser().getId())
                .status(Payment.TransactionStatus.PENDING)
                .amount(paymentInfo.getAmount().doubleValue())
                .productId(paymentInfo.getProductId())
                .transactionType(Payment.TransactionType.CREDIT)
                .transactionId(tokens[tokens.length - 1])
                .build());
    }

    public boolean checkPayment() throws HttpClientErrorException, HttpServerErrorException, IOException {
        // Fetch user id
        Long userId = userService.currentUser().getId();
        // Get all pending payments from user
        List<Payment> pendingPayments = paymentRepository.findByUserIdAndStatusAndTransactionType(userId,
                Payment.TransactionStatus.PENDING, Payment.TransactionType.CREDIT);
        // Check Payment status
        List<Payment> completedPayments = new ArrayList<>();
        for (Payment item : pendingPayments) {
            boolean success = checkPaymentStatus(item);
            CompletableFuture.runAsync(() -> updateTransactionStatus(item, success));
            if (success) {
                completedPayments.add(item);
            }
        }
        if (completedPayments.size() == 0) {
            return false;
        }

        completedPayments.stream().parallel().forEach(item -> {
            List<Fund> fundList = fundStrategyRepository.findFundsByStrategyId(item.getProductId());
            // For each fund place order
            CompletableFuture.runAsync(() -> orderService.placeOrders(userId, item, fundList));
        });
        return true;
    }

    private boolean checkPaymentStatus(Payment item) throws IOException {
        boolean success = false;
        int retries = appProperties.getGlobalRetryCount();
        while (retries > 0) {
            try {
                CheckPaymentDTO checkPayment = paymentManager.getPaymentCall(item.getTransactionId());
                if (checkPayment.getStatus().equals("Success")) {
                    success = true;
                    break;
                }
            } catch (Exception e) {
                log.error("Failed payment attempt. Message {}", e.getMessage());
            } finally {
                retries--;
            }
        }
        return success;
    }

    private void updateTransactionStatus(Payment item, boolean success) {
        if (!success) {
            item.setStatus(Payment.TransactionStatus.FAILED);
        } else {
            item.setStatus(Payment.TransactionStatus.COMPLETE);
        }
        paymentRepository.save(item);
    }
}
