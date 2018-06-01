package com.cyr1en.mcutils.dispatcher.exception;

public class CommandExistsException extends Exception {
    String command;

    public CommandExistsException(String command) {
        this.command = command;
    }
}
