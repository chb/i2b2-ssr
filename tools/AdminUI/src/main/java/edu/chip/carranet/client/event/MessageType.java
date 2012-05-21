package edu.chip.carranet.client.event;


public enum MessageType {
    UserAuthenticated,      // UserDTO was Authenticated

    UserCreated,            // UserDTO was added to the auth system
    UserDeleted,            // UserDTO was deleted from the auth system
    UsersListed,            // A list of users was retrieved from Auth

    UserInfo,               //Retrieved information about a single user
    UpdateUser,

    GotClusterStatus,       // Retrieved status of the cluster

    MachineCreated,         // A machine was created on the OLS service
    MachineDeleted,         // A machine was deleted on the OLS service
    MachinesListed,         // A list of all machines fetched from OLS
    MachineUpdated,         // A machine was updated on the OLS service

    StudyCreated,           // A study was created on the OLS service
    StudyDeleted,           // A study was deleted on the OLS service
    StudiesListed,          // A list of all studies fetched from OLS
    StudyUpdated,           // A study was updated on the OLS service
}
