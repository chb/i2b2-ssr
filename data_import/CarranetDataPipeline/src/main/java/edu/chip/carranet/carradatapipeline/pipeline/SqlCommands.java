package edu.chip.carranet.carradatapipeline.pipeline;

import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionRecord;
import edu.chip.carranet.importpipeline.dispatch.Dispatchable;

import java.util.Map;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/3/11
 */
public class SqlCommands extends Dispatchable {

    private Map<String,String> sqlCommands;
    private InformTransactionRecord transactionRecord;

    public SqlCommands(Map<String,String> sqlCommands, InformTransactionRecord transactionRecord) {
        this.sqlCommands = sqlCommands;
        this.transactionRecord = transactionRecord;
    }

    @Override
    public DispatchType getDispatchType() {
        return Dispatchable.DispatchType.SQLCommands;
    }

    public Map<String, String> getSqlCommands() {
        return sqlCommands;
    }

    public InformTransactionRecord getTransactionRecord() {
        return transactionRecord;
    }
}
