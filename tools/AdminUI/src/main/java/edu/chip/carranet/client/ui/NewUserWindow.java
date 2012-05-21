package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.CreateUserCommand;
import edu.chip.carranet.client.data.GWTUserPermissions;

import java.util.ArrayList;
import java.util.List;


public class NewUserWindow extends Composite {

    interface MyUiBinder extends UiBinder<Widget, NewUserWindow> {
    }


    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    @UiField
    TextBox userNameField;

    @UiField
    TextBox passwordField;

    @UiField
    TextBox passwordAgainField;

    @UiField
    PopupPanel newUserPopup;

    @UiField
    Button submitNewUser;

    @UiField
    CheckBox isAdmin;

    @UiField
    CheckBox isPDO;

    @UiField
    CheckBox isSiteIdentifiable;

    @UiField
    Button cancel;

    @UiField
    Label passwordLabel;

    @UiField
    Label usernameLabel;

    private Boolean canChangePasswords;

    public NewUserWindow() {
        initWidget(binder.createAndBindUi(this));


        isSiteIdentifiable.setVisible(false);
        isPDO.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                if (booleanValueChangeEvent.getValue().equals(true)) {
                    isSiteIdentifiable.setVisible(true);
                } else {
                    isSiteIdentifiable.setValue(false);
                    isSiteIdentifiable.setVisible(false);
                }
            }
        });
    }


    @UiHandler("submitNewUser")
    public void userAddButton(ClickEvent e) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String passwordAgain = passwordAgainField.getText();


        if (canChangePasswords && (userName == null || password == null || passwordAgain == null)) {
            //POPUP SOME STATUS
            return;
        }

        if (!canChangePasswords || password.equals(passwordAgain)) {

            List<GWTUserPermissions> roles = new ArrayList<GWTUserPermissions>();


            if (isAdmin.getValue()) {
                roles.add(GWTUserPermissions.admin);
            }

            if (isPDO.getValue()) {
                roles.add(GWTUserPermissions.pdo);
            }

            if (isSiteIdentifiable.getValue()) {
                roles.add(GWTUserPermissions.sitebreakdown);

            }

            CreateUserCommand command = new CreateUserCommand(userName, password, roles, new ArrayList<String>());
            command.execute();
        }
        newUserPopup.hide();
        clearValues();
    }

    @UiHandler("cancel")
    public void cancelButton(ClickEvent e) {
        newUserPopup.hide();
        clearValues();
    }

    public void hide() {
        newUserPopup.hide();
        clearValues();
    }

    public void show(int x, int y) {
        if (!canChangePasswords) {
            passwordLabel.setVisible(false);
            usernameLabel.setVisible(false);
            passwordField.setVisible(false);
            passwordAgainField.setVisible(false);
        }

        newUserPopup.setPopupPosition(x, y);
        newUserPopup.show();
    }

    @Override
    protected void onUnload() {
        hide();
    }

    private void clearValues() {
        userNameField.setValue(null);
        passwordField.setValue(null);
        passwordAgainField.setValue(null);
    }

    public void setCanChangePasswords(Boolean has) {
        this.canChangePasswords = has;
    }

}
