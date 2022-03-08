package net.zerjio.toolbox.messagebus;

import java.util.Optional;

public interface MessageRequest {

    MessageRequest with(Object message);

    MessageRequest onSuccess(MessageHandlerOnSuccess handler);

    MessageRequest onError(MessageHandlerOnError handler);

    String address();

    Optional<Object> message();

    MessageHandlerOnSuccess onSuccessHandler();

    MessageHandlerOnError onErrorHandler();

    boolean isBroadcast();

}
