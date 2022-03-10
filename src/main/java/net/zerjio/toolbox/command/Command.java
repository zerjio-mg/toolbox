package net.zerjio.toolbox.command;

public abstract class Command {

    private final String name;

    protected CommandArguments arguments;

    protected CommandInput input = CommandInput.NO_INPUT;

    protected CommandOutput output = CommandOutput.NO_OUTPUT;

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

    public abstract void run();

}
