package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;


public class CarraAsyncCallback<T> implements AsyncCallback<T> {

    private MessageType messageType;
    private Object request;

    public CarraAsyncCallback(MessageType messageType, Object request) {
        this.messageType = messageType;
        this.request = request;
    }

    @Override
    public void onFailure(Throwable caught) {
        //TODO - insert common error handling code here
        GWT.log("RPC call failed with error " + caught.getMessage());
    }

    @Override
    public void onSuccess(T result) {
        GWT.log(messageType.name() + "recieved from server");
        EventBusMessage m = new EventBusMessage(messageType, request, result);
        EventBus.getInstance().fireEvent(m);
    }
}
