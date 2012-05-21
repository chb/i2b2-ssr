package edu.chip.carranet.server;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AdminProperties {

    private static Properties prop = new Properties();
    private static boolean isLoaded = false;
    private static Logger log = Logger.getLogger(AdminProperties.class);
    private static ClassLoader loader = AdminProperties.class.getClassLoader();


    public static String getProperty(String key) {


        if (isLoaded == false) {
            try {
                InputStream stream = loader.getResourceAsStream("admin_ui.properties");
                if(stream == null){
                    System.out.println("HIIIIIII");
                    log.fatal("couldn't find admin_ui.properties on the classpath");
                }
                prop.load(stream);
            } catch (IOException e) {
                log.fatal("Can't load properties file, check if " +
                        "admin_ui_server.properties " +
                        "is in your classpath");
                return null;
            }
        }
        return prop.getProperty(key);
    }


}
