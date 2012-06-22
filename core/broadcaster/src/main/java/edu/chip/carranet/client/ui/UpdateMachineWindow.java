package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.GetMachinesCommand;
import edu.chip.carranet.client.command.UpdateMachineCommand;
import edu.chip.carranet.client.data.MachineDTO;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class UpdateMachineWindow extends Composite {
    interface MyUiBinder extends UiBinder<Widget, UpdateMachineWindow> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    @UiField
    PopupPanel updateMachinePopup;

    @UiField
    Label machineName;

    @UiField
    TextBox hostnameField;

    @UiField
    Button submitUpdatedMachine;

    @UiField
    Button cancel;

    public UpdateMachineWindow() {
        initWidget(binder.createAndBindUi(this));

        // fetch machine list
        GetMachinesCommand getMachinesCommand = new GetMachinesCommand();
        getMachinesCommand.execute();
    }

    @Override
    protected void onLoad() {
    }

    @Override
    protected void onUnload() {
        hide();
    }

    public void hide() {
        updateMachinePopup.hide();
    }

    public void show(MachineDTO machine, int xcord, int ycord) {
        clearValues();

        machineName.setText(machine.getMachineName());
        hostnameField.setValue(machine.getUri());
        updateMachinePopup.center();
        updateMachinePopup.show();
    }

    @UiHandler("submitUpdatedMachine")
    public void submitHandler(ClickEvent e) {
        doSubmit();
    }

    @UiHandler("hostnameField")
    public void submitByTextboxHandler(KeyPressEvent e) {
        if (e.getCharCode() == '\r') {
            doSubmit();
        }
    }

    private void doSubmit() {
        // get edu.chip.carranet.data
        String machineId = this.machineName.getText();
        String hostname = this.hostnameField.getValue();

        MachineDTO updatedMachine = new MachineDTO(machineId, hostname);

        // make update call
        new UpdateMachineCommand(machineId, updatedMachine).execute();
        updateMachinePopup.hide();
        clearValues();
    }


    @UiHandler("cancel")
    public void cancelHandler(ClickEvent e) {
        updateMachinePopup.hide();
        clearValues();
    }

    private void clearValues() {
        machineName.setText(null);
        hostnameField.setValue(null);
    }
}
