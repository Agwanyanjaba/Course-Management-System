package com.student.microservices.model;

public class Courses {
    //courseID,title, department, level,lastUpdated
    private String courseID;
    private String title;
    private String department;
    private String level;
    private String lastUpdated;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseID='" + courseID + '\'' +
                ", title='" + title + '\'' +
                ", department='" + department + '\'' +
                ", level='" + level + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}//End class courses
