package com.cometrica.javajuniortask.shell;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.exeption.BaseException;
import com.cometrica.javajuniortask.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
@RequiredArgsConstructor
@Log4j2
public class ClientShell {

    private final ClientService clientService;

    @ShellMethod("Shows all clients in db")
    public Iterable<ClientDTO> showAllClients() {
        try {
            return clientService.getAllClients();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }

    @ShellMethod("Adds client to db")
    public String addClient(@ShellOption String name) {
        try {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(name);
            return clientService.addClient(clientDTO).toString();
        } catch (BaseException e) {
            log.log(Level.valueOf("SHELL"), e.getMessage());
        } catch (Exception e) {
            log.log(Level.valueOf("SHELL"), "INTERNAL_SERVER_ERROR");
            log.error(e.toString());
        }
        return null;
    }
}
