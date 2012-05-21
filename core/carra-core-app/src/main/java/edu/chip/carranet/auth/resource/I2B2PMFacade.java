package edu.chip.carranet.auth.resource;

import edu.chip.carranet.auth.exception.AuthException;
import org.apache.log4j.Logger;
import org.restlet.representation.Representation;

import org.restlet.resource.ServerResource;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBException;
import java.io.IOException;


/**
 * This class will fakeout an I2b2 PM and make it work with CARRAnet
 */

//@Path("/PMService/getS")
public class I2B2PMFacade extends ServerResource {

    private static Logger log = Logger.getLogger(I2B2PMFacade.class);

    @Resource
    //I2B2PmResourceImpl impl;

    @Transactional(readOnly = true)
    public Representation authorizeI2B2(Representation rep) throws IOException, JAXBException, AuthException, Exception {
        return null; // new StringRepresentation(impl.authorizeI2B2(rep.getText()));
    }

}
