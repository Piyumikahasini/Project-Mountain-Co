package lk.ijse.projectggmountain.model;

import lk.ijse.projectggmountain.dto.NewDelivery;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.SQLException;

    public class NewDeliveryModel {
        public static boolean save(NewDelivery gotnewdelivery) throws SQLException {
            String sql = "INSERT INTO delivery(deliveryId,Date,location,orderId,empId)" +
                    "VALUES (?,?,?,?,?)";

            return CrudUtil.execute(
                    sql,
                    gotnewdelivery.getDelId(),
                    gotnewdelivery.getDueDate(),
                    gotnewdelivery.getLocation(),
                    gotnewdelivery.getOrderId(),
                    gotnewdelivery.getEmpId()
            );
        }
    }

