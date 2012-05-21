package edu.chip.carranet.auth.resource;

import java.util.List;

public class ProjectBean {
    private String projectName;
    private String projectId;
    private List<String> roles;

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#equals(java.lang.Object)
      */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProjectBean other = (ProjectBean) obj;
        if (projectId == null) {
            if (other.projectId != null) {
                return false;
            }
        } else if (!projectId.equals(other.projectId)) {
            return false;
        }
        return true;
    }

    /**
     * @return the projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#hashCode()
      */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
        return result;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#toString()
      */

    @Override
    public String toString() {
        return "ProjectBean [" + (projectId != null ? "projectId=" + projectId + ", " : "")
                + (projectName != null ? "projectName=" + projectName + ", " : "") + (roles != null ? "roles=" + roles : "") + "]";
	}
}
