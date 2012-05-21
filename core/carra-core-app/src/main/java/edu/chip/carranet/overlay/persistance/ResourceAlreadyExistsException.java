package edu.chip.carranet.overlay.persistance;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 15, 2010
 */
public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException() {
        super();
    }

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}
