package edu.chip.carranet.server;

import edu.chip.carranet.server.backend.*;


public class ServiceFactory {


    /**
     * Will return a real or fake auth service depending on how the
     * admin_ui_server properties are setup
     *
     * @return
     */
    public static IAuthBackend getAuthorizationBackend() {

        String mockInstance = AdminProperties.getProperty("USE_MOCK_INSTANCE");


        if (mockInstance != null && mockInstance.equalsIgnoreCase("true")) {
            return new MockAuthServiceBackend();
        } else {

            String remoteAuth = AdminProperties.getProperty("AUTH_SERVER_ADDRESS");

            //if anything goes wrong just bomb out with a general runtime
            //exception, nobody is going to be catching this
            try {
                return new RemoteAuthServiceBackend(remoteAuth);
            } catch (Exception e) {
                throw new RuntimeException("Can't talk to the auth server, " +
                        "did you set AUTH_SERVER_ADDRESS?");
            }
        }
    }

    public static IStudyManagementBackend getStudyManagementBackend() {
        String mockInstance = AdminProperties.getProperty("USE_MOCK_INSTANCE");

        if (mockInstance != null && mockInstance.equalsIgnoreCase("true")) {
            return new MockStudyManagementBackend();

        } else {

            String remoteOLS = AdminProperties.getProperty("OLS_SERVER_ADDRESS");

            //if anything goes wrong just bomb out with a general runtime
            //exception, nobody is going to be catching this
            try {
                return new RemoteStudyManagementBackend(remoteOLS);
            } catch (Exception e) {
                throw new RuntimeException("Can't talk to the OLS server, " +
                        "did you set OLS_SERVER_ADDRESS?");
            }
        }

    }

}
