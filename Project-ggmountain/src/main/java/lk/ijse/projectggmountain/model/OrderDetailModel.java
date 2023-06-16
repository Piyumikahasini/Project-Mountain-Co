package lk.ijse.projectggmountain.model;

import lk.ijse.projectggmountain.dto.CartDTO;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String orderid, List<CartDTO> placeOrderList) throws SQLException {
        for(CartDTO placeOrder : placeOrderList) {
            if(!save(orderid, placeOrder)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String orderid, CartDTO placeOrder) throws SQLException {
        String sql = "INSERT INTO orderItemDetail(orderId,itemId,orderQty)" +
                "VALUES(?, ?, ?)";

        return CrudUtil.execute(
                sql,
                orderid,
                placeOrder.getItemId(),
                placeOrder.getOrderQty()

        );
    }
}
