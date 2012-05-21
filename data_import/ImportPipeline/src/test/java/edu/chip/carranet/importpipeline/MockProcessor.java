package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.process.ProcessException;
import edu.chip.carranet.importpipeline.process.Processor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: 2/16/11
 * Time: 3:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockProcessor implements Processor {
    private static final Logger log = Logger.getLogger(MockProcessor.class);


    public static class LongData extends Dispatchable {
        Long l;

        public LongData(Long l) {
            this.l = l;
        }

        @Override
        public DispatchType getDispatchType() {
            return Dispatchable.DispatchType.TEST;
        }
    }

    @Override
    public Dispatchable process(Dispatchable data) throws ProcessException {
        log.debug("process");
        // TODO fix
        return new LongData(Long.parseLong(((MockFetcher.StringData)data).s));
    }
}
