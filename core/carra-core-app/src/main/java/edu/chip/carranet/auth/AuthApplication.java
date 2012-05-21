package edu.chip.carranet.auth;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AuthApplication extends Application {
    private static Logger log = Logger.getLogger(AuthApplication.class);


    public static void main(String[] args) throws Exception {
        ApplicationContext springContext = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext-router.xml", "applicationContext.xml"});

        Component top = (Component) springContext.getBean("top");
        for (Server s : top.getServers()) {
            s.getContext().getParameters().add("maxThreads", "512");
        }

        top.start();

    }


}

