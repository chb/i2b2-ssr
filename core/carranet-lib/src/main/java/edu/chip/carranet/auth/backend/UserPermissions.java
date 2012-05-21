package edu.chip.carranet.auth.backend;


/**
 * An enumeration of all the roles in the system
 */
public enum UserPermissions {

    admin,                        //User can perform admin duties
    pdo,                              //User can perform PDO
    sitebreakdown;      //PDO Results are tagged by site, user can see where results come from


    public String toAssertionString(){
        return "role:" + this.toString();
    }


}
