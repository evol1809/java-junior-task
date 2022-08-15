package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.dto.ClientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ClientDtoValidatorTest {

    @Test
    @DisplayName("The name is more than 20 characters")
    void nameMore20() {
        ClientDtoValidator clientDtoValidator = new ClientDtoValidator();
        ClientDTO client = new ClientDTO();
        client.setName("1234567890123456789012345678901234567890");
        assertThatThrownBy(() -> {
            clientDtoValidator.validate(client);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("The client name is more than 20 characters");
    }

    @Test
    @DisplayName("The name is valid")
    void nameValid() {
        ClientDtoValidator clientDtoValidator = new ClientDtoValidator();
        ClientDTO client = new ClientDTO();
        client.setName("BestClient");
        assertDoesNotThrow(() -> clientDtoValidator.validate(client));
    }

    @Test
    @DisplayName("The name is empty")
    void nameEmpty() {
        ClientDtoValidator clientDtoValidator = new ClientDtoValidator();
        ClientDTO client = new ClientDTO();
        client.setName("");
        assertThatThrownBy(() -> {
            clientDtoValidator.validate(client);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("The client name is null");
    }

    @Test
    @DisplayName("The name is null")
    void nameNull() {
        ClientDtoValidator clientDtoValidator = new ClientDtoValidator();
        ClientDTO client = new ClientDTO();
        assertThatThrownBy(() -> {
            clientDtoValidator.validate(client);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("The client name is null");
    }
}
