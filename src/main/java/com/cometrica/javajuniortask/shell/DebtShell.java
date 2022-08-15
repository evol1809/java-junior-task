package com.cometrica.javajuniortask.shell;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.exeption.BaseException;
import com.cometrica.javajuniortask.service.DebtService;
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
public class DebtShell {
    private final DebtService debtService;

    @ShellMethod("Add debt")
    public String addDebt(@ShellOption UUID clientId, @ShellOption BigDecimal value) {
        try {
            DebtDTO debtDTO = new DebtDTO();
            debtDTO.setClientId(clientId);
            debtDTO.setValue(value);
            return debtService.addDebt(debtDTO).toString();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }

    @ShellMethod("Show all debts")
    public String showAllDebts(@ShellOption UUID clientId) {
        try {
            return debtService.getAllDebts(clientId).toString();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }
}
