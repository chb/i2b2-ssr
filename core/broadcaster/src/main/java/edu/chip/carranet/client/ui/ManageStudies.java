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
import edu.chip.carranet.client.command.CreateStudyCommand;
import edu.chip.carranet.client.command.DeleteStudyCommand;
import edu.chip.carranet.client.command.GetStudiesCommand;
import edu.chip.carranet.client.command.UpdateStudyCommand;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.ui.style.TableStyle;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ManageStudies extends Composite {

    interface MyUiBinder extends UiBinder<Panel, ManageStudies> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {
            if (e.getType().equals(MessageType.StudiesListed)) {
                studyList = (List<StudyDTO>) e.getResponse();
                paintStudiesTable(studyList);
            } else if (e.getType().equals(MessageType.StudyCreated)) {
                CreateStudyCommand requestContext = (CreateStudyCommand) e.getRequest();
                StudyDTO study = new StudyDTO(requestContext.getStudyName(), requestContext.getMachines());
                studyList.add(study);
                paintStudiesTable(studyList);
            } else if (e.getType().equals(MessageType.StudyDeleted)) {
                DeleteStudyCommand requestContext = (DeleteStudyCommand) e.getRequest();
                Iterator<StudyDTO> iter = studyList.iterator();
                while (iter.hasNext()) {
                    StudyDTO existingStudy = iter.next();
                    if (existingStudy.getStudyName().equals(requestContext.getStudyId())) {
                        iter.remove();
                    }
                }
                paintStudiesTable(studyList);
            } else if (e.getType().equals(MessageType.StudyUpdated)) {
                UpdateStudyCommand requestContext = (UpdateStudyCommand) e.getRequest();
                StudyDTO study = requestContext.getUpdatedStudy();
                ListIterator<StudyDTO> iter = studyList.listIterator();
                while (iter.hasNext()) {
                    StudyDTO s = iter.next();
                    if (s.getStudyName().equals(study.getStudyName())) {
                        // found the updated entry
                        iter.set(study);
                        break;
                    }
                }
                paintStudiesTable(studyList);
            }

        }
    };

    List<StudyDTO> studyList;

    @UiField
    FlexTable studiesTable;

    @UiField
    TableStyle tableStyle;

    @UiField
    Button addNewStudy;

    @UiField
    NewStudyWindow newStudyWindow;
    @UiField
    UpdateStudyWindow editStudyWindow;

    public ManageStudies() {
        initWidget(binder.createAndBindUi(this));

        // fetch study edu.chip.carranet.data
        new GetStudiesCommand().execute();

        studiesTable.setStyleName(tableStyle.carraTable());
    }

    @UiHandler("addNewStudy")
    public void showNewStudyPopup(ClickEvent e) {
        Button b = (Button) e.getSource();

        int cordx = b.getAbsoluteLeft() + b.getOffsetWidth();
        int cordy = b.getAbsoluteTop();

        newStudyWindow.show(cordx, cordy);
    }

    @Override
    protected void onLoad() {
        EventBus.getInstance().addHandler(eventHandler);
    }

    @Override
    protected void onUnload() {
        EventBus.getInstance().removeHandler(eventHandler);
    }

    private void paintStudiesTable(List<StudyDTO> studies) {
        studiesTable.removeAllRows();

        // header
        String[] headers = new String[]{"Study", "Machines", "Edit", "Delete"};
        for (int i = 0; i < headers.length; ++i) {
            studiesTable.setText(0, i, headers[i]);
        }

        for (StudyDTO study : studies) {
            addRowToStudiesTable(study);
        }
    }

    private void addRowToStudiesTable(final StudyDTO study) {
        int nextRow = studiesTable.getRowCount();
        studiesTable.setText(nextRow, 0, study.getStudyName());
        studiesTable.setText(nextRow, 1, study.getStudyMachines().toString());
        Button editButton = new Button();
        editButton.setText("edit");
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Button b = (Button) clickEvent.getSource();
                int cordx = b.getAbsoluteLeft() + b.getOffsetWidth();
                int cordy = b.getAbsoluteTop();
                editStudyWindow.show(study, cordx, cordy);
            }
        });
        studiesTable.setWidget(nextRow, 2, editButton);

        Button deleteButton = new Button();
        deleteButton.setText("delete");
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new DeleteStudyCommand(study.getStudyName()).execute();
            }
        });
        studiesTable.setWidget(nextRow, 3, deleteButton);

        TableUtil.paintRows(tableStyle, studiesTable);
    }
}
