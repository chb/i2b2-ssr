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
public class DeleteMachineCommand implements CarraCommand {

    private String machineId;

    public DeleteMachineCommand(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public void execute() {
        StudyManagementServiceAsync studyManager =
                GWT.create(StudyManagementService.class);
        studyManager.deleteMachine(machineId,
                new CarraAsyncCallback<Void>(MessageType.MachineDeleted, this));
    }

    public String getMachineId() {
        return machineId;
    }
}
