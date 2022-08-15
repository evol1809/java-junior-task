package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.exeption.ValueNotValidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DebtDtoValidator implements BaseValidator<DebtDTO> {

    @Override
    public void validate(DebtDTO obj) {

        if(obj.getValue() == null || obj.getValue().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValueNotValidException();
        if(obj.getClientId() == null)
            throw new ClientNotFoundException();
    }
}
