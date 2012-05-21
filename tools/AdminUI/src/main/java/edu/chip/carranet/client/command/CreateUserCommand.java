package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.client.data.GWTUserPermissions;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;

import java.util.List;


public class CreateUserCommand implements CarraCommand {
    private String userName;
    private String password;
    private List<GWTUserPermissions> roles;
    private List<String> studies;


    public CreateUserCommand(String userName, String password, List<GWTUserPermissions> roles, List<String> studies) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.studies = studies;

    }

    public void execute() {
        AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);


        UserDTO u = new UserDTO();
        u.setUserName(userName);
        u.setRoles(roles);
        u.setStudies(studies);

        authService.addUser(u, password,
                new CarraAsyncCallback<UserDTO>(MessageType.UserCreated, this));
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
