package net.zerjio.toolbox.command;

public interface CommandOutput {

    CommandOutput write(String text, Object... args);
}
