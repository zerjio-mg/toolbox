package net.zerjio.toolbox.messagebus;

import java.util.Optional;

public interface Subscriber {

    Object handle(Optional<Object> message);
}
