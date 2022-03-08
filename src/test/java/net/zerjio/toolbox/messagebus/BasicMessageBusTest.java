package net.zerjio.toolbox.messagebus;

import net.zerjio.toolbox.messagebus.fixtures.PublishersFixture;
import net.zerjio.toolbox.messagebus.fixtures.SubscriberFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicMessageBusTest {

    public static final int PUBLISHERS = 5;

    public static final int MESSAGES = 50;

    public static final String ADDRESS_1 = "address_1";
    public static final String ADDRESS_2 = "address_2";

    public static final int SOME_DELAY = 100;

    @Test
    public void givenNoSubscribersWhenMessageIsSendThenIsDeliveredToNoOne() {

        BasicMessageBus mbus = new BasicMessageBus();
        assertFalse(mbus.hasMessagesPending());

        mbus.send(BasicMessageRequest.forOneSubscriber(ADDRESS_1));
        assertTrue(mbus.hasMessagesPending());

        mbus.dispatch();
        assertFalse(mbus.hasMessagesPending());
    }

    @Test
    public void givenOneSubscribersWhenMessageIsSendThenIsDeliveredSuccessfully() {

        BasicMessageBus mbus = new BasicMessageBus();
        assertFalse(mbus.hasMessagesPending());

        SubscriberFixture subscriberOne = new SubscriberFixture("One");
        mbus.subscribe(ADDRESS_1, subscriberOne);

        mbus.send(BasicMessageRequest.forOneSubscriber(ADDRESS_1));
        assertTrue(mbus.hasMessagesPending());

        mbus.dispatch();

        assertFalse(mbus.hasMessagesPending());
        assertEquals(1, subscriberOne.callsCount());
        assertFalse(subscriberOne.messages().get(0).isPresent());
    }

    @Test
    public void givenTwoSubscribersWhenMessagesAreSendThenAreDeliveredSuccessfully() {

        BasicMessageBus mbus = new BasicMessageBus();
        assertFalse(mbus.hasMessagesPending());

        SubscriberFixture subscriberOne = new SubscriberFixture("One");
        SubscriberFixture subscriberTwo = new SubscriberFixture("Two");

        mbus.subscribe(ADDRESS_1, subscriberOne);
        mbus.subscribe(ADDRESS_1, subscriberTwo);

        mbus.send(BasicMessageRequest.forOneSubscriber(ADDRESS_1).with("M1"));
        mbus.send(BasicMessageRequest.forOneSubscriber(ADDRESS_1).with("M2"));
        mbus.send(BasicMessageRequest.forOneSubscriber(ADDRESS_1).with("M3"));
        assertTrue(mbus.hasMessagesPending());

        mbus.dispatch();

        assertFalse(mbus.hasMessagesPending());
        assertEquals(2, subscriberOne.callsCount());
        assertEquals("M1", subscriberOne.messages().get(0).get());
        assertEquals("M3", subscriberOne.messages().get(1).get());
        assertEquals(1, subscriberTwo.callsCount());
        assertEquals("M2", subscriberTwo.messages().get(0).get());
    }

    @Test
    public void givenTwoSubscribersWhenMessagesAreSendInBroadcastThenAreDeliveredSuccessfully() {

        BasicMessageBus mbus = new BasicMessageBus();
        assertFalse(mbus.hasMessagesPending());

        SubscriberFixture subscriberOne = new SubscriberFixture("One");
        SubscriberFixture subscriberTwo = new SubscriberFixture("Two");
        mbus.subscribe(ADDRESS_1, subscriberOne);
        mbus.subscribe(ADDRESS_1, subscriberTwo);

        mbus.send(BasicMessageRequest.forAllSubscribers(ADDRESS_1).with("M1"));
        mbus.send(BasicMessageRequest.forAllSubscribers(ADDRESS_1).with("M2"));
        assertTrue(mbus.hasMessagesPending());

        mbus.dispatch();

        assertFalse(mbus.hasMessagesPending());
        assertEquals(2, subscriberOne.callsCount());
        assertEquals("M1", subscriberOne.messages().get(0).get());
        assertEquals("M2", subscriberOne.messages().get(1).get());
        assertEquals(2, subscriberTwo.callsCount());
        assertEquals("M1", subscriberTwo.messages().get(0).get());
        assertEquals("M2", subscriberTwo.messages().get(1).get());
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    public void givenMultiplePublishersWhenTheySendLostOfMessagesThenAllMessagesAreDeliveredToSubscriber() {

        BasicMessageBus mbus = new BasicMessageBus();
        assertFalse(mbus.hasMessagesPending());

        SubscriberFixture subscriber = new SubscriberFixture("One");
        mbus.subscribe(ADDRESS_1, subscriber);

        PublishersFixture publishersFixture = new PublishersFixture(PUBLISHERS, MESSAGES, mbus, ADDRESS_1);

        new Thread(() -> {
            while(publishersFixture.isRunning()) {
                mbus.waitForNewMessages();
                mbus.dispatch();
            }
        }).start();

        publishersFixture.start();

        do {
            delay();
        } while(publishersFixture.isRunning());

        delay();
        mbus.shutdown();

        assertEquals(PUBLISHERS * MESSAGES, subscriber.callsCount());
        for(Optional<Object> m : subscriber.messages()) {
            assertTrue(publishersFixture.messagesSended().contains(m.get()));
        }
    }

    private void delay() {
        try {
            Thread.sleep(SOME_DELAY);
        } catch (InterruptedException e) {}
    }
}