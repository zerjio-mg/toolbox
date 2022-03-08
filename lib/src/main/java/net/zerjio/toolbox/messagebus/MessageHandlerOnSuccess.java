package net.zerjio.toolbox.messagebus;

import java.util.Optional;

public interface MessageHandlerOnSuccess {

    void handle(Optional<Object> response);
}
