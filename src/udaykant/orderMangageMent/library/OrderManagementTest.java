package udaykant.orderMangageMent.library;

import java.sql.*;
import java.sql.Statement;

import junit.framework.Assert;
import org.junit.Test;


public class OrderManagementTest {
    @org.junit.Before
    public void setUp() throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql;
        sql = "CREATE SCHEMA order_management";
        statement.executeUpdate(sql);
        sql = "CREATE TABLE order_management.products" +
                "( product_id Integer NOT NULL," +
                "product_name VARCHAR(100)," +
                "unit_price INTEGER not NULL," +
                "primary key(product_id))";
        statement.executeUpdate(sql);

    }

    public Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "test";
        String driver = "org.mariadb.jdbc.Driver";
        String userName = "udaykant";
        String password = "12345678";
        Connection connection = null;

        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;


    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert_item_into_product() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.products values('100','sugar','50')";
        int actual = statement.executeUpdate(sql);
        Assert.assertEquals(actual, 1);
        closeConnection(connection);
    }


    @org.junit.After
    public void tearDown() throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();

        String sql = "DROP SCHEMA order_management";
        statement.executeUpdate(sql);
        closeConnection(connection);
    }

}

