package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;


public class CreateMachineCommand implements CarraCommand {

    private String machineId;
    private String hostname;

    public CreateMachineCommand(String machineId, String hostname) {
        this.machineId = machineId;
        this.hostname = hostname;
    }

    public void execute() {
        StudyManagementServiceAsync service = (StudyManagementServiceAsync)
                GWT.create(StudyManagementService.class);

        service.createMachine(machineId, hostname,
                new CarraAsyncCallback<Void>(MessageType.MachineCreated, this));
    }

    public String getMachineId() {
        return machineId;
    }

    public String getHostname() {
        return hostname;
    }
}

