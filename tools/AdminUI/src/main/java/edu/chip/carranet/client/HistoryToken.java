package edu.chip.carranet.client;

import com.google.gwt.user.client.History;


public enum HistoryToken {

    Main, ManageUsers, About, ManageMachines, ManageStudies, ClusterStatus, LogOut;

    public void fire() {
        History.newItem(this.toString());
    }
}

