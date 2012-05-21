package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionDBStore;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A helper app to create the tables for the import pipeline.  Takes a config
 *
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/24/11
 */
public class InitPipelineStoreTables {

    public static void main(String[] args) throws Exception {
        if(args.length == 0) {
            System.out.println("Usage: InitPipelineStoreTables propertiesfile");
            System.exit(1);
        }

        File f = new File(args[0]);
        if(!f.canRead() || !f.isFile()) {
            System.out.println("problem reading " + f.getPath());
            System.exit(1);
        }

        // setup config sources
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new PropertiesConfiguration(f));

        // build objects
        String transactionTableName = config.getString(PipelineConfig.TRANSACTION_TABLE_NAME);
        String siteTableName = config.getString(PipelineConfig.SITE_TRANSACTION_TABLE_NAME);
        String connectionString = config.getString(PipelineConfig.DB_CONNECTION_STRING);
        String user = config.getString(PipelineConfig.DB_USER);
        String passwd = config.getString(PipelineConfig.DB_PASSWORD);

        System.out.println("Connecting to db");
        Connection connection = DriverManager.getConnection(connectionString, user, passwd);
        connection.setAutoCommit(false);
        System.out.println("Connected");

        System.out.println("Creating table " + transactionTableName);
        InformTransactionDBStore.createTable(connection, transactionTableName);
        System.out.println("Creating table " + siteTableName);
        SiteTransactionDBStore.createTable(connection, siteTableName);
    }
}
