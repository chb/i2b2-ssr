package edu.chip.carranet.auth.backend;

import edu.chip.carranet.data.EventType;
import edu.chip.carranet.data.entity.LogEntryEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Dave Ortiz
 * @date 1/6/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 * <p/>
 * NOTICE: This software comes with NO guarantees whatsoever and is
 * licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class TestLogger {

    @Resource
    IAuditLogger auditLogger;

    @Resource
    SessionFactory sessionFactory;

    @Test
    @SuppressWarnings("unchecked")
    public void testAddLogEvent() throws Exception {
        auditLogger.logEvent(EventType.MODIFY_USER, "TESTUSER", "TESTMESSAGE");
        auditLogger.logEvent(EventType.ADD_USER, "TESTUSER", "ADDING_TEST_USER");
        auditLogger.logEvent(EventType.DELETE_USER, "TESTUSER", "DELETING_TEST_USER");

        Session s = sessionFactory.getCurrentSession();
        Criteria c = s.createCriteria(LogEntryEntity.class);

        List<LogEntryEntity> list = c.list();
        assertEquals(3, list.size());

        assertEquals(list.get(0).getType(), EventType.MODIFY_USER);
        assertEquals(list.get(0).getUser(), "TESTUSER");
        assertEquals(list.get(0).getMessageText(), "TESTMESSAGE");


        assertEquals(list.get(1).getType(), EventType.ADD_USER);
        assertEquals(list.get(1).getUser(), "TESTUSER");
        assertEquals(list.get(1).getMessageText(), "ADDING_TEST_USER");

        assertEquals(list.get(2).getType(), EventType.DELETE_USER);
        assertEquals(list.get(2).getUser(), "TESTUSER");
        assertEquals(list.get(2).getMessageText(), "DELETING_TEST_USER");


    }


}
