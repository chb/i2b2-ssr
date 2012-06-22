package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import edu.chip.carranet.client.command.CreateMachineCommand;
import edu.chip.carranet.client.command.GetMachinesCommand;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class NewMachineWindow extends Composite {

    interface MyUiBinder extends UiBinder<Widget, NewMachineWindow> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);


    @UiField
    PopupPanel newMachinePopup;

    @UiField
    TextBox machineIdField;

    @UiField
    TextBox uriField;

    @UiField
    Button submitNewMachine;

    @UiField
    Button cancel;

    public NewMachineWindow() {
        initWidget(binder.createAndBindUi(this));

        // fetch machine list
        GetMachinesCommand getMachinesCommand = new GetMachinesCommand();
        getMachinesCommand.execute();
    }

    public void hide() {
        newMachinePopup.hide();
    }

    public void show(int xcord, int ycord) {
        newMachinePopup.setPopupPosition(xcord, ycord);
        newMachinePopup.show();
    }

    @UiHandler("submitNewMachine")
    public void submitHandler(ClickEvent e) {
        // get edu.chip.carranet.data
        String machineId = machineIdField.getValue().trim();
        String uri = uriField.getValue().trim();


        // make update call
        new CreateMachineCommand(machineId, uri).execute();

        newMachinePopup.hide();
        clearValues();
    }

    @UiHandler("cancel")
    public void clearHandler(ClickEvent e) {
        newMachinePopup.hide();
        clearValues();
    }

    private void clearValues() {
        machineIdField.setValue(null);
        uriField.setValue(null);
    }

}
