package net.zerjio.toolbox.reactor;

import java.util.Arrays;
import java.util.List;

import net.zerjio.toolbox.reactor.fixtures.HandlersFixture;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicReactorTest {

    private static final long DELAY_STARTING = 700;
    private static final long DELAY_GAP = 100;
    private static final long DELAY_THRESHOLD = 50;

    @Test
    public void givenDelayedTasksWhenScheduledThenAreCalledAsExpected() {

        BasicReactor reactor = new BasicReactor();
        reactor.start();

        HandlersFixture handlers = addHandlersWithDelay(reactor, Arrays.asList("A", "B", "C", "D", "E", "F"));

        try {
            Thread.sleep(DELAY_STARTING + DELAY_GAP);
        } catch (InterruptedException e) {}

        assertEquals(Reactor.Status.Running, reactor.status());
        reactor.stop();

        assertEquals(0, reactor.currentBackgroundThreads());
        assertEquals(3, reactor.currentSchedulerThreads());

        assertHandlersCallingOrder(handlers.all(), handlers.handlersCalled());
        assertHandlersCallingThreshold(handlers.all());
    }

    @Test
    public void givenBackgroundTasksWhenScheduledThenAreCalledAsExpected() {

        BasicReactor reactor = new BasicReactor();
        reactor.start();

        HandlersFixture handlers = addHandlersInBackground(reactor, Arrays.asList("A", "B", "C", "D", "E", "F"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        assertEquals(Reactor.Status.Running, reactor.status());
        reactor.stop();

        assertEquals(3, reactor.currentBackgroundThreads());
        assertEquals(0, reactor.currentSchedulerThreads());

        assertHandlersCalling(handlers.all(), handlers.handlersCalled());
    }

    private HandlersFixture addHandlersWithDelay(
        BasicReactor reactor,
        List<String> handlersNames
    ) {
        long delay = DELAY_STARTING;
        long gap = DELAY_GAP;

        HandlersFixture handlers = new HandlersFixture(handlersNames);
        for(HandlersFixture.SingleHandler h : handlers.all()) {
            reactor.delayTask(delay, h);
            delay -= gap;
        }
        return handlers;
    }

    private HandlersFixture addHandlersInBackground(
        BasicReactor reactor,
        List<String> handlersNames
    ) {
        HandlersFixture handlers = new HandlersFixture(handlersNames);
        for(HandlersFixture.SingleHandler h : handlers.all()) {
            reactor.backgroundTask(h);
        }
        return handlers;
    }

    private void assertHandlersCallingOrder(
        List<HandlersFixture.SingleHandler> handlers,
        List<HandlersFixture.SingleHandler> handlersCalled
    ) {
        assertEquals(handlers.size(), handlersCalled.size());
        int totalHandlers = handlers.size();
        for(int i= 0, f = totalHandlers - 1; i < totalHandlers; i++, f--) {
            assertEquals(handlers.get(f), handlersCalled.get(i));
        }
    }

    private void assertHandlersCalling(
        List<HandlersFixture.SingleHandler> handlers,
        List<HandlersFixture.SingleHandler> handlersCalled
    ) {
        assertEquals(handlers.size(), handlersCalled.size());
        for(HandlersFixture.SingleHandler h : handlers) {
            assertTrue(handlersCalled.contains(h));
        }
    }

    private void assertHandlersCallingThreshold(List<HandlersFixture.SingleHandler> handlers) {
        long delayed = DELAY_STARTING;
        for(HandlersFixture.SingleHandler h : handlers) {
            assertTrue(isDelayBetweenThreshold(h.delayed(), delayed));
            delayed -= DELAY_GAP;
        }
    }

    private boolean isDelayBetweenThreshold(long delay, long expextedDelay) {
        long lowThreshold = expextedDelay - DELAY_THRESHOLD;
        long highThreshold = expextedDelay + DELAY_THRESHOLD;
        return delay > lowThreshold && delay < highThreshold;
    }
}