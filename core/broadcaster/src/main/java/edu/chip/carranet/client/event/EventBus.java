package edu.chip.carranet.client.event;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Event Bus Implementation
 */
public class EventBus {
    private static EventBus instance = new EventBus();
    private Set<EventBusHandler> eventHandlers = new HashSet<EventBusHandler>();

    public static EventBus getInstance() {
        return instance;
    }

    public synchronized void fireEvent(EventBusMessage e) {
        for (EventBusHandler handler : eventHandlers) {
            handler.handle(e);
        }

    }

    public synchronized void addHandler(EventBusHandler handler) {
        eventHandlers.add(handler);
    }

    public synchronized void removeHandler(EventBusHandler handler) {
        eventHandlers.remove(handler);
    }

}
