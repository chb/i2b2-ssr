package edu.chip.carranet.client.event;

/**
 * This is a message that travels across the GWT message bus.
 * It has a MessageType that describes the concrete classes of the
 * request and response objects so handlers have the full context
 * available to deal with the event.
 */
public class EventBusMessage {

    private MessageType type;
    private Object request;
    private Object response;

    public EventBusMessage(MessageType type, Object request, Object response) {
        this.type = type;
        this.request = request;
        this.response = response;
    }

    public MessageType getType() {
        return type;
    }

    public Object getRequest() {
        return request;
    }

    public Object getResponse() {
        return response;
    }

}
