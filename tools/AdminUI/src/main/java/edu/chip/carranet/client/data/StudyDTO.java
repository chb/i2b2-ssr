package edu.chip.carranet.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StudyDTO implements Serializable {

    private String studyName;
    private List<String> studyMachines = new ArrayList<String>();

    public StudyDTO() {

    }

    public StudyDTO(String studyName, List<String> studyMachines) {
        this.studyName = studyName;
        this.studyMachines = studyMachines;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public List<String> getStudyMachines() {
        return studyMachines;
    }

    public void setStudyMachines(List<String> studyMachines) {
        this.studyMachines = studyMachines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyDTO study = (StudyDTO) o;

        if (studyMachines != null ? !studyMachines.equals(study.studyMachines) : study.studyMachines != null)
            return false;
        if (studyName != null ? !studyName.equals(study.studyName) : study.studyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studyName != null ? studyName.hashCode() : 0;
        result = 31 * result + (studyMachines != null ? studyMachines.hashCode() : 0);
        return result;
    }        
}
