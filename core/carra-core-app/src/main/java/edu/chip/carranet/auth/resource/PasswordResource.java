package edu.chip.carranet.auth.resource;

import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ClientException;
import edu.chip.carranet.auth.exception.ServerException;
import edu.chip.carranet.auth.resource.impl.PasswordResourceImpl;
import edu.chip.carranet.jaxb.ChangePasswordMessage;
import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * This resource allows a user to "post" an updated password
 */
public class PasswordResource extends AbstractAuthResource {

    Logger log = Logger.getLogger(getClass());

    @Resource
    PasswordResourceImpl impl;

    @Put
    public void changePassword(Representation representation) throws AuthException {
        String uriUserName = (String) getRequestAttributes().get("user");

         try {
             String xmlRep = representation.getText();
             ChangePasswordMessage message = JAXBUtils.unmarshal(xmlRep, ChangePasswordMessage.class);
             Identity id = getIdentity();

             impl.changePassword(uriUserName,message,id);

         } catch (IOException e) {
             throw new ServerException("IOException on server");
         } catch (JAXBException e) {
             throw new ClientException("Bad message body");
         }



    }

}
