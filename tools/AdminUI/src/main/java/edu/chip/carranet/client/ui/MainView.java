package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.Application;
import edu.chip.carranet.client.HistoryToken;
import edu.chip.carranet.client.MenuCommand;


public class MainView extends Composite {


    interface MyUiBinder extends UiBinder<Panel, MainView> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);
    @UiField
    MenuBar menuBar;

    @UiField
    MenuItem addRemUser;

    @UiField
    MenuItem manageStudies;

    @UiField
    MenuItem manageMachines;

    @UiField
    MenuItem clusterStatus;

    @UiField
    MenuItem logout;

    @UiField
    Panel layout;

    @UiField
    Panel content;

    @UiField
    Image logo;


    public MainView() {
        initWidget(binder.createAndBindUi(this));
        addRemUser.setCommand(new MenuCommand(HistoryToken.ManageUsers));
        manageStudies.setCommand(new MenuCommand(HistoryToken.ManageStudies));
        manageMachines.setCommand(new MenuCommand(HistoryToken.ManageMachines));
        clusterStatus.setCommand(new MenuCommand(HistoryToken.ClusterStatus));
        logout.setCommand(new Command() {


            @Override
            public void execute() {
                Cookies.removeCookie(Application.COOKIE_NAME);
                Window.open(GWT.getHostPageBaseURL(), "_self", null);

            }
        });
    }


    public void clearContent() {
        content.clear();
    }

    public void setContent(Widget c) {
        content.clear();
        content.add(c);
    }

}
