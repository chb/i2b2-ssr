package edu.chip.carranet.auth.resource.impl;

import edu.chip.carranet.auth.backend.IAuditLogger;
import edu.chip.carranet.auth.backend.IUserStore;
import edu.chip.carranet.auth.exception.*;
import edu.chip.carranet.data.EventType;
import edu.chip.carranet.jaxb.CarraUser;
import edu.chip.carranet.jaxb.CarraUserInfo;
import edu.chip.carranet.jaxb.CreateUserMessage;
import edu.chip.carranet.ExternalExceptions.BadResourceError;
import edu.chip.carranet.ExternalExceptions.ExternalError;
import edu.chip.carranet.ExternalExceptions.ResourceNotFoundError;
import org.apache.log4j.Logger;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
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
public class UserManagementResourceImpl {

    private static String JAXB_EXCEPTION_MESSAGE = "Shouldn't happen because we just marshalled this from XML";

    private Logger log = Logger.getLogger(getClass());

    @Resource
    private IUserStore userStore;

    @Resource
    private IAuditLogger auditLogger;


    @Transactional
    public void updateUser(Identity id, String uriUserName, CarraUser userInfo)
            throws BadResourceError, ResourceNotFoundError {
        try {
            if (!uriUserName.equals(userInfo.getUser().getUsername())) {
                throw new BadResourceError("URI doesn't match body of message, different usernames");
            }

            if (!userStore.containsUser(id, uriUserName)) {
                throw new ResourceNotFoundError("Can't find that user");
            }

            userStore.updateUserInfo(id, uriUserName, userInfo.getUser());
            auditLogger.logEvent(EventType.MODIFY_USER, id.getUsername(), JAXBUtils.marshalToString(userInfo));

        } catch (JAXBException e) {
            throw new ExternalError(JAXB_EXCEPTION_MESSAGE, Response.Status.BAD_REQUEST);
        }
    }


    @Transactional
    public String createUser(CreateUserMessage message, Identity id) throws AuthException {
        try {
            userStore.addUser(id, message.getUserName(), message.getPassword());
            message.setPassword("MASKED");

            String userName;

            if (id == null) {
                userName = "first_user";
            } else {
                userName = id.getUsername();
            }

            auditLogger.logEvent(EventType.ADD_USER, userName, JAXBUtils.marshalToString(message));
            return "sucess";
        } catch (JAXBException e) {
            throw new ServerException(JAXB_EXCEPTION_MESSAGE);

        }
    }


    @Transactional
    public Representation deleteUser(String userName, Identity id){
        Representation returnRep = new EmptyRepresentation();
        auditLogger.logEvent(EventType.DELETE_USER, userName, null);
        userStore.deleteUser(id, userName);


        return returnRep;
    }

    @Transactional(readOnly = true)
    public String getUser(String username, Identity id) {

        CarraUser user = new CarraUser();
        CarraUserInfo u = userStore.getUserInfo(id, username);
        user.setUser(u);

        try {
            return JAXBUtils.marshalToString(user);
        } catch (JAXBException e) {
            throw new ExternalError("unknown error", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}



