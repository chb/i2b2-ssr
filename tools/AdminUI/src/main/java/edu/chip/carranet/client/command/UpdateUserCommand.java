package edu.chip.carranet.client.command;


import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;

public class UpdateUserCommand implements CarraCommand {

    private UserDTO user;

    public UpdateUserCommand(UserDTO u) {
        user = u;

    }

    @Override
    public void execute() {
        final AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);

        authService.updateUser(user, new CarraAsyncCallback<UserDTO>(MessageType.UpdateUser, this));
    }
}
