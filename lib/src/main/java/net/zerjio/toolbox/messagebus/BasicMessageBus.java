package net.zerjio.toolbox.messagebus;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BasicMessageBus implements MessageBus {

    private final Map<String, SubscribersList> allSubscribers;

    private final Queue<MessageRequest> messagesToDeliver;

    private boolean running;

    public BasicMessageBus() {
        allSubscribers = new HashMap<>();
        messagesToDeliver = new ConcurrentLinkedQueue<>();
        running = true;
    }

    @Override
    public MessageBus subscribe(String address, Subscriber subscriber) {
        if (running) {
            allSubscribers
                .computeIfAbsent(address, key -> new SubscribersList())
                .add(subscriber);
        }
        return this;
    }

    @Override
    public synchronized MessageBus send(MessageRequest messageRequest) {
        if (running) {
            messagesToDeliver.add(messageRequest);
            notifyAll();
        }
        return this;
    }

    @Override
    public boolean hasMessagesPending() {
        return !messagesToDeliver.isEmpty();
    }

    @Override
    public MessageBus dispatch() {
        while(running && hasMessagesPending()) {
            MessageRequest messageRequest = messagesToDeliver.poll();
            deliver(messageRequest, allSubscribers.get(messageRequest.address()));
        }
        return this;
    }

    private void deliver(MessageRequest messageRequest, SubscribersList subscribersList) {
        if (subscribersList != null && subscribersList.hashSubscribers()) {
            if (messageRequest.isBroadcast()) {
                for (Subscriber subscriber : subscribersList.all()) {
                    deliver(messageRequest, subscriber);
                }
            } else {
                deliver(messageRequest, subscribersList.nextInRound());
            }
        }
    }

    private void deliver(MessageRequest messageRequest, Subscriber subscriber) {
        try {
            Object response = subscriber.handle(messageRequest.message());
            messageRequest.onSuccessHandler().handle(Optional.ofNullable(response));
        } catch (Exception error) {
            messageRequest.onErrorHandler().handle(error);
        }
    }

    @Override
    public synchronized void waitForNewMessages() {
        if (running && messagesToDeliver.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }

    @Override
    public synchronized void shutdown() {
        messagesToDeliver.clear();
        running = false;
        notifyAll();
    }
}
