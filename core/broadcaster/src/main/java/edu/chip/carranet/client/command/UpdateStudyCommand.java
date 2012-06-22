package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 16, 2010
 */
public class UpdateStudyCommand implements CarraCommand {

    private String studyId;
    private StudyDTO updatedStudy;

    public UpdateStudyCommand(String studyId, StudyDTO updatedStudy) {
        this.studyId = studyId;
        this.updatedStudy = updatedStudy;
    }

    @Override
    public void execute() {
        StudyManagementServiceAsync studyManager =
                GWT.create(StudyManagementService.class);
        studyManager.updateStudy(studyId, updatedStudy,
                new CarraAsyncCallback<Void>(MessageType.StudyUpdated, this));
    }

    public String getStudyId() {
        return studyId;
    }

    public StudyDTO getUpdatedStudy() {
        return updatedStudy;
    }
}
