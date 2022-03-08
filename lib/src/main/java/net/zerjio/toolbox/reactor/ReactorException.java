package net.zerjio.toolbox.reactor;

public class ReactorException extends RuntimeException {

    public ReactorException(Throwable error, String format, Object... args) {
        super(String.format(format, args), error);
    }

    public ReactorException(String format, Object... args) {
        super(String.format(format, args));
    }
}
