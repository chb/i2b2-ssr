//package edu.chip.carranet.auth.resource;
//
//import edu.chip.carranet.auth.exception.AuthException;
//import edu.chip.carranet.auth.exception.ClientException;
//import edu.chip.carranet.auth.exception.InternalError;
//import edu.chip.carranet.auth.resource.impl.UserManagementResourceImpl;
//import edu.chip.carranet.jaxb.CarraUser;
//import edu.chip.carranet.jaxb.CreateUserMessage;
//import org.apache.log4j.Logger;
//import org.restlet.representation.Representation;
//import org.restlet.resource.Delete;
//import org.restlet.resource.Get;
//import org.restlet.resource.Post;
//import org.restlet.resource.Put;
//import org.spin.tools.JAXBUtils;
//import org.spin.tools.crypto.signature.Identity;
//
//import javax.annotation.Resource;
//import javax.xml.bind.JAXBException;
//import java.io.IOException;
//
//
///**
// * This Resource is responsible for creating/updating/deleting
// * user entries
// */
//public class UserManagementResource extends AbstractAuthResource {
//    Logger log = Logger.getLogger(getClass());
//
//    @Resource
//    UserManagementResourceImpl impl;
//
//    @Put
//    public Representation updateUser(Representation representation) throws AuthException {
//        try {
//            String xmlRep = representation.getText();
//            CarraUser message = JAXBUtils.unmarshal(xmlRep, CarraUser.class);
//            Identity id = getIdentity();
//            String uriUserName = (String) getRequestAttributes().get("user");
//            return impl.updateUser(id, uriUserName, message);
//
//        } catch(IOException e) {
//            log.fatal("Error getting post body");
//            throw new InternalError("Server Error");
//
//        } catch(JAXBException e) {
//            log.fatal("Error unmarshalling the post body");
//            throw new ClientException("Incorrectly formatted message");
//
//        }
//
//    }
//
//    @Post
//    public Representation createUser(Representation post) throws AuthException {
//        try {
//            String xmlPost = post.getText();
//            CreateUserMessage message = JAXBUtils.unmarshal(xmlPost, CreateUserMessage.class);
//            Identity id = getIdentity();
//            return impl.createUser(message, id);
//        } catch(IOException e) {
//            log.fatal("Error getting post body");
//            throw new ClientException("IOException");
//        } catch(JAXBException e) {
//            log.fatal("Error unmarshalling the post body");
//            throw new ClientException("Error parsing message body");
//        }
//    }
//
//    @Delete
//    public Representation deleteUser() throws AuthException {
//        Identity id = getIdentity();
//        String userName = (String) getRequestAttributes().get("user");
//        return impl.deleteUser(userName, id);
//    }
//
//    @Get
//    public Representation getUser() throws AuthException {
//        Identity id = getIdentity();
//        String userName = (String) getRequestAttributes().get("user");
//        return impl.getUser(userName, id);
//    }
//}
//
//
