package edu.chip.carranet.auth.backend;


import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.ExternalExceptions.ResourceNotFoundError;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ClientException;
import edu.chip.carranet.data.entity.UserEntity;
import edu.chip.carranet.jaxb.AuthCapabilities;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Authenticates against the DB locally
 */

@Transactional
public class LocalAuthenticator extends AbstractBaseAuthenticator {

    Logger log = Logger.getLogger(LocalAuthenticator.class);


    @Resource
    private SessionFactory sessionFactory;

    protected BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Override
    @Transactional
    public Identity authenticate(String username, String password) {
        User u = findUser(username);

        if (u == null || password == null) {
            throw new AuthorizationFailedError("Bad username or password");
        }

        if (passwordEncryptor.checkPassword(password, u.getHashedPassword())) {
            CarraUserInfo userInfo = BackendUtil.createUserInfo(u.getUserName(), u.getAssertions());
            Identity id = TokenUtil.createToken(userInfo);
            return id;
        } else {
            throw new AuthorizationFailedError("Bad username or password");
        }

    }

    @Override
    @Transactional
    public void changeUserPassword(Identity id, String username, String oldPassword, String newPassword) throws AuthException {
        if (id.getUsername().equals(username)) {
            this.verifyToken(id);
            UserEntity u = findUserByUsername(username);
            if (passwordEncryptor.checkPassword(oldPassword, u.getPasswordHash())) {
                u.setPasswordHash(hashPassword(newPassword, 0));
                Session s = sessionFactory.getCurrentSession();
                s.save(u);
            } else {
                throw new ClientException("Old password didn't match");
            }
        } else {
            verifyTokenAndAdmin(id);
            UserEntity u = findUserByUsername(username);

            u.setPasswordHash(hashPassword(newPassword, 0));
            Session s = sessionFactory.getCurrentSession();
            s.save(u);
        }

    }


    @Override
    public AuthCapabilities getAuthCapabilities() {
        AuthCapabilities returnVal = new AuthCapabilities();
        returnVal.getCapability().add(AuthCapabilityEnum.CHANGE_PASSWORD.getStringValue());
        return returnVal;
    }


    private UserEntity findUserByUsername(String username) throws ClientException {
        Session s = sessionFactory.getCurrentSession();
        Query q = s.createQuery("from UserEntity u where u.userName = :username ");
        q.setString("username", username);

        UserEntity u = (UserEntity) q.uniqueResult();
        if (u == null) {
            throw new ResourceNotFoundError("No such user");
        }
        return u;
    }


}

