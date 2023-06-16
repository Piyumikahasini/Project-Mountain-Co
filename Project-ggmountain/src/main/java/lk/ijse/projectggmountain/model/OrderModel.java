package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.projectggmountain.db.DBConnection;
import lk.ijse.projectggmountain.dto.CartDTO;
import lk.ijse.projectggmountain.dto.NewDelivery;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    static NewDelivery gotnewdelivery;

    public static String getNextOrderId() throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("ORD-");
            int id = Integer.parseInt(strings[1]);
            ++id;
            String digit=String.format("%03d", id);
            return "ORD-" + digit;
        }
        return "ORD-001";
    }

    public static boolean placeOrder(String oId, String cusId, Boolean delivery, String totle, List<CartDTO> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = save(oId, cusId, delivery,LocalDate.now(), LocalTime.now(), totle );
            if(isSaved) {
                System.out.println("isSaved");
                boolean isUpdated = InventoryModel.updateQty(cartDTOList);
                if(isUpdated) {
                    System.out.println("isUpdated");
                    boolean isOrdered = OrderDetailModel.save(oId, cartDTOList);
                    if(isOrdered) {
                        System.out.println("isOrdered");
                        if (delivery) {
                            boolean isDelivered = NewDeliveryModel.save(gotnewdelivery);
                            if (isDelivered) {
                                con.commit();
                                return true;
                            }
                        } else {
                            con.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

    private static boolean save(String orderId, String custId, Boolean delivery, LocalDate date, LocalTime time, String ordpay) throws SQLException {
        String sql = "INSERT INTO orders(orderId,date,time,deliveryStatus,payment,custId) VALUES(?,?,?,?,?,?)";

        return  CrudUtil.execute(
                sql,
                orderId,
                date,
                time,
                delivery,
                ordpay,
                custId
        );
    }

    public static void sendObject(NewDelivery newDelivery) {
        gotnewdelivery = newDelivery;
    }

    public static ObservableList<PieChart.Data> getDataToPieChart() throws SQLException {
        String sql="SELECT item.itemName,COUNT(orderitemdetail.itemId) "+
                "FROM orderitemdetail "+
                "INNER JOIN item "+
                "ON item.itemId = orderitemdetail.itemId " +
                "INNER JOIN orders " +
                "ON orderitemdetail.orderId=orders.orderId " +
                "WHERE MONTH(orders.date) = MONTH(CURRENT_DATE()) " +
                "GROUP BY item.itemName " +
                "LIMIT 5 ";
        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute(sql);

        while(resultSet.next()){
            datalist.add(
                    new PieChart.Data(
                            resultSet.getString(1),
                            resultSet.getInt(2)
                    )
            );
        }
        return datalist;
    }

    public static List<XYChart.Data<String, Double>> getDataToAreaChart(String year) throws SQLException {
        String sql= "SELECT MONTHNAME(date) AS month,SUM(payment) AS total_income FROM orders WHERE YEAR(date)=? GROUP BY month ORDER BY month desc";

        List<XYChart.Data<String, Double>> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql,year);

        while(resultSet.next()){
            String month = resultSet.getString("month");
            double income = resultSet.getDouble("total_income");
            data.add(new XYChart.Data<>(month, income));
        }
        return data;
    }

    public static boolean updatedelivery(String ordid) throws SQLException {
        String sql = "UPDATE orders SET delivery=? WHERE orderId=?";

        return CrudUtil.execute(
                sql,
                false,
                ordid
        );
    }
}
