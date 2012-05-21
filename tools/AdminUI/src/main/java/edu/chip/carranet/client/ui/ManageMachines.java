package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import edu.chip.carranet.client.command.CreateMachineCommand;
import edu.chip.carranet.client.command.DeleteMachineCommand;
import edu.chip.carranet.client.command.GetMachinesCommand;
import edu.chip.carranet.client.command.UpdateMachineCommand;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.ui.style.TableStyle;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class ManageMachines extends Composite {

    interface MyUiBinder extends UiBinder<Panel, ManageMachines> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {
            GWT.log("manageStudies sees event type " + e.getType());
            if (e.getType().equals(MessageType.MachinesListed)) {
                List<MachineDTO> machines = (List<MachineDTO>) e.getResponse();
                machineList = machines;
                paintMachinesTable(machineList);
            } else if (e.getType().equals(MessageType.MachineCreated)) {
                CreateMachineCommand requestContext = (CreateMachineCommand) e.getRequest();
                MachineDTO machine = new MachineDTO(requestContext.getMachineId(), requestContext.getHostname());
                machineList.add(machine);
                paintMachinesTable(machineList);
            } else if (e.getType().equals(MessageType.MachineDeleted)) {
                DeleteMachineCommand requestContext = (DeleteMachineCommand) e.getRequest();
                Iterator<MachineDTO> iter = machineList.iterator();
                while (iter.hasNext()) {
                    MachineDTO existingUser = iter.next();
                    if (existingUser.getMachineName().equals(requestContext.getMachineId())) {
                        iter.remove();
                    }
                }
                paintMachinesTable(machineList);
            } else if (e.getType().equals(MessageType.MachineUpdated)) {
                UpdateMachineCommand requestContext = (UpdateMachineCommand) e.getRequest();
                MachineDTO machine = requestContext.getUpdatedMachine();
                ListIterator<MachineDTO> iter = machineList.listIterator();
                while (iter.hasNext()) {
                    MachineDTO m = iter.next();
                    if (m.getMachineName().equals(machine.getMachineName())) {
                        // found the updated entry
                        iter.set(machine);
                        break;
                    }
                }
                paintMachinesTable(machineList);
            }

        }
    };

    List<MachineDTO> machineList;

    @UiField
    FlexTable machinesTable;

    @UiField
    TableStyle tableStyle;

    @UiField
    Button addNewMachine;

    @UiField
    NewMachineWindow newMachineWindow;
    @UiField
    UpdateMachineWindow editMachineWindow;

    public ManageMachines() {
        initWidget(binder.createAndBindUi(this));

        // fetch study edu.chip.carranet.data
        new GetMachinesCommand().execute();

        machinesTable.setStyleName(tableStyle.carraTable());
    }

    @UiHandler("addNewMachine")
    public void showNewStudyPopup(ClickEvent e) {
        Button b = (Button) e.getSource();

        int cordx = b.getAbsoluteLeft() + b.getOffsetWidth();
        int cordy = b.getAbsoluteTop();

        newMachineWindow.show(cordx, cordy);
    }

    @Override
    protected void onLoad() {
        EventBus.getInstance().addHandler(eventHandler);
    }

    @Override
    protected void onUnload() {
        EventBus.getInstance().removeHandler(eventHandler);
    }

    private void paintMachinesTable(List<MachineDTO> machines) {
        machinesTable.removeAllRows();

        // header
        String[] headers = new String[]{"MachineId", "Hostname", "Edit", "Delete"};
        for (int i = 0; i < headers.length; ++i) {
            machinesTable.setText(0, i, headers[i]);
        }

        for (MachineDTO machine : machines) {
            addRowToStudiesTable(machine);
        }
    }

    private void addRowToStudiesTable(final MachineDTO machine) {
        int nextRow = machinesTable.getRowCount();
        machinesTable.setText(nextRow, 0, machine.getMachineName());
        machinesTable.setText(nextRow, 1, machine.getUri());
        Button editButton = new Button();
        editButton.setText("edit");
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Button b = (Button) clickEvent.getSource();

                editMachineWindow.show(machine, b.getAbsoluteLeft() + b.getOffsetWidth(), b.getAbsoluteTop());
            }
        });
        machinesTable.setWidget(nextRow, 2, editButton);

        Button deleteButton = new Button();
        deleteButton.setText("delete");
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new DeleteMachineCommand(machine.getMachineName()).execute();
            }
        });
        machinesTable.setWidget(nextRow, 3, deleteButton);

        TableUtil.paintRows(tableStyle, machinesTable);
    }
}