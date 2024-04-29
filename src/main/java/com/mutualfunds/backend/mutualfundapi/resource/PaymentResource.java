package com.mutualfunds.backend.mutualfundapi.resource;

import com.mutualfunds.backend.mutualfundapi.daos.PaymentDAO;
import com.mutualfunds.backend.mutualfundapi.dto.PaymentResponseDTO;
import com.mutualfunds.backend.mutualfundapi.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment-gateway")
@RequiredArgsConstructor
@Slf4j
public class PaymentResource {

    private final PaymentService paymentService;

    @PostMapping("/link")
    public ResponseEntity<PaymentResponseDTO> getPaymentLink(@RequestBody PaymentDAO paymentInfo) {
        return ResponseEntity.ok(paymentService.getPaymentLink(paymentInfo));
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<Boolean> confirmPayment() {
        try {
            boolean result = paymentService.checkPayment();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Failed to confirm payment" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

}
