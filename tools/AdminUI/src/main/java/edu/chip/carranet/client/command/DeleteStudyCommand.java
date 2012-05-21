package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 15, 2010
 */
public class DeleteStudyCommand implements CarraCommand {

    private String studyId;

    public DeleteStudyCommand(String studyId) {
        this.studyId = studyId;
    }

    @Override
    public void execute() {
        StudyManagementServiceAsync studyManager =
                GWT.create(StudyManagementService.class);
        studyManager.deleteStudy(studyId,
                new CarraAsyncCallback<Void>(MessageType.StudyDeleted, this));
    }

    public String getStudyId() {
        return studyId;
    }
}
