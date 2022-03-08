package net.zerjio.toolbox.messagebus;

public interface MessageHandlerOnError {

    void handle(Exception error);
}
