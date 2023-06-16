package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lk.ijse.projectggmountain.db.DBConnection;
import lk.ijse.projectggmountain.dto.CartDTO;
import lk.ijse.projectggmountain.dto.Inventory;
import lk.ijse.projectggmountain.dto.NewLoadSupplier;
import lk.ijse.projectggmountain.dto.tm.InventoryTM;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE itemId=?";

        return CrudUtil.execute(sql, id);
    }

    public static boolean save(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO item(itemId,itemName,category,unitPrice,qtyOnHand)" +
                "VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql, inventory.getId(), inventory.getName(), inventory.getCategory(), inventory.getUnitPrice(), inventory.getQtyOnHand());

    }

    public static boolean update(Inventory inventory) throws SQLException {
        String sql = "UPDATE item SET itemName=?,category=?,unitPrice=?,qtyOnHand=? WHERE itemId=?";

        return CrudUtil.execute(sql, inventory.getName(), inventory.getCategory(), inventory.getUnitPrice(), inventory.getQtyOnHand(), inventory.getId());

    }

    public static ObservableList<InventoryTM> getAll() throws SQLException {
        String sql = "SELECT * FROM item";

        ObservableList<InventoryTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            obList.add(new InventoryTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }
        return obList;
    }

    public static Inventory findById(String id) throws SQLException {
        String sql = "SELECT * FROM item WHERE itemId=?";

        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()) {
            return (new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }
        return null;
    }

    public static List<String> loadItemIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT itemId FROM Item");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static boolean updateQty(List<CartDTO> placeOrderList) throws SQLException {
        System.out.println("updateQtyList");
        for (CartDTO placeorder : placeOrderList) {
            if (!updateQty(placeorder)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CartDTO placeorder) throws SQLException {
        System.out.println("updateQty");
        String sql = "UPDATE item SET QtyOnHand = (QtyOnHand - ?) WHERE itemId = ?";

        return CrudUtil.execute(
                sql,
                placeorder.getOrderQty(),
                placeorder.getItemId()
        );
    }

    public static boolean addQty(List<NewLoadSupplier> newLoadList) throws SQLException {
        for (NewLoadSupplier newLoadSupplier : newLoadList) {
            if (!addQty(newLoadSupplier)) {
                return false;
            }
        }
        return true;
    }

    private static boolean addQty(NewLoadSupplier newLoadSupplier) throws SQLException {
        String sql = "UPDATE item SET QtyOnHand = (QtyOnHand + ?) WHERE itemId = ?";

        return CrudUtil.execute(
                sql,
                newLoadSupplier.getSupQty(),
                newLoadSupplier.getItemId()
        );
    }

    public static ObservableList<XYChart.Series<String, Integer>> getDataToBarChart() throws SQLException {
        String sql="SELECT itemName,QtyOnHand FROM item WHERE QtyOnHand<=100 ";

        ObservableList<XYChart.Series<String, Integer>> datalist =FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute(sql);

        // Creating a new series object
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        while(resultSet.next()){
            String itemName = resultSet.getString("itemName");
            int itemQty = resultSet.getInt("QtyOnHand");
            series.getData().add(new XYChart.Data<>(itemName, itemQty));
        }

        datalist.add(series);
        return datalist;
    }
}
