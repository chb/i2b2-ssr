package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;


public class AuthenticateUserCommand implements CarraCommand {

    private String userName;
    private String password;

    public AuthenticateUserCommand(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void execute() {

        AuthServiceAsync service = (AuthServiceAsync) GWT.create(AuthService.class);

        service.authorizeUser(userName, password,
                new CarraAsyncCallback<String>(MessageType.UserAuthenticated, this));


    }
}
