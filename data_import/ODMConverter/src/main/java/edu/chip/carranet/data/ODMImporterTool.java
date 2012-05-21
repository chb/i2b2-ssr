package edu.chip.carranet.data;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Wrapper class to run ODMImport
 * <p/>
 * user it like
 * java odm-importer.jar -file <file_to_import> -connection <jdbc_connection_string>
 */
public class ODMImporterTool {

    private static Logger log = Logger.getLogger(ODMImporter.class);
    private static String FILE_OPT = "file";
    private static String CONNECTION_STRING_OPT = "connection";
    private static String USERNAME_OPT = "username";
    private static String PASSWORD_OPT = "password";


    public static void main(String[] args) throws Exception {


        OptionParser parser = new OptionParser();
        parser.accepts(FILE_OPT, "file to import").withRequiredArg();
        parser.accepts(CONNECTION_STRING_OPT, "file to import").withRequiredArg();
        parser.accepts(USERNAME_OPT, "database username").withRequiredArg();
        parser.accepts(PASSWORD_OPT, "database password").withRequiredArg();


        OptionSet opts = parser.parse(args);

        if (!(opts.has(FILE_OPT))) {
            log.fatal("Bad options, check em");
            System.exit(1);
        }
        String fileName = (String) opts.valueOf(FILE_OPT);

        File f = new File(fileName);

        if (!f.exists()) {
            log.fatal("No such import file");
            System.exit(1);
        }

        String connectionString = (String) opts.valueOf(CONNECTION_STRING_OPT);
        if (connectionString == null) {
            connectionString = "jdbc:oracle:thin:@chdev1:1521/CHRACDEV";
        }

        String username = (String) opts.valueOf(USERNAME_OPT);
        if (username == null) {
            username = "rc_carra_vm002";
        }

        String password = (String) opts.valueOf(PASSWORD_OPT);
        if (password == null) {
            password = "chracdev";
        }

        Connection connection;
        try {
            connection =
                    DriverManager.getConnection(connectionString,
                            username,
                            password);
        } catch (SQLException e) {
            log.fatal("Error connecting to the database, check your connection string");
            return;
        }

        ODMImporter odmIMporter = new ODMImporter(connection);
        String report = odmIMporter.getReport(new FileInputStream(f));

        log.info("First Stage Results-------------------------------");

        //print out the report
        log.info(report);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        log.info("Do you want to continue(y/n)");

        String response = null;
        try {
            response = br.readLine();
        } catch (IOException ioe) {
            log.fatal("IOException, dying");
            System.exit(1);
        }
        if (response != null && response.equalsIgnoreCase("y")) {
            log.info("Attempting to push changes to the database");
            odmIMporter.processData(new FileInputStream(f));
        }


    }


}
