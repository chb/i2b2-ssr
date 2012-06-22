package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.DeleteUserCommand;
import edu.chip.carranet.client.command.GetUsersCommand;
import edu.chip.carranet.client.data.GWTUserPermissions;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;
import edu.chip.carranet.client.ui.style.CarraStyle;
import edu.chip.carranet.client.ui.style.TableStyle;

import java.util.List;

public class ManageUsers extends Composite {


    interface MyUiBinder extends UiBinder<Widget, ManageUsers> {
    }


    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    //This event handler checks for new updates and
    private EventBusHandler mangeUserEventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {
            if (e.getType().equals(MessageType.UserCreated)) {
                addRowToUserTable(((UserDTO) e.getResponse()));
            }
            if (e.getType().equals(MessageType.UserDeleted)) {
                deleteRowFromUserTable(((DeleteUserCommand) e.getRequest()).getU());
            }
            if (e.getType().equals(MessageType.UsersListed)) {
                paintTable((List<UserDTO>) e.getResponse());
            }

            if (e.getType().equals(MessageType.UpdateUser)) {
                updateUserStudies((UserDTO) e.getResponse());
                updateUserRoles((UserDTO) e.getResponse());
            }


        }
    };


    @UiField
    FlexTable userTable;

    @UiField
    NewUserWindow newUserWindow;

    @UiField
    Button addNewUser;

    @UiField
    CarraStyle carraStyle;

    @UiField
    TableStyle tableStyle;

    @UiField
    EditUserWindow editUserWindow;

    private class BooleanHolder {
        public boolean booleanVal;

        public BooleanHolder(boolean bool) {
            this.booleanVal = bool;
        }


    }

    //let's make the assumption for now that LDAP backends
    //aren't the norm(except they are)

    private final BooleanHolder canManagePasswords = new BooleanHolder(false);


    public ManageUsers() {
        GWT.log("Bringing up user page");
        initWidget(binder.createAndBindUi(this));

        GetUsersCommand getUsersCommand = new GetUsersCommand();
        getUsersCommand.execute();
        userTable.setStyleName(tableStyle.carraTable());

        //SOOOO hacky but we need to send a quick message on page load
        //to figure out if the backend is going to let us manage passwords
        //this isn't totally message bus appropriate because who the heck else
        //cares if the password store is ldap backed?

        AuthServiceAsync service = (AuthServiceAsync) GWT.create(AuthService.class);
        service.canManagePasswords(new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Can't get capabilities from auth servers");
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                canManagePasswords.booleanVal = aBoolean;
            }
        });


    }

    private void paintTable(List<UserDTO> users) {
        if (users == null) {
            return;
        }
        userTable.clear();
        int count = 1;

        userTable.setText(0, 0, "Username");
        userTable.setText(0, 1, "Studies");
        userTable.setText(0, 2, "Delete User");
        userTable.setText(0, 3, "Edit User");
        userTable.setText(0, 4, "Administrator");
        userTable.setText(0, 5, "PDO");
        userTable.setText(0, 6, "Site Breakdown");


        for (UserDTO u : users) {
            addRowToUserTable(u);
        }

    }

    private void addRowToUserTable(final UserDTO user) {
        Button b = new Button("x");

        int row = userTable.getRowCount();
        userTable.setText(row, 0, user.getUserName());
        userTable.setText(row, 1, user.getStudies().toString());
        userTable.setWidget(row, 2, b);
        drawRowPermissions(user, row);


        b.addClickHandler(
                new DeleteUserClickHandler(user.getUserName()));


        final Button editUserButton = new Button("Edit User");
        editUserButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {

                int xcord = editUserButton.getAbsoluteLeft() + editUserButton.getOffsetWidth();
                int ycord = editUserButton.getAbsoluteTop();
                editUserWindow.show(xcord, ycord, user.getUserName());
            }
        });

        userTable.setWidget(row, 3, editUserButton);

        TableUtil.paintRows(tableStyle, userTable);


    }

    private void deleteRowFromUserTable(UserDTO u) {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            String userName = userTable.getText(i, 0);
            if (u.getUserName().equals(userName)) {
                userTable.removeRow(i);
            }
        }
        TableUtil.paintRows(tableStyle, userTable);
    }


    @UiHandler("addNewUser")
    public void showNewUserPopup(ClickEvent e) {

        Button b = (Button) e.getSource();

        int cordx = b.getAbsoluteLeft() + b.getOffsetWidth();
        int cordy = b.getAbsoluteTop();

        newUserWindow.setCanChangePasswords(canManagePasswords.booleanVal);
        newUserWindow.show(cordx, cordy);
    }

    private int findUserRow(String userName) {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            String u = userTable.getText(i, 0);
            if (u.equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    private void updateUserStudies(UserDTO user) {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            String u = userTable.getText(i, 0);
            if (u.equals(user.getUserName())) {
                userTable.setText(i, 1, user.getStudies().toString());
            }
        }

    }

    private void drawRowPermissions(UserDTO user, int row) {
        if (user.getRoles().contains(GWTUserPermissions.admin)) {
            userTable.setWidget(row, 4, new Image("images/Good.png"));
        } else {
            userTable.setWidget(row, 4, new Image("images/Bad.png"));
        }

        if (user.getRoles().contains(GWTUserPermissions.pdo)) {
            userTable.setWidget(row, 5, new Image("images/Good.png"));
        } else {
            userTable.setWidget(row, 5, new Image("images/Bad.png"));
        }

        if (user.getRoles().contains(GWTUserPermissions.sitebreakdown)) {
            userTable.setWidget(row, 6, new Image("images/Good.png"));
        } else {
            userTable.setWidget(row, 6, new Image("images/Bad.png"));
        }

    }

    private void updateUserRoles(UserDTO user) {
        for (int i = 0; i < userTable.getRowCount(); i++) {
            String u = userTable.getText(i, 0);
            if (u.equals(user.getUserName())) {
                drawRowPermissions(user, i);
            }
        }
    }


    @Override
    protected void onLoad() {
        EventBus.getInstance().addHandler(mangeUserEventHandler);
    }

    @Override
    protected void onUnload() {
        EventBus.getInstance().removeHandler(mangeUserEventHandler);
    }

}


