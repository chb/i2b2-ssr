package edu.chip.carranet.auth.backend;

import edu.chip.carranet.data.EventType;

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
public interface IAuditLogger {

    public void logEvent(EventType type, String user, String message);
}
