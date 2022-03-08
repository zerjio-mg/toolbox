package net.zerjio.toolbox.reactor;

public abstract class AbstractNode implements Node {

    private String name;

    private Reactor reactor;

    public AbstractNode(String name) {
        this.name = name;
    }

    @Override
    public Node init(Reactor reactor) {
        this.reactor = reactor;
        return this;
    }

    @Override
    public final Reactor reactor() {
        return reactor;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public Node start() {
        return this;
    }

    @Override
    public Node stop() {
        return this;
    }

}
