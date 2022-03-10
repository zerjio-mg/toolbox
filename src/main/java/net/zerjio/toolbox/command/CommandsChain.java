package net.zerjio.toolbox.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CommandsChain {

    private final CommandInput input;

    private final CommandOutput output;

    private List<CommandPipeable> commandsChain;

    public CommandsChain(CommandInput input, CommandOutput output) {
        this.input = input;
        this.output = output;
        this.commandsChain = new ArrayList<>();
    }

    public CommandsChain link(CommandPipeable... commands) {
        Collections.addAll(commandsChain, commands);
        return this;
    }

    public void run() {
        commandsChain.forEach(CommandPipeable::start);
        while(true) {
            Optional<String> item = input.read();
            if (item.isEmpty()) {
                commandsChain.forEach(CommandPipeable::end);
                return;
            }
            for(CommandPipeable c : commandsChain) {
                item = c.run(item.get());
                if (item.isEmpty()) {
                    break;
                }
            }
            item.ifPresent(output::write);
        }
    }
}
