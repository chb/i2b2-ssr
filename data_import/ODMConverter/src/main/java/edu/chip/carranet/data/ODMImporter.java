package edu.chip.carranet.data;

import org.apache.log4j.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class will perform an import of ODM edu.chip.carranet.data into a database
 */
public class ODMImporter {
    private static final Logger log = Logger.getLogger(ODMImporter.class);
    private Connection sqlConnection;
    private ODMXSLT transformer;

    public ODMImporter(Connection connection) throws TransformerConfigurationException {
        this.sqlConnection = connection;
        transformer = new ODMXSLT();
    }

    /**
     * returns a string of xml that reports what edu.chip.carranet.data changes would be necessary to
     * process the new odm edu.chip.carranet.data.  Returns null if there's a problem.
     *
     * @param odmDataStream
     * @return
     */
    public String getReport(InputStream odmDataStream) throws SQLException, TransformerException {
        String sqlStatements = transformer.transformToReportSql(odmDataStream);
        sqlConnection.setAutoCommit(false);
        return runStepOne(sqlStatements, sqlConnection);
    }


    public void processData(InputStream odmDataStream) throws SQLException, TransformerException {
        String sqlStatements = transformer.transformToProcessSql(odmDataStream);
        sqlConnection.setAutoCommit(false);
        runStepTwo(sqlStatements, sqlConnection);
    }

    protected static String runStepOne(String sqlStatements, Connection con) throws SQLException {
        String report = null;
        String[] sqlList = sqlStatements.split("(?<=(INSERT|TRUNCATE|SELECT).*;)");
        for (int i = 0; i < sqlList.length; i++) {

            Statement statement = con.createStatement();
            String sql = sqlList[i].replace(";", "");

            if(sql.replace(" ", "").isEmpty()) {
                continue;
            }

            try {
                // select statement is special
                if(sql.replace(" ", "").startsWith("SELECT")) {
                    ResultSet rSet = statement.executeQuery(sql);
                    if (rSet.next()) {
                        report = rSet.getString(1);
                    }
                } else {
                    statement.execute(sql);
                }
                con.commit();
            }
            catch (SQLException e) {
                log.error("Failed to run SQL: " + sql);
                throw e;
            }
            finally {
                statement.close();
            }
        }

        return report;
    }


    protected static void runStepTwo(String sql, Connection con) throws SQLException {
        Statement statement = con.createStatement();
        String[] stringLines = sql.split(";");
        for (int i = 0; i < stringLines.length; ++i) {
            String sqlLine = stringLines[i];
            if(sqlLine.replace(" ", "").replace("\n", "").replace("\t","").isEmpty()) {
                continue;
            }
//            s.addBatch(statement.replace(";", ""));
            String lastStatement = sqlLine.replace(";", "");
            try {
                statement.execute(sqlLine.replace(";", ""));
            } catch (SQLException e) {
//                System.out.println(lastStatement);
                throw e;
            }
            con.commit();
        }

//        s.executeBatch();
        con.commit();
    }

}
