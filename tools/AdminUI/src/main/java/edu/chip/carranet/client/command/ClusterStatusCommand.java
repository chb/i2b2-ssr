package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.MachineStatusDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

import java.util.List;


public class ClusterStatusCommand implements CarraCommand {


    @Override
    public void execute() {
        StudyManagementServiceAsync service = (StudyManagementServiceAsync)
                GWT.create(StudyManagementService.class);


        service.getAllMachineStatuses(
                new CarraAsyncCallback<List<MachineStatusDTO>>(
                        MessageType.GotClusterStatus, this));
    }
}
