package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.exeption.ValueNotValidException;
import com.cometrica.javajuniortask.repository.DebtRepository;
import com.cometrica.javajuniortask.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class PaymentDtoValidatorTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    DebtRepository debtRepository;

    PaymentDTO paymentDTO;

    @BeforeEach
    void init() {
        paymentDTO = new PaymentDTO();
        paymentDTO.setClientId(UUID.randomUUID());
    }

    @Test
    @DisplayName("The payment less than 0")
    void paymentLessZero() {
        paymentDTO.setValue(BigDecimal.valueOf(-10));

        assertThatThrownBy(() -> {
            new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO);
        }).hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The payment is 0")
    void paymentZero() {
        paymentDTO.setValue(BigDecimal.valueOf(0));

        assertThatThrownBy(() -> {
            new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO);
        }).hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The payment is null")
    void paymentNull() {
        paymentDTO.setValue(null);

        assertThatThrownBy(() -> {
            new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO);
        }).hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The client id is null")
    void clientNull() {
        paymentDTO.setValue(BigDecimal.valueOf(1));
        paymentDTO.setClientId(null);

        assertThatThrownBy(() -> {
            new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO);
        }).hasMessage(new ClientNotFoundException().getMessage());
    }

    @Test
    @DisplayName("The payment more debt")
    void paymentMoreDebt() {

        paymentDTO.setValue(BigDecimal.valueOf(10));

        Mockito.when(paymentRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.ZERO));

        Mockito.when(debtRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(1)));

        assertThatThrownBy(() -> {
            new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO);
        }).hasMessage("The payment is more than the debt");
    }

    @Test
    @DisplayName("The payment is valid, when payment = 10 debt = 100")
    void paymentValid1() {

        paymentDTO.setValue(BigDecimal.valueOf(10));

        Mockito.when(paymentRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.ZERO));

        Mockito.when(debtRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(100)));

        assertDoesNotThrow(() -> new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO));
    }

    @Test
    @DisplayName("The payment is valid,  when payment = 10 debt = 10")
    void paymentValid2() {

        paymentDTO.setValue(BigDecimal.valueOf(10));

        Mockito.when(paymentRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.ZERO));

        Mockito.when(debtRepository.getValueSum(paymentDTO.getClientId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(10)));

        assertDoesNotThrow(() -> new PaymentDtoValidator(paymentRepository, debtRepository).validate(paymentDTO));
    }
}
