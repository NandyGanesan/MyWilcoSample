package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

public class Priority {

        @SerializedName("ID")
        public int ID;

        @SerializedName("PriorityLevel")
        public String PriorityLevel;

        @SerializedName("ActiveStatus")
        public boolean ActiveStatus;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getPriorityLevel() {
            return PriorityLevel;
        }

        public void setPriorityLevel(String priorityLevel) {
            PriorityLevel = priorityLevel;
        }

        public boolean isActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            ActiveStatus = activeStatus;
        }

}
