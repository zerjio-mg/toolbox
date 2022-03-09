package net.zerjio.toolbox.command;

import java.io.BufferedReader;
import java.util.Optional;

public class CommandInputReader implements CommandInput {

    private final BufferedReader reader;

    public CommandInputReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public Optional<String> read() {
        try {
            return Optional.ofNullable(reader.readLine());
        } catch (Exception error) {
            throw new CommandException(error);
        }
    }
}
