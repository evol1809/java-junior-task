package com.cometrica.javajuniortask.controller.web;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class ClientWebController {

    private final ClientService clientService;

    @GetMapping(value = "")
    public String index() {
        return "redirect:/clients";
    }

    @GetMapping(value = "/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "client/clients";
    }

    @GetMapping(value = "/clients/create")
    public String getCreate(Model model) {
        model.addAttribute("clientDTO", new ClientDTO());
        return "client/create";
    }

    @PostMapping(value = "/clients")
    public String save(@ModelAttribute ClientDTO form, Model model) {
        clientService.addClient(form);
        model.addAttribute("clients", clientService.getAllClients());
        return "redirect:/clients";
    }

}
