package net.zerjio.toolbox.command;

public interface CommandOutput {

    CommandOutput NO_OUTPUT = new CommandOutput() {};

    default CommandOutput write(String text, Object... args) {
        return this;
    }

}
