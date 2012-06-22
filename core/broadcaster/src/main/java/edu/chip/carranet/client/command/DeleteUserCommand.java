package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;

/**
 * This command deletes a user
 */
public class DeleteUserCommand implements CarraCommand {

    private UserDTO u;

    public DeleteUserCommand(UserDTO user) {
        this.u = user;
    }

    public void execute() {
        AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);

        authService.deleteUser(u,
                new CarraAsyncCallback<UserDTO>(MessageType.UserDeleted, this));

    }

    public UserDTO getU() {
        return u;
    }
}
