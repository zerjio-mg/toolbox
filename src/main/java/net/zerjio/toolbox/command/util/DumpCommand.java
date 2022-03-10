package net.zerjio.toolbox.command.util;

import net.zerjio.toolbox.command.Command;
import net.zerjio.toolbox.command.CommandArguments;

import java.util.Optional;

public class DumpCommand extends Command {

    public DumpCommand() {
        this(CommandArguments.NO_ARGUMENTS);
    }

    public DumpCommand(CommandArguments arguments) {
        super("test", arguments);
    }

    @Override
    public void run() {
        arguments
            .stream()
            .forEach(entry -> output.write("[%s:%s]", entry.getKey(), entry.getValue()));
        while(true) {
            Optional<String> text = input.read();
            if (text.isEmpty()) {
                return;
            }
            output.write("\n%s", text.get());
        }
    }

}
