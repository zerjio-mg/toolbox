package net.zerjio.toolbox.command;

public class CommandOutputNull implements CommandOutput {

    @Override
    public CommandOutputNull write(String text, Object... args) {
        return this;
    }
}
