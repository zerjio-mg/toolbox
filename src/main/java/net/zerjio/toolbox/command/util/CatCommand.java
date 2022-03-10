package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.CommandArguments;
import net.zerjio.toolbox.command.CommandPipeable;

import java.util.Optional;

public class CatCommand extends CommandPipeable {

    public static final String COMMAND_NAME = "cat";

    public static final String PARAMETER_SHOW_NUMBER = "show-number";

    private StringBuilder buffer;

    private Boolean showLineNumbers;

    private int lineNumber;

    public CatCommand() {
        this(new CommandArguments());
    }

    public CatCommand(CommandArguments arguments) {
        super(COMMAND_NAME, arguments);
    }

    public CatCommand showNumbers(Boolean value) {
        arguments.put(PARAMETER_SHOW_NUMBER, value);
        return this;
    }

    @Override
    public void start() {
        buffer = new StringBuilder();
        showLineNumbers = arguments.getBoolean(PARAMETER_SHOW_NUMBER, Boolean.FALSE);
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
