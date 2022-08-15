package com.cometrica.javajuniortask.shell;

import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.exeption.BaseException;
import com.cometrica.javajuniortask.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.util.UUID;

@ShellComponent
@RequiredArgsConstructor
@Log4j2
public class PaymentShell {
    private final PaymentService paymentService;

    @ShellMethod("Add payment")
    String addPayment(@ShellOption UUID clientId, @ShellOption BigDecimal value) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setValue(value);
            paymentDTO.setClientId(clientId);

            return paymentService.addPayment(paymentDTO).toString();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }

    @ShellMethod("Show all payments")
    String showAllPayments(@ShellOption UUID clientId) {
        try {
            return paymentService.getAllPayment(clientId).toString();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }
}
