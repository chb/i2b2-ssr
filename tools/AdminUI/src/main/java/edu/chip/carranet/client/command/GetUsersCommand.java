package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;

import java.util.ArrayList;


public class GetUsersCommand implements CarraCommand {


    public void execute() {
        AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);

        authService.getUsers(
                new CarraAsyncCallback<ArrayList<UserDTO>>(MessageType.UsersListed, this));
    }


}
