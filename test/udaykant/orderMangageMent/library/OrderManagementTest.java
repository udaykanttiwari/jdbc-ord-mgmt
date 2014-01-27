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
        statement.execute(sql);
        statement.close();
        statement = connection.createStatement();
        sql = "CREATE TABLE order_management.customer_detail" +
                "(customer_id Integer NOT NULL," +
                "customer_name varchar(20)," +
                "address1 varchar(100)," +
                "address2 varchar(100)," +
                "city varchar(30)," +
                "pincode int )";
        statement.execute(sql);
        statement.close();
        statement = connection.createStatement();
        sql = "alter  table order_management.customer_detail add primary key(customer_id)";
        statement.execute(sql);
        statement.close();
        statement = connection.createStatement();

        sql = "CREATE TABLE order_management.order_item_info" +
                "(order_id Integer NOT NULL," +
                "customer_id Integer ," +
                "date_of_order date," +
                "date_of_delivery date," +
                "total_amount float)";
        statement.execute(sql);
        statement.close();
        statement = connection.createStatement();
        sql = "ALTER TABLE order_management.order_item_info add primary key(order_id)";
        statement.execute(sql);
        statement.close();

        statement = connection.createStatement();
        sql = "ALTER TABLE order_management.order_item_info add constraint customer_id_fk foreign key (customer_id)" +
                "references customer_detail(customer_id)";
        statement.execute(sql);
        statement.close();
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
            connection = DriverManager.getConnection(url + dbName, userName, password);
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
    public void insert_item_into_products() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.products values('100','sugar','50')";
        int actual = statement.executeUpdate(sql);
        Assert.assertEquals(actual, 1);
        closeConnection(connection);
    }

    @Test
    public void insert_item_into_products_and_remove_items() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.products values('100','sugar','50')";
        statement.executeUpdate(sql);
        String sql1 = "DELETE FROM order_management.products where product_id = 100";
        int actual = statement.executeUpdate(sql1);
        Assert.assertEquals(actual, 1);

        closeConnection(connection);
    }

    @Test
    public void insert_item_ant_test_whether_item_inserted() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.products values('100','sugar','50')";
        statement.executeUpdate(sql);
        sql = "select * from order_management.products";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int product_id = resultSet.getInt("product_id");
            org.junit.Assert.assertTrue(product_id == 100);
        }
        closeConnection(connection);
    }

    @Test
    public void insert_customer_detail_into_table() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        int actual = statement.executeUpdate(sql);
        org.junit.Assert.assertTrue(actual == 1);
    }

    @Test
            (expected = SQLIntegrityConstraintViolationException.class)
    public void should_not_insert_duplicate_primary_key_into_table_and_should_give_error() throws SQLException {
        Connection connection = createConnection();
        int expected = 1;
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        String sql1 = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";

        statement.executeUpdate(sql);
        int actual = statement.executeUpdate(sql1);
        org.junit.Assert.assertEquals(expected, actual);
    }
    @Test
    public void inserted_customer_detail() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        int actual = statement.executeUpdate(sql);
        org.junit.Assert.assertTrue(actual == 1);
    }

    @Test
    public void delete_inserted_customer_detail() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        statement.executeUpdate(sql);
        sql = "DELETE FROM order_management.customer_detail where customer_id = 1";
        int actual = statement.executeUpdate(sql);
        org.junit.Assert.assertTrue(actual == 1);
    }
    @Test
    public void insert_order_info_into_order_info_table() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        statement.executeUpdate(sql);
        String queryForInsertingOrderInfo = "INSERT INTO order_management.order_item_info(order_id,customer_id,date_of_order,date_of_delivery,total_amount" +
        ")  values ('101','1','2014-01-23','2014-01-26','1000')";
        int actual = statement.executeUpdate(queryForInsertingOrderInfo);
        org.junit.Assert.assertTrue(actual == 1);
    }
    @Test
    public void delete_order_info_where_order_id_is_one_not_one() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        statement.executeUpdate(sql);
        String queryForInsertingOrderInfo = "INSERT INTO order_management.order_item_info(order_id,customer_id,date_of_order,date_of_delivery,total_amount" +
                ")  values ('101','1','2014-01-23','2014-01-26','1000')";
        statement.executeUpdate(queryForInsertingOrderInfo);
        String queryForDeleteOrder = "delete from order_management.order_item_info where order_id =101";
        int actual = statement.executeUpdate(queryForDeleteOrder);
        org.junit.Assert.assertTrue(actual == 1);
    }
    @Test
            (expected = SQLIntegrityConstraintViolationException.class)
    public void delete_customer_id_when_customer_id_is_foreign_key_in_order_info() throws SQLException {
        int expected = 0;
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO order_management.customer_detail(customer_id,customer_name,address1," +
                "address2,pincode)  values ('1','uday','bodva','balipur','230139')";
        statement.executeUpdate(sql);
        String queryForInsertingOrderInfo = "INSERT INTO order_management.order_item_info(order_id,customer_id,date_of_order,date_of_delivery,total_amount" +
                ")  values ('101','1','2014-01-23','2014-01-26','1000')";
        statement.executeUpdate(queryForInsertingOrderInfo);
        String queryForDeleteOrder = "delete from order_management.customer_detail where customer_id =1";
        int actual = statement.executeUpdate(queryForDeleteOrder);
        org.junit.Assert.assertTrue(actual == expected);
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

