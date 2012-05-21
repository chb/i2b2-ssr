package edu.chip.carranet.importpipeline.dispatch;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: 2/16/11
 * Time: 1:34 AM
 * To change this template use File | Settings | File Templates.
 */
// TODO: interface insteead?
public abstract class Dispatchable {
    // TODO: these don't belong here unless the corresponding handlers are going to be here
    // further, these should be a set of base types that have handlers in the lib
    // people should extend this (which you can't with enums) to add their own dispatchable types.
    public static enum DispatchType {
        TEST,
        ODMData,
        SQLCommands,
        STRING
    }

    abstract public DispatchType getDispatchType();
}
