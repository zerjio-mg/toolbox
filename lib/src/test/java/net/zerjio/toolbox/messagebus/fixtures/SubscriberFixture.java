package net.zerjio.toolbox.messagebus.fixtures;

import net.zerjio.toolbox.messagebus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriberFixture implements Subscriber {

    private String name;

    List<Optional<Object>> messages;

    public SubscriberFixture(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    @Override
    public Object handle(Optional<Object> message) {
        messages.add(message);
        return messages.size();
    }

    public int callsCount() {
        return messages.size();
    }

    public List<Optional<Object>> messages() {
        return messages;
    }

    @Override
    public String toString() {
        return String.format("SubscriberFixture{%s, callsCount: %d}", name, messages.size());
    }
}