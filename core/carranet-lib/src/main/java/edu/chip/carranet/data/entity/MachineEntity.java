package edu.chip.carranet.data.entity;

import edu.chip.carranet.jaxb.StudyEntry;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a machine in i2b2-ssr
 */
@Entity
@Table(name = "MACHINE")
public class MachineEntity {

    private Long id;
    private String name;
    private boolean enabled;
    private String locater;
    private Set<StudyEntity> studies = new HashSet<StudyEntity>();


    public MachineEntity() {
    }

    public MachineEntity(Long id, String name, boolean enabled, String locator) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.locater = locator;
    }


    @Id
    @GeneratedValue
    @Column(name = "machine_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;


    }

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "Boolean default 100")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "locator", nullable = false)
    public String getLocater() {
        return locater;
    }

    public void setLocater(String locater) {
        this.locater = locater;
    }

        @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        mappedBy = "machines",
        targetEntity = StudyEntity.class
    )
    public Set<StudyEntity> getStudies() {
        return studies;
    }

    public void setStudies(Set<StudyEntity> studies) {
        this.studies = studies;
    }
}
