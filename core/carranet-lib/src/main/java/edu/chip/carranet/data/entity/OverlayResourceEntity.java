
package edu.chip.carranet.data.entity;


import javax.persistence.*;

@Entity
@Table(name = "OVERLAY_RESOURCES")
public class OverlayResourceEntity {

    private Long id;
    private String path;
    private boolean enabled;
    private byte[] payload;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="path", unique = true, nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "payload", length = 10000)
    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Basic
    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
