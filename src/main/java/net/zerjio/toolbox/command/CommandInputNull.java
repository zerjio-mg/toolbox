package net.zerjio.toolbox.command;

import java.util.Optional;

public class CommandInputNull implements CommandInput {

    @Override
    public Optional<String> read() {
        return Optional.empty();
    }
}
