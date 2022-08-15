package com.cometrica.javajuniortask.exeption;

public class ClientNotFoundException extends BaseException {
    public ClientNotFoundException() {
        super("Client not found");
    }
}
