package net.zerjio.toolbox.messagebus;

import java.util.Optional;

public class BasicMessageRequest implements MessageRequest {

    private final Optional<Object> NO_MESSAGE = Optional.empty();

    private final String address;

    private Optional<Object> message;

    private MessageHandlerOnSuccess onSuccessHandler;

    private MessageHandlerOnError onErrorHandler;

    private final boolean isBroadcast;

    public static MessageRequest forOneSubscriber(String address) {
        return new BasicMessageRequest(address, false);
    }

    public static MessageRequest forAllSubscribers(String address) {
        return new BasicMessageRequest(address, true);
    }

    private BasicMessageRequest(String address, boolean isBroadcast) {
        this.address = address;
        this.message = NO_MESSAGE;
        this.onSuccessHandler = this::onSuccessHandle;
        this.onErrorHandler = this::onErrorHandle;
        this.isBroadcast = isBroadcast;
    }

    @Override
    public MessageRequest with(Object message) {
        this.message = (message == null)? NO_MESSAGE : Optional.of(message);
        return this;
    }

    @Override
    public MessageRequest onSuccess(MessageHandlerOnSuccess handler) {
        onSuccessHandler = handler;
        return this;
    }

    @Override
    public MessageRequest onError(MessageHandlerOnError handler) {
        onErrorHandler = handler;
        return this;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public Optional<Object> message() {
        return message;
    }

    @Override
    public MessageHandlerOnSuccess onSuccessHandler() {
        return onSuccessHandler;
    }

    @Override
    public MessageHandlerOnError onErrorHandler() {
        return onErrorHandler;
    }

    @Override
    public boolean isBroadcast() {
        return isBroadcast;
    }

    private void onSuccessHandle(Optional<Object> response) {
    }

    private void onErrorHandle(Exception error) {
    }

    @Override
    public String toString() {
        return String.format("BasicMessageRequest{address: %s, hasMessage: %b}", address, message.isPresent());
    }
}
