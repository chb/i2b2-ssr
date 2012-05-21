package edu.chip.carranet.importpipeline.output;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: Oct 22, 2010
 * Time: 3:49:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Outputter<T extends Dispatchable> {
    public void output(T data) throws OutputterException;
}
