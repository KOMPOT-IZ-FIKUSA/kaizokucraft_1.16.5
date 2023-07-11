package com.deadfikus.kaizokucraft.exceptions;

public class ClientSideException extends RuntimeException {

    public ClientSideException() {
        super("Server code running on client side");
    }

}
