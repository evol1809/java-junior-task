package com.cometrica.javajuniortask.controller.rest;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.service.DebtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/debts")
public class DebtController {

    private final DebtService debtService;

    @GetMapping("/{id}")
    public ResponseEntity getAllDebts(@PathVariable("id") String id) {
        return new ResponseEntity(this.debtService.getAllDebts(UUID.fromString(id)), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity addDebt(@RequestBody DebtDTO debtDTO) {
        return new ResponseEntity(debtService.addDebt(debtDTO), HttpStatus.CREATED);
    }


}
