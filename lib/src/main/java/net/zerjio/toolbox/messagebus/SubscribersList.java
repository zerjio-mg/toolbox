package net.zerjio.toolbox.messagebus;

import java.util.ArrayList;
import java.util.List;

public class SubscribersList {

    private List<Subscriber> subscribers;

    private int nextInRound;

    public SubscribersList() {
        subscribers = new ArrayList<>();
        nextInRound = 0;
    }

    SubscribersList add(Subscriber subscriber) {
        subscribers.add(subscriber);
        return this;
    }

    boolean hashSubscribers() {
        return !subscribers.isEmpty();
    }

    Subscriber nextInRound() {
        if (subscribers.isEmpty()) {
            throw new MessageBusException("Subscribers list is empty");
        }
        if (nextInRound >= subscribers.size()) {
            nextInRound = 0;
        }
        return subscribers.get(nextInRound++);
    }

    List<Subscriber> all() {
        return subscribers;
    }

    @Override
    public String toString() {
        return String.format("SubscribersList{subscribers: %d, nextInRound: %d}", subscribers.size(), nextInRound);
    }
}
