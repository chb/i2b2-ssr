package edu.chip.carranet.client.data;

import java.io.Serializable;


public class MachineDTO implements Serializable {
    private String machineName;
    private String uri;
    private String certificate;
    private boolean enabled = true;


    public MachineDTO() {
    }

    public MachineDTO(String machineName, String uri) {
        this(machineName, uri, null, true);
    }

    public MachineDTO(String machineName, String uri, String certificate, boolean enabled){
      this.machineName = machineName;
        this.uri = uri;
        this.certificate = certificate;
        this.enabled = true;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MachineDTO that = (MachineDTO) o;

        if (enabled != that.enabled) return false;
        if (certificate != null ? !certificate.equals(that.certificate) : that.certificate != null) return false;
        if (machineName != null ? !machineName.equals(that.machineName) : that.machineName != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = machineName != null ? machineName.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.machineName + ":" + this.uri;
    }
}
