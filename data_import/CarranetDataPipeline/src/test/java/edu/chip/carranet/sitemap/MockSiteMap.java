package edu.chip.carranet.sitemap;

import edu.chip.carranet.carradatapipeline.sitemap.ISiteMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/17/11
 */
public class MockSiteMap implements ISiteMap {
    private Map<String, MockConnectionInfo> connectionMap;

    public MockSiteMap(Map<String, MockConnectionInfo> siteMap) {
        this.connectionMap = siteMap;
    }

    public static class MockConnectionInfo {
        private String connectionString;
        private String user;
        private String password;

        public MockConnectionInfo(String connectionString, String user, String password) {
            this.connectionString = connectionString;
            this.user = user;
            this.password = password;
        }

        public Connection makeConnection() throws SQLException {
            return DriverManager.getConnection(this.connectionString, this.user, this.password);
        }
    }


    @Override
    public Connection getConnection(String identifier) throws SQLException {
        return connectionMap.get(identifier).makeConnection();
    }


}
