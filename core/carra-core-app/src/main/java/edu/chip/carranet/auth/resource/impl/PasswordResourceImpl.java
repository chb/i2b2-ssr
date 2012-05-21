package edu.chip.carranet.auth.resource.impl;

import edu.chip.carranet.auth.backend.IAuditLogger;
import edu.chip.carranet.auth.backend.IAuthenticator;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ServerException;
import edu.chip.carranet.data.EventType;
import edu.chip.carranet.jaxb.ChangePasswordMessage;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class PasswordResourceImpl {

    @Resource
    private IAuthenticator authenticator;

    @Resource
    IAuditLogger auditLogger;

    @Transactional
    public void changePassword(String uriUsername, ChangePasswordMessage message, Identity id) throws AuthException {
        try{
            auditLogger.logEvent(EventType.MODIFY_USER,id.getUsername(), JAXBUtils.marshalToString(message));
            authenticator.changeUserPassword(id, uriUsername, message.getOldPassword(), message.getNewPassword());
        }
        catch(JAXBException e){
            throw new ServerException("This should never happen, error unmarshalling" +
                    " string we marshalled a second ago...JAX is broke!");
        }

    }
}
