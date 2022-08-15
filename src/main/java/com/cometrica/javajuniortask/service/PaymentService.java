package com.cometrica.javajuniortask.service;

import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.model.Client;
import com.cometrica.javajuniortask.model.Payment;
import com.cometrica.javajuniortask.repository.ClientRepository;
import com.cometrica.javajuniortask.repository.PaymentRepository;
import com.cometrica.javajuniortask.validation.PaymentDtoValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final PaymentDtoValidator paymentDtoValidator;

    @Transactional
    public UUID addPayment(PaymentDTO paymentDTO) {

        paymentDtoValidator.validate(paymentDTO);

        Client client = clientRepository.findById(paymentDTO.getClientId()).orElseThrow(ClientNotFoundException::new);

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setValue(paymentDTO.getValue());
        payment.setClient(client);
        return paymentRepository.save(payment).getId();
    }

    public Iterable<PaymentDTO> getAllPayment(UUID clientId) {
        if (clientId == null)
            throw new ClientNotFoundException();

        clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);

        return StreamSupport.stream(paymentRepository.findAllByClientId(clientId).spliterator(), false).map(payment -> {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setClientId(payment.getClient().getId());
            paymentDTO.setId(payment.getId());
            paymentDTO.setValue(payment.getValue());
            return paymentDTO;
        }).collect(Collectors.toList());
    }
}
