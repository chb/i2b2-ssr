package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;


public class LoginWindow extends Composite {


    interface MyUiBinder extends UiBinder<Widget, LoginWindow> {
    }


    public interface LoginListener {
        public void onSuccessfulLogin(String sessionId);
    }


    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);


    @UiField
    TextBox loginBox;

    @UiField
    TextBox passwordBox;

    @UiField
    DialogBox box;

    @UiField
    Button buttonSubmit;

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    private LoginListener listener;


    public LoginWindow() {
        initWidget(binder.createAndBindUi(this));
        box.center();
        box.show();

    }

    @UiHandler({"passwordBox","loginBox"})
    public void onKeyPress(KeyPressEvent keyPressEvent) {
        if (keyPressEvent.getCharCode() == KeyCodes.KEY_ENTER) {
            attemptLogin();
        }
    }

    @UiHandler("buttonSubmit")
    void handleClick(ClickEvent e) {
        attemptLogin();
    }

    private void attemptLogin() {
        AsyncCallback callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Error");
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(String result) {
                if (result.length() > 0) {
                    if (listener != null) {
                        listener.onSuccessfulLogin(loginBox.getText());
                        box.hide();

                    }

                }
            }
        };

        AuthServiceAsync authService =
                (AuthServiceAsync) GWT.create(AuthService.class);
        authService.authorizeUser(loginBox.getText(),
                passwordBox.getText(),
                callback);
    }

    public void show() {
        loginBox.setText("");
        passwordBox.setText("");
        box.show();
    }


}
