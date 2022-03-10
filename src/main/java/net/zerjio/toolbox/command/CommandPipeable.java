package net.zerjio.toolbox.command;

import java.util.Optional;

public abstract class CommandPipeable extends Command {

    public CommandPipeable(String name, CommandArguments arguments) {
        super(name, arguments);
    }

    public void run() {
        start();
        Optional<String> text = input.read();
        do {
            if (text.isPresent()) {
                output.write(run(text.get()).get());
            }
            text = input.read();
        } while (text.isPresent());
        end();
    }

    public void start() {
    }

    public void end() {
    }

    public abstract Optional<String> run(String item);
}
