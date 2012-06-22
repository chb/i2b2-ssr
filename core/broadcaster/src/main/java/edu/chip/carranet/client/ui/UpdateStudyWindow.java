package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.GetMachinesCommand;
import edu.chip.carranet.client.command.UpdateStudyCommand;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class UpdateStudyWindow extends Composite {
    interface MyUiBinder extends UiBinder<Widget, UpdateStudyWindow> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    //This event handler checks for new updates and
    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {
            if(e.getType().equals(MessageType.MachinesListed)) {
                List<MachineDTO> machines = (List<MachineDTO>)e.getResponse();
                populateMachineList(machines);
                dataMachineList = machines;
            }
        }
    };

    @UiField
    PopupPanel updateStudyPopup;

    @UiField
    Label studyName;

    @UiField
    ListBox machineList;

    @UiField
    Button submitUpdatedStudy;

    @UiField
    Button cancel;

    List<MachineDTO> dataMachineList;

    /**
     * <p>
     * Creates a new StudyDTO window
     * </p>
     */
    public UpdateStudyWindow() {
        initWidget(binder.createAndBindUi(this));

        // fetch machine list
        GetMachinesCommand getMachinesCommand = new GetMachinesCommand();
        getMachinesCommand.execute();
    }

    @Override
    protected void onLoad() {
        EventBus.getInstance().addHandler(eventHandler);
    }

    @Override
    protected void onUnload() {
        hide();
        EventBus.getInstance().removeHandler(eventHandler);
    }

    public void hide() {
        updateStudyPopup.hide();
    }


    public void show(StudyDTO s, int cordx, int cordy) {
        clearValues();
        updateStudyPopup.setPopupPosition(cordx,cordy);
        studyName.setText(s.getStudyName());
        for(int i = 0; i < dataMachineList.size(); ++i) {
            for(String m : s.getStudyMachines()) {
                if(dataMachineList.get(i).getMachineName().equals(m)) {
                    machineList.setItemSelected(i, true);
                    break;
                }
            }
        }

        updateStudyPopup.center();
        updateStudyPopup.show();
    }

    @UiHandler("submitUpdatedStudy")
    public void submitHandler(ClickEvent e) {
        // get edu.chip.carranet.data
        String studyId = this.studyName.getText();
        List<String> machines = new ArrayList<String>();

        for(int i = 0, size = machineList.getItemCount(); i < size; ++i) {
            if(machineList.isItemSelected(i)) {
                machines.add(dataMachineList.get(i).getMachineName());
            }
        }

        StudyDTO updatedStudy = new StudyDTO(studyId, machines);
        // make update call
        new UpdateStudyCommand(studyId, updatedStudy).execute();
        updateStudyPopup.hide();
        clearValues();
    }

    @UiHandler("cancel")
    public void clearHandler(ClickEvent e) {
        updateStudyPopup.hide();
        clearValues();
    }

    private void clearValues() {
        studyName.setText(null);
        for(int i = 0, size = machineList.getItemCount(); i < size; ++i) {
            machineList.setItemSelected(i, false);
        }
    }

    private void populateMachineList(List<MachineDTO> machines) {
        machineList.clear();
        for(MachineDTO m : machines) {
            machineList.addItem(m.getMachineName());
        }
        machineList.setVisibleItemCount(machines.size());
    }
}
