package edu.chip.carranet.client.data;


public enum GWTUserPermissions {

    admin,                        //User can perform admin duties
    pdo,                              //User can perform PDO
    sitebreakdown;      //PDO Results are tagged by site, user can see where results come from



    public String toAssertionString(){
        return "role:" + this.toString();
    }


    public static GWTUserPermissions permissionFromString(String s){
        if(s.equals("admin")) return admin;
        if(s.equals("pdo")) return pdo;
        if(s.equals("sitebreakdown")) return sitebreakdown;
        else return null;
    }


}
