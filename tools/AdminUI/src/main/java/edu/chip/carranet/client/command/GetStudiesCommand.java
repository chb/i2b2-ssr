package edu.chip.carranet.client.command;

import com.google.gwt.core.client.GWT;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.client.service.StudyManagementServiceAsync;

import java.util.List;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 13, 2010
 */
public class GetStudiesCommand implements CarraCommand {
    @Override
    public void execute() {
        StudyManagementServiceAsync service = (StudyManagementServiceAsync)
                GWT.create(StudyManagementService.class);

        service.getStudies(
                new CarraAsyncCallback<List<StudyDTO>>(
                        MessageType.StudiesListed, this));
    }
}
