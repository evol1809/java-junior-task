package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Debt;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.repository.DebtRepository;
import com.cometrica.javajuniortask.validation.DebtDtoValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final DebtRepository debtRepository;
    private final ClientRepository clientRepository;
    private final DebtDtoValidator debtDtoValidator;

    @Transactional
    public UUID addDebt(DebtDTO debtDTO) {

        debtDtoValidator.validate(debtDTO);

        Client client = clientRepository.findById(debtDTO.getClientId()).orElseThrow(ClientNotFoundException::new);

        Debt debt = new Debt();
        debt.setId(UUID.randomUUID());
        debt.setValue(debtDTO.getValue());
        debt.setClient(client);
        return debtRepository.save(debt).getId();
    }

    public Iterable<DebtDTO> getAllDebts(UUID clientId) {

        if (clientId == null)
            throw new ClientNotFoundException();

        clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);

        return StreamSupport.stream(debtRepository.findAllByClientId(clientId).spliterator(), false).map(debt -> {
            DebtDTO debtDTO = new DebtDTO();
            debtDTO.setClientId(clientId);
            debtDTO.setId(debt.getId());
            debtDTO.setValue(debt.getValue());
            return debtDTO;
        }).collect(Collectors.toList());
    }
}
