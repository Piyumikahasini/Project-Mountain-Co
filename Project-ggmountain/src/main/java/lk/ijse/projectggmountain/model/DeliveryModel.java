package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.projectggmountain.dto.Delivery;
import lk.ijse.projectggmountain.dto.tm.DeliveryTM;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryModel {
    public static ObservableList<DeliveryTM> getAll() throws SQLException {
        String sql = "SELECT * FROM delivery";

        ObservableList<DeliveryTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            obList.add(new DeliveryTM(
                    resultSet.getString(5),
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(6)
            ));
        }
        return obList;
    }

    public static boolean update(Delivery delivery) throws SQLException {
        String sql = "UPDATE delivery SET deliveryStatus=?,date=?,location=?,orderId=?,empId=? WHERE deliveryId=?";

        return CrudUtil.execute(
                sql,
                delivery.getDelsts(),
                delivery.getDeldate(),
                delivery.getLoc(),
                delivery.getOrdid(),
                delivery.getEmpid(),
                delivery.getDelid()
        );
    }

    public static boolean delete(String delid) throws SQLException {
        String sql = "DELETE FROM delivery WHERE deliveryId=?";

        return CrudUtil.execute(sql,delid);
    }

    public static Delivery findById(String id) throws SQLException {
        String sql = "SELECT * FROM delivery WHERE orderId=?";

        ResultSet resultSet = CrudUtil.execute(sql,id);
        if(resultSet.next()){
            return (new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return null;
    }

    public static ObservableList<DeliveryTM> getByDeliveryStatus(String delists) throws SQLException {
        String sql = "SELECT * FROM delivery WHERE deliveryStatus=?";

        ObservableList<DeliveryTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql,delists);
        while (resultSet.next()) {
            obList.add(new DeliveryTM(
                    resultSet.getString(5),
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(6)
            ));
        }
        return obList;
    }

    public static ObservableList<DeliveryTM> getByDueDate(String date) throws SQLException {
        String sql = "SELECT * FROM delivery WHERE date=?";

        ObservableList<DeliveryTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql,date);
        while (resultSet.next()) {
            obList.add(new DeliveryTM(
                    resultSet.getString(5),
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(6)
            ));
        }
        return obList;
    }

    public static Delivery findBydeliveryId(String delid) throws SQLException {
        String sql = "SELECT * FROM delivery WHERE deliveryId=?";

        ResultSet resultSet = CrudUtil.execute(sql,delid);
        if(resultSet.next()){
            return (new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return null;
    }
}

