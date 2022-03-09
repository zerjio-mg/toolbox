package net.zerjio.toolbox.command;

public class CommandOutputString implements CommandOutput {

    private final StringBuilder buffer;

    public CommandOutputString(StringBuilder buffer) {
        this.buffer = buffer;
    }

    @Override
    public CommandOutputString write(String text, Object... args) {
        buffer.append((args.length > 0)? String.format(text, args) : text);
        return this;
    }
}
