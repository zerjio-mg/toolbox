package net.zerjio.toolbox.command;

import java.util.Optional;

public interface CommandInput {

    CommandInput NO_INPUT = new CommandInput() {};

    default Optional<String> read() {
        return Optional.empty();
    }
}
