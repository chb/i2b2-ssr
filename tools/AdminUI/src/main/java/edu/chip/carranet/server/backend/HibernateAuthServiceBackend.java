package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.auth.backend.IAuthenticator;
import edu.chip.carranet.auth.backend.IUserStore;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.auth.backend.UserUtil;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.data.entity.AssertionEntity;
import edu.chip.carranet.data.entity.UserEntity;
import edu.chip.carranet.jaxb.CarraUser;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class HibernateAuthServiceBackend implements IAuthBackend {

    @Autowired
    private IUserStore userStore;

    @Autowired
    private SessionFactory sf;


    @Autowired
    private IAuthenticator authenticator;


    @Override
    public Token authorizeUser(String userName, String password) throws AuthClientException {
        Identity id = authenticator.authenticate(userName, password);
        return new Token(id);

    }

    @Override
    @Transactional
    public ArrayList<UserDTO> getUsers(Token t) throws AuthClientException {
        Identity id = TokenUtil.authStringToIdentity(t.getAuthString());
        ArrayList<UserDTO> returnList = new ArrayList<UserDTO>();
        for (CarraUserInfo u : userStore.getUsers(id)) {
            UserDTO temp = new UserDTO();
            temp.setAssertionStrings(u.getAssertions());
            temp.setUserName(u.getUsername());
            returnList.add(temp);

        }

        return returnList;
    }

    @Override
    @Transactional
    public UserDTO deleteUser(Token t, UserDTO u) throws AuthClientException {
        userStore.deleteUser(TokenUtil.authStringToIdentity(t.getAuthString()), u.getUserName());
        return u;
    }

    @Override
    @Transactional
    public UserDTO addUser(Token t, UserDTO u, String newPassword) throws AuthClientException {
        if (isAdmin(t)) {
            Session s = sf.getCurrentSession();

            Query q = s.createQuery("select count(u) from UserEntity u where u.userName = :userName");
            q.setString("userName", u.getUserName());

            int count = ((Long) q.iterate().next()).intValue();
            if (count > 0) {
                throw new AuthClientException("User already exists");
            }


            UserEntity entity = new UserEntity();
            entity.setUserName(u.getUserName());
            List<AssertionEntity> assertionsEntityList = new ArrayList<AssertionEntity>();
            for (String assertion : u.getAssertionStrings()) {
                AssertionEntity e = new AssertionEntity();
                e.setAssertion(assertion);
                assertionsEntityList.add(e);
            }
            entity.getAssertions().addAll(assertionsEntityList);
            s.save(entity);
            s.flush();
        }

        return u;
    }

    @Override
    @Transactional
    public UserDTO updateUser(Token t, UserDTO dto) throws AuthClientException {
        CarraUserInfo userInfo = new CarraUserInfo();
        userInfo.setUsername(dto.getUserName());
        userInfo.getAssertions().addAll(dto.getAssertionStrings());
        userStore.updateUserInfo(TokenUtil.authStringToIdentity(t.getAuthString()), dto.getUserName(), userInfo);
        return dto;
    }

    @Override
    @Transactional
    public UserDTO getUser(Token t, String userName) throws AuthClientException {
        CarraUserInfo user = userStore.getUserInfo(TokenUtil.authStringToIdentity(t.getAuthString()), userName);
        UserDTO returnVal = new UserDTO();

        returnVal.setUserName(user.getUsername());
        returnVal.setAssertionStrings(user.getAssertions());

        return returnVal;
    }

    @Override
    @Transactional
    public boolean canChangePassword(Token t) throws AuthClientException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private boolean isAdmin(Token t) {
        Identity id = TokenUtil.authStringToIdentity(t.getAuthString());
        return TokenUtil.verifyToken(id) && id.getAssertion().contains("role:admin");
    }
}
