package net.zerjio.toolbox.command;

public class CommandException extends RuntimeException {

    public CommandException(String format, Object... args) {
        super(String.format(format, args));
    }

    public CommandException(Throwable error) {
        super(error);
    }

}
