package com.mutualfunds.backend.mutualfundapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutualfunds.backend.mutualfundapi.dto.HoldingsDTO;
import com.mutualfunds.backend.mutualfundapi.services.HoldingsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class RTAResource {

    private final HoldingsService holdingsService;

    @GetMapping("/getPortfolio")
    public ResponseEntity<HoldingsDTO> getFundHoldings() {
        try {
            HoldingsDTO response = holdingsService.calculateHoldingsDTO();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Failed to fetch portfolio" + e.getMessage());
            HoldingsDTO errorResponse = HoldingsDTO.genericFailureResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
