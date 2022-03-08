package net.zerjio.toolbox.reactor;

import net.zerjio.toolbox.messagebus.MessageBus;
import net.zerjio.toolbox.messagebus.MessageRequest;
import net.zerjio.toolbox.messagebus.Subscriber;
import net.zerjio.toolbox.messagebus.BasicMessageBus;
import net.zerjio.toolbox.messagebus.BasicMessageRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BasicReactor implements Reactor {

    public static final String MESSAGE_ADDRESS_DEPLOY = "reactor@deploy";
    public static final String MESSAGE_ADDRESS_DELAYED = "reactor@delayed";

    private static final int SCHEDULER_POOL = 3;
    private static final int BACKGROUND_POOL = 3;

    private static int nextReactorThreadId = 0;

    private int nextSchedulerThreadId = 0;

    private int nextBackgroundThreadId = 0;

    private final String name;

    private Status status;

    private final ScheduledExecutorService scheduler;

    private final ExecutorService background;

    private final MessageBus messageBus;

    private final Map<String, Node> nodes;

    public BasicReactor() {
        this(String.format("Reactor-%d", ++nextReactorThreadId));
    }

    public BasicReactor(String name) {
        this.name = name;
        this.status = Status.Initializing;
        this.scheduler = Executors.newScheduledThreadPool(
            SCHEDULER_POOL,
            r -> new Thread(r, String.format("%s-%s-%d", this.name, "Scheduler", ++nextSchedulerThreadId))
        );
        this.background = Executors.newFixedThreadPool(
            BACKGROUND_POOL,
            r -> new Thread(r, String.format("%s-%s-%d", this.name, "Background", ++nextBackgroundThreadId))
        );
        this.messageBus = new BasicMessageBus();
        this.nodes = new HashMap<>();

        messageBus.subscribe(MESSAGE_ADDRESS_DEPLOY, this::deployHandler);
        messageBus.subscribe(MESSAGE_ADDRESS_DELAYED, this::delayedHandler);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public Reactor deploy(Node node) {
        checkReactorIsReady();
        node.init(this);
        nodes.put(node.name(), node);
        send(MESSAGE_ADDRESS_DEPLOY).with(node);
        return this;
    }

    @Override
    public Reactor subscribe(String address, Subscriber subscriber) {
        checkReactorIsReady();
        messageBus.subscribe(address, subscriber);
        return this;
    }

    @Override
    public MessageRequest send(String address) {
        checkReactorIsReady();
        MessageRequest messageRequest = BasicMessageRequest.forOneSubscriber(address);
        messageBus.send(messageRequest);
        return messageRequest;
    }

    @Override
    public MessageRequest publish(String address) {
        checkReactorIsReady();
        MessageRequest messageRequest = BasicMessageRequest.forAllSubscribers(address);
        messageBus.send(messageRequest);
        return messageRequest;
    }

    @Override
    public Reactor delayTask(long milliseconds, Handler handler) {
        checkReactorIsReady();
        scheduler.schedule(
            () -> send(MESSAGE_ADDRESS_DELAYED).with(handler),
            milliseconds,
            TimeUnit.MILLISECONDS
        );
        return this;
    }

    @Override
    public Reactor backgroundTask(Handler handler) {
        checkReactorIsReady();
        background.submit(() -> handler.run());
        return this;
    }

    @Override
    public Reactor start() {
        if (status != Status.Initializing) return this;
        status = Status.Running;
        new Thread(() -> {
            while(status == Status.Running) {
                messageBus.waitForNewMessages();
                messageBus.dispatch();
            }
        }, name).start();
        return this;
    }

    @Override
    public Reactor stop() {
        if (status != Status.Running) return this;
        status = Status.Stopped;
        scheduler.shutdownNow();
        background.shutdownNow();
        messageBus.shutdown();
        nodes.values().forEach(n -> n.stop());
        return this;
    }

    public int currentSchedulerThreads() {
        return nextSchedulerThreadId;
    }

    public int currentBackgroundThreads() {
        return nextBackgroundThreadId;
    }

    private Object deployHandler(Optional<Object> message) {
        if (message.isPresent() && message.get() instanceof Node) {
            ((Node)message.get()).start();
        }
        return null;
    }

    private Object delayedHandler(Optional<Object> message) {
        if (message.isPresent() && message.get() instanceof Handler) {
            ((Handler)message.get()).run();
        }
        return null;
    }

    private void checkReactorIsReady() {
        if (status == Status.Stopped) {
            throw new ReactorException("Operation not allowed now. Reactor is stopped !!!");
        }
    }

}