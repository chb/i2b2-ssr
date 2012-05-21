package edu.chip.carranet.importpipeline.process;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;

/**
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public interface Processor<I extends Dispatchable, O extends Dispatchable> {
    public O process(I data) throws ProcessException;
}
