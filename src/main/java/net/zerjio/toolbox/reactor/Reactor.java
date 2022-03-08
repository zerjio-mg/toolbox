package net.zerjio.toolbox.reactor;

import net.zerjio.toolbox.messagebus.MessageRequest;
import net.zerjio.toolbox.messagebus.Subscriber;

public interface Reactor {

    enum Status {
        Initializing,
        Running,
        Stopped
    }

    String name();

    Status status();

    Reactor deploy(Node node);

    Reactor subscribe(String address, Subscriber subscriber);

    MessageRequest send(String address);

    MessageRequest publish(String address);

    Reactor delayTask(long milliseconds, Handler handler);

    Reactor backgroundTask(Handler handler);

    Reactor start();

    Reactor stop();
}
