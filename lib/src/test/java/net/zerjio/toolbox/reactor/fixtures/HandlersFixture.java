package net.zerjio.toolbox.reactor.fixtures;

import net.zerjio.toolbox.reactor.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandlersFixture  {

    public class SingleHandler implements Handler {

        private String name;

        private long created;

        private long called;

        public SingleHandler(String name) {
            this.name = name;
            this.created = System.currentTimeMillis();
        }

        @Override
        public void run() {
            this.called = System.currentTimeMillis();
            handlersCalled.add(this);
        }

        public long delayed() {
            return called - created;
        }

        @Override
        public String toString() {
            return String.format("SingleHandler{%s, delayed: %d}", name, delayed());
        }
    }

    List<SingleHandler> handlers;

    List<SingleHandler> handlersCalled;

    public HandlersFixture(List<String> names) {
        this.handlers = new ArrayList<>();
        this.handlersCalled = Collections.synchronizedList(new ArrayList<>());

        for(String name: names) {
            this.handlers.add(new SingleHandler(name));
        }
    }

    public List<SingleHandler> all() {
        return handlers;
    }

    public SingleHandler get(int i) {
        return handlers.get(i);
    }

    public List<SingleHandler> handlersCalled() {
        return handlersCalled;
    }

    @Override
    public String toString() {
        return String.format("HandlerFixture{%d}", handlers.size());
    }
}
