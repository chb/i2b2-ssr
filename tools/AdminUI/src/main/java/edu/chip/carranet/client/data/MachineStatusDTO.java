package edu.chip.carranet.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MachineStatusDTO implements Serializable {

    private MachineDTO machine;
    private List<String> messages = new ArrayList<String>();
    private MachineStatusEnum status;

    public MachineStatusDTO() {
    }

    public MachineStatusDTO(MachineDTO m, MachineStatusEnum status) {
        this.machine = m;
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public MachineStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MachineStatusEnum status) {
        this.status = status;
    }


}



