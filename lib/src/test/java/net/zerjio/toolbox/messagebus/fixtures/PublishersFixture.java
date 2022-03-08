package net.zerjio.toolbox.messagebus.fixtures;

import net.zerjio.toolbox.messagebus.MessageBus;
import net.zerjio.toolbox.messagebus.BasicMessageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PublishersFixture {

    public class Publisher implements Runnable {

        private Random rnd;

        private int id;

        private int messagesCount;

        private boolean running;

        public Publisher(int id, int messagesCount) {
            this.rnd = new Random();
            this.id = id;
            this.messagesCount = messagesCount;
            this.running = true;
        }

        @Override
        public void run() {
            for(int count= 0; count < messagesCount; count++) {
                delay();
                String message = String.format("Publisher[%d][%s]", id, count);
                mbus.send(BasicMessageRequest.forAllSubscribers(address).with(message));
                messagesSended.add(message);
            }
            running = false;
        }

        private void delay() {
            try {
                long delay = rnd.nextInt(100);
                Thread.sleep(delay);
            } catch (InterruptedException e) {}
        }

        @Override
        public String toString() {
            return String.format("PublisherFixture.Subscriber{id: %d, running: %b}", id, running);
        }
    }

    private List<Object> messagesSended;

    private List<Publisher> publishers;

    private MessageBus mbus;

    private String address;

    public PublishersFixture(int publishersCount, int messagesCount, MessageBus mbus, String address) {
        messagesSended = Collections.synchronizedList(new ArrayList<>());
        publishers = new ArrayList<>();
        this.mbus = mbus;
        this.address = address;

        for(int count= 0; count < publishersCount; count++) {
            publishers.add(new Publisher(count, messagesCount));
        }
    }

    public void start() {
        for(Publisher p : publishers) {
            new Thread(p).start();
        }
    }

    public boolean isRunning() {
        for(Publisher p : publishers) {
            if (p.running) {
                return true;
            }
        }
        return false;
    }

    public List<Object> messagesSended() {
        return messagesSended;
    }
}
