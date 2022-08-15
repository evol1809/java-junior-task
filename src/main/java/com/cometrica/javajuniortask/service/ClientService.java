package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.repository.ClientRepository;

import com.cometrica.javajuniortask.repository.DebtRepository;
import com.cometrica.javajuniortask.repository.PaymentRepository;
import com.cometrica.javajuniortask.validation.ClientDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private  final DebtRepository debtRepository;
    private final PaymentRepository paymentRepository;
    private final ClientDtoValidator clientDtoValidator;

    @Transactional
    public Iterable<ClientDTO> getAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).map(client -> {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(client.getName());
            clientDTO.setId(client.getId());

            BigDecimal debtSum = debtRepository.getValueSum(client.getId()).orElse(BigDecimal.ZERO);
            BigDecimal paymentSum = paymentRepository.getValueSum(client.getId()).orElse(BigDecimal.ZERO);
            clientDTO.setTotalDebt(debtSum.add(paymentSum.negate()));
            return clientDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public UUID addClient(ClientDTO clientDTO) {
        toUpperCaseOfFirstSymbol(clientDTO);
        clientDtoValidator.validate(clientDTO);
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setId(UUID.randomUUID());
        return clientRepository.save(client).getId();
    }

    private void toUpperCaseOfFirstSymbol(ClientDTO clientDTO) {
        String name = clientDTO.getName();
        if(name.length() > 0)
            clientDTO.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
    }
}
