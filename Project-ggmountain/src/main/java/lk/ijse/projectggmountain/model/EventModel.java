package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.projectggmountain.dto.Event;
import lk.ijse.projectggmountain.dto.tm.EventTM;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventModel {
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM event WHERE eventId=?";
        return CrudUtil.execute(sql,id);
    }

    public static boolean save(lk.ijse.projectggmountain.dto.Event event) throws SQLException {
        String sql = "INSERT INTO event(eventId,eventName,date,time,eventType,empId) " +
                "VALUES(?,?,?,?,?,?)";

        return CrudUtil.execute(sql,event.getId(),event.getName(),event.getDate(),event.getTime(),event.getType(),event.getEmpId());
    }

    public static boolean update(lk.ijse.projectggmountain.dto.Event event) throws SQLException {
        String sql = "UPDATE event SET eventName=?,date=?,time=?,eventType=?,empId=? WHERE eventId=?";

        return CrudUtil.execute(sql,event.getName(),event.getDate(),event.getTime(),event.getType(),event.getEmpId(),event.getId());

    }

    public static ObservableList<EventTM> getAll() throws SQLException {
        String sql = "SELECT * FROM event";

        ObservableList<EventTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            obList.add(new EventTM(
                    resultSet.getString(6),
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)


            ));
        }
        return obList;
    }

    public static lk.ijse.projectggmountain.dto.Event findById(String id) throws SQLException {
        String sql = "SELECT * FROM event WHERE eventId=?";

        ResultSet resultSet = CrudUtil.execute(sql,id);
        if(resultSet.next()){
            return (new Event(
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
