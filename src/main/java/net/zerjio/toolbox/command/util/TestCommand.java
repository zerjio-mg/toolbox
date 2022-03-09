package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.Command;
import net.zerjio.toolbox.command.CommandArguments;

import java.util.Optional;

public class TestCommand extends Command {

    private StringBuilder buffer;

    public TestCommand() {
        this(CommandArguments.NO_ARGUMENTS);
    }

    public TestCommand(CommandArguments arguments) {
        super("test", arguments);
    }

    @Override
    public void start() {
        buffer = new StringBuilder();
        arguments
            .stream()
            .forEach(entry -> buffer
                .append("[")
                .append(entry.getKey())
                .append(":")
                .append(entry.getValue())
                .append("]")
            );
    }

    @Override
    public Optional<String> run(String item) {
        Optional<String> result =  Optional.of(buffer.append("\n").append(item).toString());
        buffer.setLength(0);
        return result;
    }

}
