package edu.chip.carranet.data.entity;

import edu.chip.carranet.jaxb.Machine;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dave Ortiz
 *         Entity Representing a study
 */
@Entity
@Table(name = "STUDY")
public class StudyEntity {

    private Long id;
    private String name;
    private boolean enabled;
    private Set<MachineEntity> machines = new HashSet<MachineEntity>();

    public StudyEntity() {
    }


    @Id
    @GeneratedValue
    @Column(name = "study_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = MachineEntity.class)
    @JoinTable(name = "STUDY_MACHINES",
            joinColumns = {@JoinColumn(name = "study_id")},
            inverseJoinColumns = {@JoinColumn(name = "machine_id")})
    public Set<MachineEntity> getMachines() {
        return machines;
    }


    public void setMachines(Set<MachineEntity> machines) {
        this.machines = machines;
    }
}
