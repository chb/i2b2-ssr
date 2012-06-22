package edu.chip.carranet.client.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Apr 20, 2010
 */
public class StudyUserDTO implements Serializable {
    private String userName;
    private List<String> studyIds;

    public StudyUserDTO() {
    }

    public StudyUserDTO(String userName, List<String> studyIds) {
        this.userName = userName;
        this.studyIds = studyIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getStudyIds() {
        return studyIds;
    }

    public void setStudyIds(List<String> studyIds) {
        this.studyIds = studyIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyUserDTO that = (StudyUserDTO) o;

        if (studyIds != null ? !studyIds.equals(that.studyIds) : that.studyIds != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (studyIds != null ? studyIds.hashCode() : 0);
        return result;
    }
}
