package net.zerjio.toolbox.messagebus;

import net.zerjio.toolbox.messagebus.fixtures.SubscriberFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubscribersListTest {

    @Test
    public void givenNoSubscribersWhenAskForNextInRoundThenExceptionIsThrown() {

        SubscribersList subscribersList = new SubscribersList();

        MessageBusException exception = assertThrows(
            MessageBusException.class,
            () -> subscribersList.nextInRound()
        );

        assertEquals(exception.getMessage(), "Subscribers list is empty");
    }

    @Test
    public void givenOneSubscribersWhenAskForNextInRoundThenSubscriberIsReturnedAlways() {

        Subscriber subscriber = new SubscriberFixture("A");

        SubscribersList subscribersList = new SubscribersList();
        subscribersList.add(subscriber);

        assertEquals(subscriber, subscribersList.nextInRound());
        assertEquals(subscriber, subscribersList.nextInRound());
        assertEquals(subscriber, subscribersList.nextInRound());
    }

    @Test
    public void givenSomeSubscribersWhenAskForNextInRoundThenExpectedSubscriberIsReturned() {

        Subscriber[] subscribers = {
            new SubscriberFixture("A"),
            new SubscriberFixture("B"),
            new SubscriberFixture("C")
        };

        SubscribersList subscribersList = new SubscribersList();
        for (Subscriber subscriber : subscribers) {
            subscribersList.add(subscriber);
        }

        assertEquals(subscribers[0], subscribersList.nextInRound());
        assertEquals(subscribers[1], subscribersList.nextInRound());
        assertEquals(subscribers[2], subscribersList.nextInRound());
        assertEquals(subscribers[0], subscribersList.nextInRound());
    }
}