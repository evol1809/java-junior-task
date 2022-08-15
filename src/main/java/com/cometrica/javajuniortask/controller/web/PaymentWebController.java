package com.cometrica.javajuniortask.controller.web;

import com.cometrica.javajuniortask.dto.PaymentDTO;
import com.cometrica.javajuniortask.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class PaymentWebController {

    private final PaymentService paymentService;

    @GetMapping(value = "/payments")
    public String getAllPayments(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientName") String clientName,
            Model model) {
        model.addAttribute("payments", paymentService.getAllPayment(UUID.fromString(clientId)));
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        return "payment/payments";
    }

    @GetMapping(value = "/payments/create")
    public String getCreate(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientName") String clientName,
            Model model) {
        model.addAttribute("paymentDTO", new PaymentDTO());
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        return "payment/create";
    }

    @PostMapping(value = "/payments")
    public String save(
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientName") String clientName,
            @ModelAttribute PaymentDTO form,
            Model model) {
        form.setClientId(UUID.fromString(clientId));
        paymentService.addPayment(form);
        model.addAttribute("payments", paymentService.getAllPayment(UUID.fromString(clientId)));
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        return "redirect:/payments?clientId=" + clientId + "&clientName=" + clientName;
    }
}
