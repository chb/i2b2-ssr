package edu.chip.carranet.auth.backend;

import edu.chip.carranet.data.EventType;
import edu.chip.carranet.data.entity.LogEntryEntity;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

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


public class HibernateAuditLogger implements IAuditLogger {


    @Resource
    SessionFactory sessionFactory;

    /**
     * Log the request to the database
     *
     * @param type
     * @param message
     */
    public void logEvent(EventType type, String user, String message) {
        LogEntryEntity entry = new LogEntryEntity(type,user, message);
        sessionFactory.getCurrentSession().save(entry);
    }


}
