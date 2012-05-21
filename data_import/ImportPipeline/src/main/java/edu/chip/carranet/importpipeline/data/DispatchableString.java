package edu.chip.carranet.importpipeline.data;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/16/11
 */
public class DispatchableString extends Dispatchable {
    String s;

    public DispatchableString(String s) {
        this.s = s;
    }

    @Override
    public DispatchType getDispatchType() {
        return Dispatchable.DispatchType.STRING;
    }
}
