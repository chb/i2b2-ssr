package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

import java.util.List;


public class CreateStudyCommand implements CarraCommand {

    private String studyName;
    private List<String> machines;

    public CreateStudyCommand(String studyName, List<String> machines) {
        this.studyName = studyName;
        this.machines = machines;
    }

    @Override
    public void execute() {
        StudyManagementServiceAsync service = (StudyManagementServiceAsync)
                GWT.create(StudyManagementService.class);

        service.createStudy(studyName, machines,
                new CarraAsyncCallback<Void>(MessageType.StudyCreated, this));
    }

    public String getStudyName() {
        return studyName;
    }

    public List<String> getMachines() {
        return machines;
    }
}
