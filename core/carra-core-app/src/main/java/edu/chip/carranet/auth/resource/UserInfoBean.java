package edu.chip.carranet.auth.resource;

import java.util.List;


public class UserInfoBean {
        private String userFullName;
        private String password;
        private boolean passwordEncrypted;
        private String domain;
        private String environment;
        private String helpUrl;
        private String crcUrl;
        private String encUrl;
        private String frcUrl;
        private String frmUrl;
        private String labUrl;
        private String ontUrl;
        private String workUrl;
        private List<ProjectBean> projects;

        /**
         * @return the crcUrl
         */
        public String getCrcUrl() {
            return crcUrl;
        }

        /**
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * @return the encUrl
         */
        public String getEncUrl() {
            return encUrl;
        }

        /**
         * @return the environment
         */
        public String getEnvironment() {
            return environment;
        }

        /**
         * @return the frcUrl
         */
        public String getFrcUrl() {
            return frcUrl;
        }

        /**
         * @return the frmUrl
         */
        public String getFrmUrl() {
            return frmUrl;
        }

        /**
         * @return the helpUrl
         */
        public String getHelpUrl() {
            return helpUrl;
        }

        /**
         * @return the labUrl
         */
        public String getLabUrl() {
            return labUrl;
        }

        /**
         * @return the ontUrl
         */
        public String getOntUrl() {
            return ontUrl;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Returns a project with the given identifier
         *
         * @param id
         * @return
         */
        public ProjectBean getProjectById(String id) {
            if (projects != null) {
                for (ProjectBean project : projects) {
                    if (project.getProjectId().equals(id)) {
                        return project;
                    }
                }
            }
            return null;
        }

        /**
         * @return the projects
         */
        public List<ProjectBean> getProjects() {
            return projects;
        }

        /**
         * @return the userFullName
         */
        public String getUserFullName() {
            return userFullName;
        }

        /**
         * @return the workUrl
         */
        public String getWorkUrl() {
            return workUrl;
        }

        /**
         * @return the passwordEncrypted
         */
        public boolean isPasswordEncrypted() {
            return passwordEncrypted;
        }

        /**
         * @param crcUrl the crcUrl to set
         */
        public void setCrcUrl(String crcUrl) {
            this.crcUrl = crcUrl;
        }

        /**
         * @param domain the domain to set
         */
        public void setDomain(String domain) {
            this.domain = domain;
        }

        /**
         * @param encUrl the encUrl to set
         */
        public void setEncUrl(String encUrl) {
            this.encUrl = encUrl;
        }

        /**
         * @param environment the environment to set
         */
        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        /**
         * @param frcUrl the frcUrl to set
         */
        public void setFrcUrl(String frcUrl) {
            this.frcUrl = frcUrl;
        }

        /**
         * @param frmUrl the frmUrl to set
         */
        public void setFrmUrl(String frmUrl) {
            this.frmUrl = frmUrl;
        }

        /**
         * @param helpUrl the helpUrl to set
         */
        public void setHelpUrl(String helpUrl) {
            this.helpUrl = helpUrl;
        }

        /**
         * @param labUrl the labUrl to set
         */
        public void setLabUrl(String labUrl) {
            this.labUrl = labUrl;
        }

        /**
         * @param ontUrl the ontUrl to set
         */
        public void setOntUrl(String ontUrl) {
            this.ontUrl = ontUrl;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @param passwordEncrypted the passwordEncrypted to set
         */
        public void setPasswordEncrypted(boolean passwordEncrypted) {
            this.passwordEncrypted = passwordEncrypted;
        }

        /**
         * @param projects the projects to set
         */
        public void setProjects(List<ProjectBean> projects) {
            this.projects = projects;
        }

        /**
         * @param userFullName the userFullName to set
         */
        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }

        /**
         * @param workUrl the workUrl to set
     */
	public void setWorkUrl(String workUrl) {
		this.workUrl = workUrl;
	}
}

