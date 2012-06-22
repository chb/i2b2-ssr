package edu.chip.carranet.client.data;

import java.io.Serializable;


public class MachineDTO implements Serializable {
    private String machineName;
    private String uri;


    public MachineDTO() {
    }

    public MachineDTO(String machineName, String uri) {
        this.machineName = machineName;
        this.uri = uri;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MachineDTO machine = (MachineDTO) o;

        if (uri != null ? !uri.equals(machine.uri) : machine.uri != null) return false;
        if (machineName != null ? !machineName.equals(machine.machineName) : machine.machineName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = machineName != null ? machineName.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.machineName + ":" + this.uri;
    }
}
