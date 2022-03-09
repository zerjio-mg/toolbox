package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.Command;
import net.zerjio.toolbox.command.CommandArguments;

import java.util.Optional;

public class CatCommand extends Command {

    private StringBuilder buffer;

    private Boolean showLineNumbers;

    private int lineNumber;

    public CatCommand() {
        this(CommandArguments.NO_ARGUMENTS);
    }

    public CatCommand(CommandArguments arguments) {
        super("cat", arguments);
    }

    @Override
    public void start() {
        buffer = new StringBuilder();
        showLineNumbers = arguments.getBoolean("show-number", Boolean.FALSE);
        lineNumber = 0;
    }

    @Override
    public Optional<String> run(String item) {
        buffer.setLength(0);
        if (++lineNumber > 1) {
            buffer.append("\n");
        }
        if (showLineNumbers) {
            buffer.append(lineNumber).append(": ");
        }
        buffer.append(item);

        return Optional.of(buffer.toString());
    }

}
