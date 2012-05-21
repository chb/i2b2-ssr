package edu.chip.carranet.auth;

import edu.chip.carranet.auth.backend.IUserStore;


public class UserStoreFactory {

    private static IUserStore instance;



    public synchronized static IUserStore getUserStore() {
        return instance;
    }

    public synchronized static void setUserStore(IUserStore store) {
        instance = store;
    }

}
