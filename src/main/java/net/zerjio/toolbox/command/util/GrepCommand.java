package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.CommandArguments;
import net.zerjio.toolbox.command.CommandPipeable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepCommand extends CommandPipeable {

    private Pattern pattern;

    public GrepCommand() {
        this(new CommandArguments());
    }

    public GrepCommand(CommandArguments arguments) {
        super("grep", arguments);
    }

    @Override
    public void start() {
        pattern = Pattern.compile(arguments.getString("pattern", ""));
    }

    @Override
    public Optional<String> run(String item) {
        Matcher matcher = pattern.matcher(item);
        return (matcher.find())? Optional.of(item) : Optional.empty();
    }
}
