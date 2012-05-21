package edu.chip.carranet.carradatapipeline.sitemap;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/17/11
 */
public interface ISiteMap {
     public Connection getConnection(String identifier) throws SQLException;
}
