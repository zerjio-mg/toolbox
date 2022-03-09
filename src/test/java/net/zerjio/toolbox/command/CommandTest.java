package net.zerjio.toolbox.command;

import net.zerjio.toolbox.command.util.CatCommand;
import net.zerjio.toolbox.command.util.GrepCommand;
import net.zerjio.toolbox.command.util.ReplaceCommand;
import net.zerjio.toolbox.command.util.TestCommand;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    public void givenTestCommandWhenExecutedThenOuputIsCorrect() {

        StringWriter buffer = new StringWriter();

        CommandArguments arguments = new CommandArguments()
            .put("text", "abc")
            .put("number", Integer.valueOf(666));

        CommandInput input = new CommandInputReader(new BufferedReader(new StringReader("one\ntwo\nthree")));
        CommandOutput output = new CommandOutputWriter(buffer);

        new TestCommand(arguments)
            .withInput(input)
            .withOutput(output)
            .run();

        assertEquals("[text:abc][number:666]\none\ntwo\nthree", buffer.toString());
    }

    @Test
    public void givenCatCommandWhenExecutedThenOuputIsCorrect() {

        StringWriter buffer = new StringWriter();

        CommandInput input = new CommandInputReader(new BufferedReader(new StringReader("one\ntwo\nthree")));
        CommandOutput output = new CommandOutputWriter(buffer);

        new CatCommand()
            .withInput(input)
            .withOutput(output)
            .run();

        assertEquals("one\ntwo\nthree", buffer.toString());
    }

    @Test
    public void givenCatCommandWithShowNumberWhenExecutedThenOuputIsCorrect() {

        StringWriter buffer = new StringWriter();

        CommandArguments arguments = new CommandArguments()
            .put("show-number", true);

        CommandInput input = new CommandInputReader(new BufferedReader(new StringReader("one\ntwo\nthree")));
        CommandOutput output = new CommandOutputWriter(buffer);

        new CatCommand(arguments)
            .withInput(input)
            .withOutput(output)
            .run();

        assertEquals("1: one\n2: two\n3: three", buffer.toString());
    }

    @Test
    public void givenChainOfCommandsWhenExecutedThenOuputIsCorrect() {

        StringWriter buffer = new StringWriter();

        CommandInput input = new CommandInputReader(new BufferedReader(new StringReader("one\ntwo\nthree")));
        CommandOutput output = new CommandOutputWriter(buffer);

        new CommandsChain(input, output)
            .link(
                new CatCommand(),
                new GrepCommand(new CommandArguments().put("pattern", "o")),
                new ReplaceCommand(new CommandArguments().put("replace", "o").put("with", "X")),
                new ReplaceCommand(new CommandArguments().put("replace", "\n").put("with", ""))
            )
            .run();

        assertEquals("XnetwX", buffer.toString());
    }

}