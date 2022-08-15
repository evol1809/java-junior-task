package com.cometrica.javajuniortask.exeption;

public class ValueNotValidException extends BaseException {
    public ValueNotValidException() {
        super("Value less than 0 or equal to 0 or equal null");
    }
}
