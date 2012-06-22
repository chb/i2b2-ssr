package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.client.command.GetMachinesCommand;
import edu.chip.carranet.client.command.GetStudiesCommand;
import edu.chip.carranet.client.command.GetUserCommand;
import edu.chip.carranet.client.command.UpdateUserCommand;
import edu.chip.carranet.client.data.GWTUserPermissions;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Popup window to edit the studies that a user participates in.
 */
public class EditUserWindow extends Composite {

    interface MyUiBinder extends UiBinder<Widget, EditUserWindow> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    private static final String NO_SITE_STRING = "No home site";

    private String userName;

    protected String userHomeSite;

    private Map<String, MachineDTO> machineMap = new HashMap<String, MachineDTO>();

    @UiField
    PopupPanel editUserPopup;

    @UiField
    ListBox userStudyField;

    @UiField
    Button updateUserButton;

    @UiField
    Button cancelButton;

    @UiField
    CheckBox isAdmin;

    @UiField
    CheckBox isPDO;

    @UiField
    CheckBox isSiteBreakdownEnabled;

    @UiField
    ListBox homeSiteListBox;

    public EditUserWindow() {
        initWidget(binder.createAndBindUi(this));
        setVisible(false);

    }

    public void hide() {
        editUserPopup.hide();
        userStudyField.clear();

    }

    public void show(int xcord, int ycord, String userName) {
        homeSiteListBox.clear();
        homeSiteListBox.addItem(NO_SITE_STRING);
        isAdmin.setValue(false);
        isPDO.setValue(false);
        isSiteBreakdownEnabled.setValue(false);
        //We chain commands together in the event handler down below
        //doing a GetStudiesCommand cascades into other commands
        //It's a dumb way enforce serial steps and I'd probably
        //be better off making a edu.chip.carranet.data object that just gives me everything
        //I need in one shot
        GetStudiesCommand studyCommand = new GetStudiesCommand();
        studyCommand.execute();

        //Populate the listbox
        if(homeSiteListBox.getItemCount() == 1) {

        }

        this.userName = userName;
        editUserPopup.setPopupPosition(xcord, ycord);

    }

    @UiHandler("updateUserButton")
    public void handleSubmitClick(ClickEvent e) {
        List<String> studies = new ArrayList<String>();
        List<GWTUserPermissions> roles = new ArrayList<GWTUserPermissions>();

        for(int i = 0; i < userStudyField.getItemCount(); i++) {
            if(userStudyField.isItemSelected(i)) {
                studies.add(userStudyField.getItemText(i));
            }
        }

        if(isAdmin.getValue()) {
            roles.add(GWTUserPermissions.admin);
        }
        if(isPDO.getValue()) {
            roles.add(GWTUserPermissions.pdo);
        }
        if(isSiteBreakdownEnabled.getValue()) {
            roles.add(GWTUserPermissions.sitebreakdown);
        }

        int selectedIndex = homeSiteListBox.getSelectedIndex();
        String machineString = homeSiteListBox.getItemText(selectedIndex);
        String homeSiteHostname = null;

        if(!machineString.equals(NO_SITE_STRING)) {
            MachineDTO m = machineMap.get(machineString);
            String s = m.getUri().replace("\t", "");
            homeSiteHostname = extractHostnameFromUrl(s);
        }

        UserDTO u = new UserDTO();
        u.setUserName(userName);
        u.setRoles(roles);
        u.setStudies(studies);
        u.setHomeSites(Arrays.asList(new String[]{homeSiteHostname}));
        UpdateUserCommand command = new UpdateUserCommand(u);

        command.execute();
        hide();

    }

    @UiHandler("cancelButton")
    public void handleCancelClick(ClickEvent e) {
        hide();
    }

    @Override
    public void onLoad() {
        EventBus.getInstance().addHandler(eventHandler);
    }

    @Override
    public void onUnload() {
        EventBus.getInstance().removeHandler(eventHandler);
    }

    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {

            if(e.getType().equals(MessageType.StudiesListed)) {
                List<StudyDTO> studies = (List<StudyDTO>) e.getResponse();
                for(StudyDTO s : studies) {
                    userStudyField.addItem(s.getStudyName());
                }
                GetUserCommand command = new GetUserCommand(userName);
                command.execute();
            }

            if(e.getType().equals(MessageType.UserInfo)) {
                UserDTO dto = (UserDTO) e.getResponse();
                for(int i = 0; i < userStudyField.getItemCount(); i++) {
                    if(dto.getStudies()
                            .contains(userStudyField.getItemText(i))) {
                        userStudyField.setItemSelected(i, true);
                    }
                }
                if(dto.getRoles().contains(
                        GWTUserPermissions.admin)) {
                    isAdmin.setValue(true);
                }
                if(dto.getRoles().contains(
                        GWTUserPermissions.pdo)) {
                    isPDO.setValue(true);
                }
                if(dto.getRoles().contains(
                        GWTUserPermissions.sitebreakdown)) {
                    isSiteBreakdownEnabled.setValue(true);
                }
                if(dto.getHomeSites().size() > 0) {
                    userHomeSite = dto.getHomeSites().get(0);
                }

                GetMachinesCommand machinesCommand = new GetMachinesCommand();
                machinesCommand.execute();


            }
            if(e.getType().equals(MessageType.MachinesListed)) {
                List<MachineDTO> machineDTOs = (List<MachineDTO>) e
                        .getResponse();
                for(MachineDTO machine : machineDTOs) {
                    machineMap.put(machine.getMachineName(), machine);
                    homeSiteListBox.addItem(machine.getMachineName());
                    if(extractHostnameFromUrl(machine.getUri()).equals(userHomeSite)) {
                        homeSiteListBox.setSelectedIndex(homeSiteListBox.getItemCount() - 1);
                    }
                }

                editUserPopup.show();
            }

        }
    };

    private String extractHostnameFromUrl(String url) {
        RegExp regex = RegExp.compile("(?:([^:\\/?#]+):)?(?:\\/\\/((?:(([^:@]*)(?::([^:@]*))?)?@)?([^:\\/?#]*)(?::(\\d*))?))?((((?:[^?#\\/]*\\/)*)([^?#]*))(?:\\?([^#]*))?(?:#(.*))?)");
        MatchResult match = regex.exec(url);
        return match.getGroup(6);
    }

}
