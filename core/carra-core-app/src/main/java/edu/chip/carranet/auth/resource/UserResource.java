//package edu.chip.carranet.auth.resource;
//
//import edu.chip.carranet.auth.UserStoreFactory;
//import edu.chip.carranet.auth.backend.IUserStore;
//import edu.chip.carranet.auth.exception.AuthException;
//import edu.chip.carranet.auth.resource.impl.UserResourceImpl;
//import org.restlet.representation.Representation;
//import org.restlet.resource.Get;
//
//import javax.annotation.Resource;
//
//
//public class UserResource extends AbstractAuthResource {
//
//    @Resource
//    UserResourceImpl impl;
//
//    @Resource
//    IUserStore userStore = UserStoreFactory.getUserStore();
//
//    /**
//     * Returns all the users
//     *
//     * @return
//     */
//    @Get
//    public String getUsers()  {
//        return impl.getUsers(getIdentity());
//    }
//}
