package com.cometrica.javajuniortask.controller.web;

import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.service.DebtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class DebtWebController {

    private final DebtService debtService;

    @GetMapping(value = "/debts")
    public String getAllDebts(
            @RequestParam(value = "clientId", required = true) String clientId,
            @RequestParam(value = "clientName", required = true) String clientName,
            Model model) {
        model.addAttribute("debts", debtService.getAllDebts(UUID.fromString(clientId)));
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        return "debt/debts";
    }

    @GetMapping(value = "/debts/create")
    public String getCreate(@RequestParam(value = "clientId", required = true) String clientId,
                            @RequestParam(value = "clientName", required = true) String clientName,
                            Model model) {
        model.addAttribute("debtDTO", new DebtDTO());
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        return "debt/create";
    }

    @PostMapping(value = "/debts")
    public String save(@RequestParam(value = "clientId", required = true) String clientId,
                       @RequestParam(value = "clientName", required = true) String clientName,
                       @ModelAttribute DebtDTO form,
                       Model model) {
        form.setClientId(UUID.fromString(clientId));
        debtService.addDebt(form);
        model.addAttribute("debts", debtService.getAllDebts(UUID.fromString(clientId)));
        return "redirect:/debts?clientId=" + clientId + "&clientName=" + clientName;
    }
}
