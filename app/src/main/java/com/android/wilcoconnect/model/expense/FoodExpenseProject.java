package com.android.wilcoconnect.model.expense;

import com.google.gson.annotations.SerializedName;

public class FoodExpenseProject {

    @SerializedName("ProjectID")
    public String ProjectID;

    @SerializedName("ProjectTypeID")
    public int ProjectTypeID;

    @SerializedName("ProjectName")
    public String ProjectName;

    @SerializedName("ProjectDescription")
    public String ProjectDescription;

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public int getProjectTypeID() {
        return ProjectTypeID;
    }

    public void setProjectTypeID(int projectTypeID) {
        ProjectTypeID = projectTypeID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectDescription() {
        return ProjectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        ProjectDescription = projectDescription;
    }
}
