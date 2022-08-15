package com.cometrica.javajuniortask.controller.rest;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity addClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity(clientService.addClient(clientDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAllClients() {
        return new ResponseEntity(clientService.getAllClients(), HttpStatus.OK);
    }
}
