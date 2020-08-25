package com.student.microservices.model;

public class Modules {
    //moduleID,moduleName, courseID
    private String moduleID;
    private String moduleName;
    private String courseID;

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    private String lastUpdated;

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Modules{" +
                "moduleID='" + moduleID + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", courseID='" + courseID + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
