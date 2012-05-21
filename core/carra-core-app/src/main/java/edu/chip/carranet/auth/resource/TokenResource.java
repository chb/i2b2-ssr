//package edu.chip.carranet.auth.resource;
//
//import edu.chip.carranet.auth.backend.IAuthenticator;
//import edu.chip.carranet.auth.backend.TokenUtil;
//import edu.chip.carranet.auth.backend.User;
//import edu.chip.carranet.auth.exception.AuthException;
//import edu.chip.carranet.auth.resource.impl.TokenResourceImpl;
//import org.apache.log4j.Logger;
//import org.restlet.edu.chip.carranet.data.ChallengeResponse;
//import org.restlet.representation.Representation;
//import org.restlet.resource.Get;
//
//import javax.annotation.Resource;
//
//
//public class TokenResource extends AbstractAuthResource {
//    private static Logger log = Logger.getLogger(TokenResource.class);
//
//    @Resource
//    TokenResourceImpl impl;
//
//    @Resource
//    private IAuthenticator authenticator;
//
//    @Get
//    public String getToken() throws AuthException {
//        ChallengeResponse ch = getChallengeResponse();
//
//        User user = TokenUtil.userFromHttpBasicAuth(new String(ch.getIdentifier()), new String(ch.getSecret()));
//
//        return impl.getToken(user);
//    }
//
//}
