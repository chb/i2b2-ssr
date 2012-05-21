package edu.chip.carranet.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import edu.chip.carranet.client.command.ClusterStatusCommand;
import edu.chip.carranet.client.data.MachineStatusDTO;
import edu.chip.carranet.client.data.MachineStatusEnum;
import edu.chip.carranet.client.event.EventBus;
import edu.chip.carranet.client.event.EventBusHandler;
import edu.chip.carranet.client.event.EventBusMessage;
import edu.chip.carranet.client.event.MessageType;
import edu.chip.carranet.client.ui.style.TableStyle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Composite widget to display cluster status.
 */
public class ClusterStatus extends Composite {


    interface MyUiBinder extends UiBinder<Panel, ClusterStatus> {
    }

    private static final MyUiBinder binder = GWT.create(MyUiBinder.class);

    //This event handler checks for new updates and
    private EventBusHandler eventHandler = new EventBusHandler() {
        @Override
        public void handle(EventBusMessage e) {

            if (e.getType().equals(MessageType.GotClusterStatus)) {
                List<MachineStatusDTO> status = (List<MachineStatusDTO>) e.getResponse();
                Collections.sort(status, new Comparator<MachineStatusDTO>() {
                    @Override
                    public int compare(MachineStatusDTO o1, MachineStatusDTO o2) {
                        return o1.getMachine().getMachineName().compareTo(o2.getMachine().getMachineName());
                    }
                });
                
                for (MachineStatusDTO m : status) {
                    addRowToStatusTable(m);
                }

            }

        }
    };

    @UiField
    TableStyle tableStyle;

    @UiField
    FlexTable statusTable;


    public ClusterStatus() {
        initWidget(binder.createAndBindUi(this));
        ClusterStatusCommand command = new ClusterStatusCommand();
        statusTable.setStyleName(tableStyle.carraTable());

        String[] headers = new String[]{"Machine ID", "Machine Hostname", "Status"};
        for (int i = 0; i < headers.length; ++i) {
            statusTable.setText(0, i, headers[i]);
        }


        command.execute();

    }

    @Override
    protected void onLoad() {
        EventBus.getInstance().addHandler(eventHandler);
    }

    @Override
    protected void onUnload() {
        EventBus.getInstance().removeHandler(eventHandler);
    }


    private void addRowToStatusTable(MachineStatusDTO m) {


        int row = statusTable.getRowCount();


        Image good = new Image("images/Good.png");
        Image bad = new Image("images/Bad.png");
        Image unknown = new Image("images/Unknown.png");

        statusTable.setText(row, 0, m.getMachine().getMachineName());
        statusTable.setText(row, 1, m.getMachine().getUri());


        if (m.getStatus().equals(MachineStatusEnum.NotResponding)) {
            statusTable.setWidget(row, 2, bad);
        } else if (m.getStatus().equals(MachineStatusEnum.Responding)) {
            statusTable.setWidget(row, 2, unknown);
        } else {
            statusTable.setWidget(row, 2, good);
        }

        TableUtil.paintRows(tableStyle, statusTable);


    }

}
