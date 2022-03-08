package net.zerjio.toolbox.statemachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicStateMachine<T, E> implements StateMachine<T, E> {

    private class StateChange<T> {

        T   state;

        TransitionHandler handler;

        public StateChange(T state, TransitionHandler handler) {
            this.state = state;
            this.handler = handler;
        }
    }

    private boolean isActive;

    private T currentState;

    private T endState;

    private TransitionHandler onEndHandler;

    private TransitionLostHandler onLostEventHandle;

    private Map<T, Map<E, StateChange<T>>> statesGraph;

    public BasicStateMachine(
        List<T> states,
        List<StateMachineBuilder<T, E>.Transition> transitions,
        T beginState,
        T endState,
        TransitionHandler onEndHandler,
        TransitionLostHandler onLostEventHandler
    ) {
        this.isActive = true;
        this.currentState = beginState;
        this.endState = endState;
        this.onEndHandler = onEndHandler;
        this.onLostEventHandle = onLostEventHandler;

        statesGraph = new HashMap<>();
        states.forEach(state -> statesGraph.put(state, new HashMap<>()));
        transitions.forEach(transition -> {
            Map<E, StateChange<T>> from = statesGraph.get(transition.from());
            from.put(transition.when(), new StateChange<>(transition.to(), transition.handler()));
        });
    }

    @Override
    public T event(E event) {
        if (isActive) {
            Map<E, StateChange<T>> from = statesGraph.get(currentState);
            StateChange<T> to = from.get(event);
            if (to != null) {
                to.handler.handle(currentState, to.state, event);
                if (to.state == endState) {
                    onEndHandler.handle(currentState, to.state, event);
                    isActive = false;
                }
                currentState = to.state;
            } else {
                onLostEventHandle.handle(event);
            }
        }
        return currentState;
    }

    @Override
    public T currentState() {
        return currentState;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
