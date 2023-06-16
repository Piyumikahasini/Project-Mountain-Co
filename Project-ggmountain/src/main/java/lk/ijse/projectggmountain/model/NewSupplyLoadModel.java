package lk.ijse.projectggmountain.model;

import lk.ijse.projectggmountain.db.DBConnection;
import lk.ijse.projectggmountain.dto.NewLoadSupplier;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class NewSupplyLoadModel {
    public static boolean placeOrder(String loadId, String supId, String totPrice, List<NewLoadSupplier> newLoadList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = save(loadId,supId,totPrice, LocalDate.now(), LocalTime.now(),newLoadList);
            if(isSaved) {
                boolean isUpdated = InventoryModel.addQty(newLoadList);
                if(isUpdated) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            System.out.println(er);
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

    private static boolean save(String loadId, String supId, String totPrice, LocalDate now, LocalTime now1, List<NewLoadSupplier> newLoadList) throws SQLException {
        for(NewLoadSupplier newLoadSupplier : newLoadList) {
            if(!saveSupplyLoadDetails(loadId,supId,totPrice,now,now1,newLoadSupplier)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveSupplyLoadDetails(String loadId, String supId, String totPrice, LocalDate now, LocalTime now1, NewLoadSupplier newLoadSupplier) throws SQLException {
        String sql = "INSERT INTO supplierorderdetail(supplierOrderId,itemId,supId,qty,loadDate,loadTime,price)" +
                "VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                loadId,
                newLoadSupplier.getItemId(),
                supId,
                newLoadSupplier.getSupQty(),
                now,
                now1,
                totPrice
        );
    }
}
