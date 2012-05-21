package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.CreateStudyCommand;
import edu.chip.carranet.client.command.GetMachinesCommand;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;

import java.util.ArrayList;
import java.util.List;


public class NewStudyWindow extends Composite {

    interface MyUiBinder extends UiBinder<Widget, NewStudyWindow> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    //This event handler checks for new updates and
    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {
            if(e.getType().equals(MessageType.MachinesListed)) {
                GWT.log("handling got ols machines");
                List<MachineDTO> machines = (List<MachineDTO>)e.getResponse();
                populateMachineList(machines);
                dataMachineList = machines;
            }
        }
    };

    @UiField
    Button cancel;

    @UiField
    PopupPanel newStudyPopup;

    @UiField
    Button submitNewStudy;

    @UiField
    TextBox studyNameField;

    @UiField
    ListBox machineList;

    List<MachineDTO> dataMachineList;

    /**
     * <p>
     * Creates a new StudyDTO window
     * </p>     
     */
    public NewStudyWindow() {
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
        newStudyPopup.hide();
    }

    public void show(int xcord, int ycord) {
        newStudyPopup.setPopupPosition(xcord, ycord);
        newStudyPopup.show();
    }
    
    @UiHandler("submitNewStudy")
    public void submitHandler(ClickEvent e) {
        // get edu.chip.carranet.data
        String username = studyNameField.getValue();
        List<String> machines = new ArrayList<String>();

        for(int i = 0, size = machineList.getItemCount(); i < size; ++i) {
            if(machineList.isItemSelected(i)) {
                machines.add(dataMachineList.get(i).getMachineName());
            }
        }

        // make update call
        CreateStudyCommand command = new CreateStudyCommand(username, machines);
        command.execute();

        newStudyPopup.hide();
        clearValues();
    }

    @UiHandler("cancel")
    public void clearHandler(ClickEvent e) {
        newStudyPopup.hide();
        clearValues();
    }

    private void clearValues() {
        studyNameField.setValue(null);
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
