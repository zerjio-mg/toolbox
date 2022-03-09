package net.zerjio.toolbox.command;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CommandArguments {

    public static final CommandArguments NO_ARGUMENTS = new CommandArguments(Collections.emptyMap());

    private final Map<String, Object> arguments;

    public CommandArguments() {
        this(new LinkedHashMap<>());
    }

    public CommandArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public CommandArguments put(String name, Object value) {
        arguments.put(name, value);
        return this;
    }

    public boolean exists(String name) {
        return arguments.containsKey(name);
    }

    public Object get(String name, Object defaultValue) {
        return arguments.getOrDefault(name, defaultValue);
    }

    public String getString(String name, String defaultValue) {
        return arguments.getOrDefault(name, defaultValue).toString();
    }

    public Boolean getBoolean(String name, Boolean defaultValue) {
        return Boolean.parseBoolean(arguments.getOrDefault(name, defaultValue).toString());
    }

    public Integer getInteger(String name, Integer defaultValue) {
        return Integer.parseInt(arguments.getOrDefault(name, defaultValue).toString());
    }

    public Stream<Map.Entry<String, Object>> stream() {
        return arguments.entrySet().stream();
    }
}
