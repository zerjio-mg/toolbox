package net.zerjio.toolbox.statemachine;

public interface StateMachine<T, E> {

    T event(E event);

    T currentState();

    boolean isActive();

}
