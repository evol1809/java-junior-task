package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.exeption.ValueNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DebtDtoValidatorTest {

    @Test
    @DisplayName("The value is null")
    void valueEmpty() {
        DebtDTO debtDTO = new DebtDTO();
        assertThatThrownBy(() -> {
            new DebtDtoValidator().validate(debtDTO);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The value is 0")
    void valueZero() {
        DebtDTO debtDTO = new DebtDTO();
        debtDTO.setValue(BigDecimal.ZERO);
        assertThatThrownBy(() -> {
            new DebtDtoValidator().validate(debtDTO);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The value is less than 0")
    void valueLessZero() {
        DebtDTO debtDTO = new DebtDTO();
        debtDTO.setValue(BigDecimal.valueOf(100).negate());
        assertThatThrownBy(() -> {
            new DebtDtoValidator().validate(debtDTO);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage(new ValueNotValidException().getMessage());
    }

    @Test
    @DisplayName("The client id is null")
    void clientNull() {
        DebtDTO debtDTO = new DebtDTO();
        debtDTO.setValue(BigDecimal.valueOf(100));
        assertThatThrownBy(() -> {
            new DebtDtoValidator().validate(debtDTO);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage(new ClientNotFoundException().getMessage());
    }

    @Test
    @DisplayName("The debt is valid")
    void debtValid() {
        DebtDTO debtDTO = new DebtDTO();
        debtDTO.setValue(BigDecimal.valueOf(10));
        debtDTO.setClientId(UUID.randomUUID());
        assertDoesNotThrow(() -> new DebtDtoValidator().validate(debtDTO));
    }
}
