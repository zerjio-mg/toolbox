package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.CommandArguments;
import net.zerjio.toolbox.command.CommandPipeable;

import java.util.Optional;

public class ReplaceCommand extends CommandPipeable {

    public static final String PARAMETER_REPLACE = "replace";
    public static final String PARAMETER_WITH = "with";

    private String replace;
    private String with;

    public ReplaceCommand() {
        this(new CommandArguments());
    }

    public ReplaceCommand(CommandArguments arguments) {
        super("replace", arguments);
    }

    public ReplaceCommand replace(String value) {
        arguments.put(PARAMETER_REPLACE, value);
        return this;
    }

    public ReplaceCommand with(String value) {
        arguments.put(PARAMETER_WITH, value);
        return this;
    }

    @Override
    public void start() {
        replace = arguments.getString(PARAMETER_REPLACE, "");
        with = arguments.getString(PARAMETER_WITH, "");
    }

    @Override
    public Optional<String> run(String item) {
        return Optional.of(item.replace(replace, with));
    }
}
