package edu.chip.carranet.overlay.resource;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.junit.runner.RunWith;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoaderListener;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 22, 2010
 */
public abstract class ResourceTestHarness extends JerseyTest {

    public abstract Client getClient();

    protected int port = 9998;

    public ResourceTestHarness() {
        super(new WebAppDescriptor.Builder("edu.chip.carranet")
                .contextPath("/test")
                .contextParam("contextConfigLocation", "/testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }


    public Response sendAuthenticatedRequest(Method m, CarraUserInfo actor, Reference ref, Representation machineRep)
            throws JAXBException, UnsupportedEncodingException, AuthException {
        // create the request and send it off
        String tokenString = makeEncodedTokenString(actor);


        Form f = new Form();
        f.add("x-carra-auth", tokenString);
        Request request = new Request();
        request.getAttributes().put("org.restlet.http.headers", f);
        request.setMethod(m);
        request.setResourceRef(ref);
        request.setEntity(machineRep);

        return getClient().handle(request);
    }


    public static String makeEncodedTokenString(CarraUserInfo carraUser)
            throws AuthException, JAXBException, UnsupportedEncodingException {
        Identity id = TokenUtil.createToken(carraUser);
        return TokenUtil.convertIdentityToAuthString(id);
    }
}
