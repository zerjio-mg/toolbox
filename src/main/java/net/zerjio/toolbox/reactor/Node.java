package net.zerjio.toolbox.reactor;

public interface Node {

    Node init(Reactor reactor);

    Reactor reactor();

    String name();

    Node start();

    Node stop();
}
