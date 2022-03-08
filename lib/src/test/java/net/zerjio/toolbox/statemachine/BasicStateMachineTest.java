package net.zerjio.toolbox.statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BasicStateMachineTest {

    public static final String STATE_MACHINE_NEEDS_AT_LEAST_ONE_STATE = "StateMachine needs at least one state";
    public static final String STATE_NOT_PRESENT_IN_THIS_MACHINE = "state not present in this machine";

    @Test
    public void givenNoStatesWhenBuildMachineThenExplodes() {
        assertThrowsWithExceptionAndMessage(
            IllegalArgumentException.class,
            () -> new StateMachineBuilder<String, Integer>().build(),
            STATE_MACHINE_NEEDS_AT_LEAST_ONE_STATE
        );
    }

    @Test
    public void givenNullStatesWhenBuildMachineThenExplodes() {
        assertThrowsWithExceptionAndMessage(
            IllegalArgumentException.class,
            () -> new StateMachineBuilder<String, Integer>().states(null).build(),
            STATE_MACHINE_NEEDS_AT_LEAST_ONE_STATE
        );
    }

    @Test
    public void givenEmptyStatesWhenBuildMachineThenExplodes() {
        assertThrowsWithExceptionAndMessage(
            IllegalArgumentException.class,
            () -> new StateMachineBuilder<String, Integer>().states(Collections.emptyList()).build(),
            STATE_MACHINE_NEEDS_AT_LEAST_ONE_STATE
        );
    }

    @Test
    public void givenBadStateWhenBuildMachineThenExplodes() {
        assertThrowsWithExceptionAndMessage(
            IllegalArgumentException.class,
            () -> new StateMachineBuilder<String, Integer>()
                .states(Arrays.asList("A", "B", "C"))
                .from("A").to("B").when(1).then()
                .from("B").to("C").when(2).then()
                .from("B").to("D").when(3).then()
                .build(),
            STATE_NOT_PRESENT_IN_THIS_MACHINE
        );
    }

    @Test
    public void givenOneStateGraphWhenEventsAreTriggeredThenMachineWorksFancy() {

        StateMachine<String, Integer> stateMachine = new StateMachineBuilder<String, Integer>()
            .states(Collections.singletonList("A"))
            .from("A").to("A").when(1).then()
            .build();

        assertTrue(stateMachine.isActive());
        assertEquals("A", stateMachine.currentState());

        String state = stateMachine.event(1);

        assertFalse(stateMachine.isActive());
        assertEquals("A", state);
        assertEquals("A", stateMachine.currentState());
    }

    @Test
    public void givenSimpleStateGraphWhenEventsAreTriggeredThenHandlersAreCalled() {

        List<String> handlersCalls = new ArrayList<>();

        StateMachine<String, Integer> stateMachine = new StateMachineBuilder<String, Integer>()
            .states(Arrays.asList("A", "B", "C"))
            .from("A").to("B").when(1)
                .then((f, t, e) -> handlersCalls.add(String.format("change[%s, %s, %s]", f, t, e)))
            .from("B").to("C").when(2)
                .then((f, t, e) -> handlersCalls.add(String.format("change[%s, %s, %s]", f, t, e)))
            .onEnd((f, t, e) -> handlersCalls.add(String.format("end[%s, %s, %s]", f, t, e)))
            .onLostEvent(e -> handlersCalls.add(String.format("lost[%s]", e)))
            .build();

        stateMachine.event(0);
        stateMachine.event(1);
        stateMachine.event(1);
        stateMachine.event(2);
        stateMachine.event(2);

        assertFalse(stateMachine.isActive());
        assertEquals("C", stateMachine.currentState());
        assertEquals(5, handlersCalls.size());
        assertEquals("lost[0]", handlersCalls.get(0));
        assertEquals("change[A, B, 1]", handlersCalls.get(1));
        assertEquals("lost[1]", handlersCalls.get(2));
        assertEquals("change[B, C, 2]", handlersCalls.get(3));
        assertEquals("end[B, C, 2]", handlersCalls.get(4));
    }

    @Test
    public void givenNiceStatesGraphWhenEventsAreTriggeredThenMachineWorksFancy() {

        StateMachine<String, Integer> stateMachine = new StateMachineBuilder<String, Integer>()
            .states(Arrays.asList("A", "B", "C", "X"))
            .from("A").to("B").when(1).then()
            .from("B").to("B").when(2).then()
            .from("B").to("C").when(3).then()
            .from("C").to("B").when(4).then()
            .from("C").to("X").when(5).then()
            .build();

        Integer[]  events = { 2, 1, 2, 3, 4, 1, 3, 5};
        String[] expectedStates = { "A", "B", "B", "C", "B", "B", "C", "X" };
        Boolean[] expectedIsActive = { true, true, true, true, true, true, true, false };

        for (int step = 0; step < events.length; step++) {
            String state = stateMachine.event(events[step]);
            assertEquals(expectedStates[step], state);
            assertEquals(expectedStates[step], stateMachine.currentState());
            assertEquals(expectedIsActive[step], stateMachine.isActive());
        }
    }

    private <T extends Throwable> void assertThrowsWithExceptionAndMessage(
        Class<T> expectedException,
        Executable executable,
        String expectedMessage
    ) {
        T exception = assertThrows(expectedException, executable);
        assertEquals(exception.getMessage(), expectedMessage);
    }

}