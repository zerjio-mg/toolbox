package net.zerjio.toolbox.command;

import java.util.Optional;

public interface CommandInput {

    Optional<String> read();
}
