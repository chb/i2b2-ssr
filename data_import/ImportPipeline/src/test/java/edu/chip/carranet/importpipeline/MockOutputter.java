package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.output.OutputterException;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: 2/16/11
 * Time: 5:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockOutputter implements Outputter {
    private StringBuffer sb;

    public MockOutputter(StringBuffer sb) {
        this.sb = sb;
    }

    @Override
    public void output(Dispatchable data) throws OutputterException {
        sb.append(((MockProcessor.LongData)data).l + "\n");
    }
}
