package net.zerjio.toolbox.messagebus;

public interface MessageBus {

    MessageBus subscribe(String address, Subscriber subscriber);

    MessageBus send(MessageRequest messageRequest);

    boolean hasMessagesPending();

    MessageBus dispatch();

    void waitForNewMessages();

    void shutdown();
}
