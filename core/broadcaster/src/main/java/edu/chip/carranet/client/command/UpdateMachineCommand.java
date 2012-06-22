package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class UpdateMachineCommand implements CarraCommand {
    private String machineId;
    private MachineDTO updatedMachine;

    public UpdateMachineCommand(String machineId, MachineDTO updatedMachine) {
        this.machineId = machineId;
        this.updatedMachine = updatedMachine;
    }

    @Override
    public void execute() {
        StudyManagementServiceAsync studyManager =
                GWT.create(StudyManagementService.class);
        studyManager.updateMachine(machineId, updatedMachine,
                new CarraAsyncCallback<Void>(MessageType.MachineUpdated, this));
    }

    public String getMachineId() {
        return machineId;
    }

    public MachineDTO getUpdatedMachine() {
        return updatedMachine;
    }
}
