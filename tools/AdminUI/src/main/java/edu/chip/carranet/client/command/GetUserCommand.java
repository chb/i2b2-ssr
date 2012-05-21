package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;

import java.util.ArrayList;

public class GetUserCommand implements CarraCommand {

    private String userName;

    /**
     *
     * @param userName
     */
    public GetUserCommand(String userName){
     this.userName = userName;
    }

    @Override
    public void execute() {
         AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);

         authService.getUser(userName, new CarraAsyncCallback<UserDTO>(MessageType.UserInfo, this));
    }
}
