package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.exeption.BaseException;
import com.cometrica.javajuniortask.dto.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoValidator implements BaseValidator<ClientDTO> {

    @Override
    public void validate(ClientDTO obj) {

        if(obj.getName() == null || obj.getName().isEmpty())
            throw new BaseException("The client name is null");
        if(obj.getName().length() > 20)
            throw new BaseException("The client name is more than 20 characters");
    }
}
