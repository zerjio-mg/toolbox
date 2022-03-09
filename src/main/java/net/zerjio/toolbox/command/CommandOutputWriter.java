package net.zerjio.toolbox.command;

import java.io.Writer;

public class CommandOutputWriter implements CommandOutput {

    private final Writer writer;

    public CommandOutputWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public CommandOutputWriter write(String text, Object... args) {
        try {
            writer.write((args.length > 0)? String.format(text, args) : text);
            writer.flush();
            return this;
        } catch (Exception error) {
            throw new CommandException(error);
        }
    }
}
