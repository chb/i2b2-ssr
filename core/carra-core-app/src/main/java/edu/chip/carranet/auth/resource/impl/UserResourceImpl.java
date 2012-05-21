package edu.chip.carranet.auth.resource.impl;

import edu.chip.carranet.ExternalExceptions.ExternalError;
import edu.chip.carranet.auth.UserStoreFactory;
import edu.chip.carranet.auth.backend.IUserStore;
import edu.chip.carranet.jaxb.CarraUsers;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

/**
 * @author Dave Ortiz
 * @date 2/1/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 * <p/>
 * NOTICE: This software comes with NO guarantees whatsoever and is
 * licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */
public class UserResourceImpl {

    @Resource
    IUserStore userStore = UserStoreFactory.getUserStore();


    /**
     * Returns all the users
     *
     * @return
     */
    @Transactional(readOnly = true)
    public String getUsers(Identity id) {
        CarraUsers users = new CarraUsers();
        users.getUsers().addAll(userStore.getUsers(id));
        return generateResponseRepresentation(users);

    }


    /**
     * Creates a response by serializing via JAXB
     *
     * @param response
     * @return
     * @throws edu.chip.carranet.auth.exception.AuthException
     *
     */
    public static String generateResponseRepresentation(CarraUsers response) {
        try {
            String responseString = JAXBUtils.marshalToString(response);
            return responseString;
        } catch (JAXBException e) {
            throw new ExternalError("Error marshalling to XML", Response.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
