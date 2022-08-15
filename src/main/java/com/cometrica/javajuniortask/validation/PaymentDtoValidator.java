package com.cometrica.javajuniortask.validation;

import com.cometrica.javajuniortask.exeption.BaseException;
import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.exeption.ClientNotFoundException;
import com.cometrica.javajuniortask.exeption.ValueNotValidException;
import com.cometrica.javajuniortask.repository.DebtRepository;
import com.cometrica.javajuniortask.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class PaymentDtoValidator implements BaseValidator<PaymentDTO> {

    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;

    @Override
    public void validate(PaymentDTO obj) {

        if (obj.getValue() == null || obj.getValue().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValueNotValidException();

        if (obj.getClientId() == null)
            throw new ClientNotFoundException();

        BigDecimal debtSum = debtRepository.getValueSum(obj.getClientId()).orElse(BigDecimal.ZERO);
        BigDecimal paymentSum = paymentRepository.getValueSum(obj.getClientId()).orElse(BigDecimal.ZERO);
        BigDecimal totalDebt = debtSum.add(paymentSum.negate());
        if (totalDebt.add(obj.getValue().negate())
                .compareTo(BigDecimal.ZERO) < 0)
            throw new BaseException("The payment is more than the debt");
    }
}
