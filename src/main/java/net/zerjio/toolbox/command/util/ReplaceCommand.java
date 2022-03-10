package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.CommandArguments;
import net.zerjio.toolbox.command.CommandPipeable;

import java.util.Optional;

public class ReplaceCommand extends CommandPipeable {

    private String replace;
    private String with;

    public ReplaceCommand() {
        this(new CommandArguments());
    }

    public ReplaceCommand(CommandArguments arguments) {
        super("replace", arguments);
    }

    @Override
    public void start() {
        replace = arguments.getString("replace", "");
        with = arguments.getString("with", "");
    }

    @Override
    public Optional<String> run(String item) {
        return Optional.of(item.replace(replace, with));
    }
}
