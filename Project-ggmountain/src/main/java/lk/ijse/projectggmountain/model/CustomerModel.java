package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.projectggmountain.db.DBConnection;
import lk.ijse.projectggmountain.dto.Customer;
import lk.ijse.projectggmountain.dto.tm.CustomerTM;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE custId=?";
        return CrudUtil.execute(sql,id);
    }

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer(custId,custName,address,email,contact) " +
                "VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql,customer.getId(),customer.getName(),customer.getAddress(),customer.getEmail(),customer.getContact());

    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET custName=?,address=?,email=?,contact=? WHERE custId=?";

        return CrudUtil.execute(sql,customer.getName(),customer.getAddress(),customer.getEmail(),customer.getContact(),customer.getId());

    }

    public static ObservableList<CustomerTM> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";


        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            obList.add(new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return obList;
    }

    public static Customer findById(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE custId=?";

        ResultSet resultSet = CrudUtil.execute(sql,id);
        if(resultSet.next()){
            return (new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return null;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT custId FROM Customer");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static String getCustName(String custId) throws SQLException {
        String sql = "SELECT custName FROM customer WHERE custId=?";
        ResultSet resultSet = CrudUtil.execute(sql,custId);

        if(resultSet.next()){
            return (new String(
                    resultSet.getString(1)
            ));
        }
        return null;
    }
}
