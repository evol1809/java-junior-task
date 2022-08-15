package com.cometrica.javajuniortask.controller.rest;

import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity addPayment(@RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity(paymentService.addPayment(paymentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllPayments(@PathVariable("id") String id) {
        return new ResponseEntity(paymentService.getAllPayment(UUID.fromString(id)), HttpStatus.FOUND);
    }
}
