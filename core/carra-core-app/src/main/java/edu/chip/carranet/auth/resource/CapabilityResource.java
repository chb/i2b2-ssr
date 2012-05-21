package edu.chip.carranet.auth.resource;

import edu.chip.carranet.auth.backend.BackendUtil;
import edu.chip.carranet.auth.backend.IAuthenticator;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ServerException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.spin.tools.JAXBUtils;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;


/**
 *
 */
public class CapabilityResource extends AbstractAuthResource {

    @Resource
    IAuthenticator authenticator;

    @Get
    public Representation getCapabilities() throws AuthException {
        try {
            //only authenticated users can get capabilities
            BackendUtil.verifyToken(getIdentity());
            return new StringRepresentation(JAXBUtils.marshalToString(authenticator.getAuthCapabilities()));
        } catch (JAXBException e) {
            throw new ServerException("Can't unmarshall capabilities message");
        }

    }


}
