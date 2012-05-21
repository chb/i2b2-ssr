package edu.chip.carranet.auth.backend;


import edu.chip.carranet.data.entity.AssertionEntity;
import edu.chip.carranet.data.entity.UserEntity;
import edu.chip.carranet.jaxb.CarraUserInfo;
import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.ExternalExceptions.BadResourceError;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User metadata store which uses Hibernate as the storage backend
 */
@Component
public class HibernateUserStore extends AuthenticatedActionPerformer implements IUserStore {


    @Resource
    protected SessionFactory sessionFactory;

    public HibernateUserStore() {

    }

    @Override
    public List<CarraUserInfo> getUsers(Identity id) throws AuthorizationFailedError {
        verifyToken(id);
        Session s = sessionFactory.getCurrentSession();
        List<UserEntity> users = s.createQuery("from UserEntity").list();


        List<CarraUserInfo> returnList = new ArrayList<CarraUserInfo>();
        for (UserEntity u : users) {
            CarraUserInfo tempUserInfo = new CarraUserInfo();
            tempUserInfo.setUsername(u.getUserName());
            for (AssertionEntity assertion : u.getAssertions()) {
                tempUserInfo.getAssertions().add(assertion.getAssertion());
            }
            returnList.add(tempUserInfo);
        }

        return returnList;

    }

    @Override
    public void updateUserInfo(Identity id, String userName, CarraUserInfo info) throws AuthorizationFailedError, BadResourceError {
        Session s = sessionFactory.getCurrentSession();
        verifyTokenAndAdmin(id);
        UserEntity e = findUserByUserName(userName);
        e.getAssertions().clear();
        for (String assertion : info.getAssertions()) {
            AssertionEntity newEnt = new AssertionEntity();
            newEnt.setAssertion(assertion);
            e.getAssertions().add(newEnt);
        }
        s.update(e);
        s.flush();
    }

    @Override
    public CarraUserInfo getUserInfo(Identity id, String userName) throws BadResourceError, AuthorizationFailedError {
        verifyToken(id);
        UserEntity e = findUserByUserName(userName);
        CarraUserInfo returnVal = new CarraUserInfo();
        returnVal.setUsername(e.getUserName());
        for (AssertionEntity assertion : e.getAssertions()) {
            returnVal.getAssertions().add(assertion.getAssertion());
        }

        return returnVal;
    }

    @Override
    public void addUser(Identity id, String userName, String password) throws AuthorizationFailedError {
        if (countUsers() == 0) {
            addUser(userName, password, true);
            return;
        }

        if (id != null && id.getUsername() != null && id.getUsername().equals(userName)) {
            verifyToken(id);
        } else {
            verifyTokenAndAdmin(id);
        }
        addUser(userName, password, false);

    }

    private void addUser(String userName, String password, boolean isAdmin) {
        UserEntity u = new UserEntity();
        u.setUserName(userName);
        u.setPasswordHash(hashPassword(password, 0));
        if (isAdmin) {
            AssertionEntity adminAssert = new AssertionEntity();
            adminAssert.setAssertion(UserPermissions.admin.toAssertionString());
            u.getAssertions().add(adminAssert);
        }
        Session s = sessionFactory.getCurrentSession();
        s.save(u);
    }

    @Override
    public void deleteUser(Identity id, String userName) throws AuthorizationFailedError,
            BadResourceError {
        verifyTokenAndAdmin(id);
        Session s = sessionFactory.getCurrentSession();
        UserEntity u = findUserByUserName(userName);
        s.delete(u);

    }


    @Override
    public boolean containsUser(Identity id, String userName) throws BadResourceError {
        return findUserByUserName(userName) != null;
    }


    @Transactional(readOnly = true)
    public UserEntity findUserByUserName(String userName) throws BadResourceError {
        Session s = sessionFactory.getCurrentSession();
        Query q = s.createQuery("from UserEntity u where u.userName = :username ");
        q.setString("username", userName);
        UserEntity u = (UserEntity) q.uniqueResult();
        if (u == null) {
            throw new BadResourceError("No Such User");
        } else {
            return u;
        }

    }


    private int countUsers() {
        Session s = sessionFactory.getCurrentSession();
        Integer count = ((Long) s.createQuery("select count(u) from UserEntity u").uniqueResult()).intValue();
        return count;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
