package net.zerjio.toolbox.statemachine;

public interface TransitionLostHandler<E> {

    void handle(E event);
}
