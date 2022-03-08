package net.zerjio.toolbox.statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StateMachineBuilder<T, E> {

    public class Transition {

        T from;
        T to;
        E when;

        TransitionHandler<T, E> handler;

        Transition(T state) {
            this.from = state;
        }

        public Transition to(T state) {
            checkStateIsPresent(state);
            this.to = state;
            return this;
        }

        public Transition when(E event) {
            this.when = event;
            return this;
        }

        public StateMachineBuilder<T, E> then(TransitionHandler<T, E> handler) {
            this.handler = handler;
            return StateMachineBuilder.this;
        }

        public StateMachineBuilder<T, E> then() {
            this.handler = (from, to, event) -> {};
            return StateMachineBuilder.this;
        }

        public T from() {
            return from;
        }

        public T to() {
            return to;
        }

        public E when() {
            return when;
        }

        public TransitionHandler handler() {
            return handler;
        }
    }

    private List<T> states;

    private T beginState;

    private T endState;

    private List<Transition> transitions;

    private TransitionHandler<T, E> onEndHandler;

    private TransitionLostHandler<E> onLostEventHandler;


    public StateMachineBuilder() {
        this.states = Collections.emptyList();
        this.transitions = new ArrayList<>();
        this.onEndHandler = (f, t, e) -> {};
        this.onLostEventHandler = (e) -> {};
    }

    public StateMachineBuilder<T, E> states(List<T> states) {
        checkStatesAreValid(states);
        this.states = states;
        this.beginState = states.get(0);
        this.endState = states.get(states.size() - 1);
        return this;
    }

    public Transition from(T state) {
        checkStateIsPresent(state);
        Transition transition = new Transition(state);
        transitions.add(transition);
        return transition;
    }

    public StateMachineBuilder<T, E> onEnd(TransitionHandler<T, E> handler) {
        onEndHandler = handler;
        return this;
    }

    public StateMachineBuilder<T, E> onLostEvent(TransitionLostHandler<E> handler) {
        onLostEventHandler = handler;
        return this;
    }

    public StateMachine<T, E> build() {
        checkStatesAreValid(states);
        return new BasicStateMachine<>(states, transitions, beginState, endState, onEndHandler, onLostEventHandler);
    }

    private void checkStateIsPresent(T state) {
        if (!states.contains(state)) {
            throw new IllegalArgumentException("state not present in this machine");
        }
    }

    private void checkStatesAreValid(List<T> states) {
        if (states == null || states.isEmpty()) {
            throw new IllegalArgumentException("StateMachine needs at least one state");
        }
    }
}
