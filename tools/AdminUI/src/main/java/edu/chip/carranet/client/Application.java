package edu.chip.carranet.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.client.service.AuthServiceAsync;
import edu.chip.carranet.client.ui.*;

import java.util.Date;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
        implements EntryPoint {

    public static final String COOKIE_NAME = "sid";
    private static final int COOKIE_TIMEOUT = 1000 * 60 * 20; //Milliseconds till timeout

    LoginWindow loginWindow;
    MainView mainWindow;
    Date lastChecked = new Date();
    AuthServiceAsync service = (AuthServiceAsync) GWT.create(AuthService.class);

    AsyncCallback<Void> logoutCallback = new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
            //even if the server doesn't
            //respond we're still logged out, the session
            //will have to timeout on it's own
            Cookies.removeCookie(COOKIE_NAME);
        }

        @Override
        public void onSuccess(Void result) {
            Cookies.removeCookie(COOKIE_NAME);
            loginWindow.show();
        }
    };


    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {


        GWT.log("Application starting up...");
        RootPanel rootPanel = RootPanel.get();

        //setup the root window
        mainWindow = new MainView();
        rootPanel.add(mainWindow);

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                navigateTo(event.getValue());
            }
        });

        if (!isLoggedIn()) {
            loginWindow = new LoginWindow();
            //Setup event Listeners

            loginWindow.setListener(new LoginWindow.LoginListener() {
                @Override
                public void onSuccessfulLogin(String sessionId) {
                    GWT.log("Logged in successfully");
                    Cookies.setCookie(COOKIE_NAME, sessionId, createCookieExpirationDate());
                    if (!History.getToken().equals(HistoryToken.LogOut)) {
                        navigateTo(History.getToken());
                    } else {
                        GWT.log("Some smart-alec bookmarked the #Logout resource...brilliant");
                    }
                }
            });

            mainWindow.setContent(loginWindow);
        } else {
            navigateTo(History.getToken());

        }


    }

    private void navigateTo(String s) {
        if (!isLoggedIn()) {
            return;
        }

        if (s.equals(HistoryToken.ManageUsers.toString())) {
            mainWindow.setContent(new ManageUsers());
        }

        if (s.equals(HistoryToken.ManageStudies.toString())) {
            mainWindow.setContent(new ManageStudies());
        }
        if (s.equals(HistoryToken.ManageMachines.toString())) {
            mainWindow.setContent(new ManageMachines());
        }
        if (s.equals(HistoryToken.ClusterStatus.toString())) {
            mainWindow.setContent(new ClusterStatus());
        }


    }

    private boolean isLoggedIn() {
        String loggedIn = Cookies.getCookie(COOKIE_NAME);
        GWT.log("Is user logged in?");

        if (loggedIn != null) {
            //Check Cookie session map
            GWT.log("use is logged in");


            return true;
        } else {
            GWT.log("use is not logged in");
            return false;

        }
    }

    /**
     * After we check our browser cookie to make sure
     * we're still logged in, we fire off something to the server to make sure
     * we're still logged in.  The issue is in the server there's a timed token
     * so we have to check for a while to make sure we're still alive
     */
    private void verifyServerSession() {
        service.isSessionActive(Cookies.getCookie(COOKIE_NAME), new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                Cookies.removeCookie(COOKIE_NAME);
                loginWindow.show();
            }

            @Override
            public void onSuccess(Boolean result) {
                if (!result) {
                    Cookies.removeCookie(COOKIE_NAME);

                    loginWindow.show();
                }
            }
        });

    }

    private Date createCookieExpirationDate() {
        Date date = new Date();
        date.setTime(date.getTime() + COOKIE_TIMEOUT);
        return date;
    }


}
