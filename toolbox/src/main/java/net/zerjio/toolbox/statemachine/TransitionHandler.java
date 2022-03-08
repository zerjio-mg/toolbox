package net.zerjio.toolbox.statemachine;

public interface TransitionHandler<T, E> {

    void handle(T from, T to, E event);
}
