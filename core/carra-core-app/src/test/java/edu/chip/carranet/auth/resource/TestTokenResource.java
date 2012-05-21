package edu.chip.carranet.auth.resource;

import edu.chip.carranet.auth.backend.IAuthenticator;
import edu.chip.carranet.auth.backend.IUserStore;
//import org.jmock.Expectations;
//import org.jmock.Mockery;
import org.junit.Test;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.spin.tools.crypto.signature.Identity;

/**
 * Basic test for the token resource
 */
public class TestTokenResource {

//    //Base64 encoded user/password pair dave:password
//    String basicAuthPair1 = "ZGF2ZTpwYXNzd29yZA==";
//
//    //Base64 encoded user/password pair justin:password2
//    String basicAuthPair2 = "anVzdGluOnBhc3N3b3JkMg==";
//
//
//    /**
//     * This test tests the TokenResource
//     */
//    @Test
//    public void testGetToken() throws Exception {
//
//        Mockery context = new Mockery();
//        final IAuthenticator iAuthenticator = context.mock(IAuthenticator.class);
//
//        //Train the mock to do stuff
//        context.checking(new Expectations() {{
//            oneOf(iAuthenticator).authenticate("dave", "password");
//            will(returnValue(new Identity("nodomain", "dave")));
//            oneOf(iAuthenticator).authenticate("justin", "password2");
//            will(returnValue(new Identity("nodomain", "justin")));
//        }});
//
//        //Create a new Component.
//        Component component = new Component();
//
//        // Add a new HTTP server listening on port 8182.
//        component.getServers().add(Protocol.HTTP, 8182);
//
//        Representation r1 = new EmptyRepresentation();
//
//
//    }

}
