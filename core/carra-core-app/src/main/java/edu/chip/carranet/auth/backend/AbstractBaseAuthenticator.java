package edu.chip.carranet.auth.backend;

import edu.chip.carranet.data.entity.AssertionEntity;
import edu.chip.carranet.data.entity.UserEntity;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class is the basis of any authenticator that will be talking to the userlying database
 * <p/>
 * This class assumes we'll be using Hibernate to talk to the database
 */
@Transactional
abstract public class AbstractBaseAuthenticator extends AuthenticatedActionPerformer implements IAuthenticator {
    Logger log = Logger.getLogger(AbstractBaseAuthenticator.class);

    @Resource
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public User findUser(String username){
        Session session = sessionFactory.getCurrentSession();
        List<UserEntity> entities;
        Query q = session.createQuery("from UserEntity e where e.userName = :userName");
        q.setString("userName", username);
        entities = q.list();


        //correct case should only be 1
        if (entities.size() > 0) {
            if (entities.size() > 1) {
                log.fatal("More than 1 instance of user in database, problems ahead");
            }
            UserEntity entity = entities.get(0);
            User u = new User();
            u.setUserName(username);
            for (AssertionEntity e : entity.getAssertions()) {
                u.getAssertions().add(e.getAssertion());
            }

            u.setHashedPassword(entity.getPasswordHash());

            return u;
        }
        else{
            return null;
        }

    }
}
