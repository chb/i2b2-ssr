package edu.chip.carranet.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import edu.chip.carranet.client.command.DeleteUserCommand;
import edu.chip.carranet.client.data.UserDTO;


public class DeleteUserClickHandler implements ClickHandler {


    String userName;

    public DeleteUserClickHandler(String userName) {
        this.userName = userName;
    }

    @Override
    public void onClick(ClickEvent event) {
        UserDTO u = new UserDTO();
        u.setUserName(userName);

        DeleteUserCommand command = new DeleteUserCommand(u);
        command.execute();
    }
}
