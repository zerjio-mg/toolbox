package net.zerjio.toolbox.command;

import java.util.Optional;

public abstract class Command {

    private static final CommandInput NO_INPUT = new CommandInputNull();

    private static final CommandOutput NO_OUTPUT = new CommandOutputNull();

    private final String name;

    protected CommandArguments arguments;

    protected CommandInput input = NO_INPUT;

    protected CommandOutput output = NO_OUTPUT;

    public Command(String name, CommandArguments arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Command withInput(CommandInput input) {
        this.input = input;
        return this;
    }

    public Command withOutput(CommandOutput output) {
        this.output = output;
        return this;
    }

    public String name() {
        return name;
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
