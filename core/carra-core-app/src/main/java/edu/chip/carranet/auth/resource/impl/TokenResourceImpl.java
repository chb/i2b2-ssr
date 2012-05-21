package edu.chip.carranet.auth.resource.impl;

import edu.chip.carranet.auth.backend.IAuditLogger;
import edu.chip.carranet.auth.backend.IAuthenticator;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.auth.backend.User;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ClientException;
import edu.chip.carranet.auth.exception.PermissionException;
import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

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

public class TokenResourceImpl {
    private static Logger log = Logger.getLogger(TokenResourceImpl.class);


    @Resource
    private IAuthenticator authenticator;

    @Resource
    private IAuditLogger auditLogger;

    @Transactional(readOnly = true)
    public String getToken(User user) throws AuthException {

        try {

            Identity id = null;
            if(user != null) {
                id = authenticator.authenticate(user.getUserName(), user.getHashedPassword());
            }

            if(id == null) {
                throw new PermissionException("Bad token, exiting");
            }


            String idString = TokenUtil.convertIdentityToAuthString(id);
            //sign Token
            return idString;


        } catch(JAXBException e) {
            throw new ClientException("Bad identity");
        } catch(UnsupportedEncodingException e) {
            throw new ClientException("Error creating token: Bad encoding");
        }
    }


}
