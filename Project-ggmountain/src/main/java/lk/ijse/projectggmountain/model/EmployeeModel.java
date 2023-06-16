package lk.ijse.projectggmountain.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.projectggmountain.db.DBConnection;
import lk.ijse.projectggmountain.dto.Employee;
import lk.ijse.projectggmountain.dto.tm.EmployeeTM;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE empId=?";
        return CrudUtil.execute(sql,id);
    }

    public static boolean save(Employee employee) throws SQLException {
            String sql = "INSERT INTO employee(empId,empName,nic,dob,address,email,jobTitle,contact,salary) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";

            return CrudUtil.execute(sql,employee.getId(),employee.getName(),employee.getNic(),employee.getDob(),employee.getAddress(),employee.getEmail(),employee.getJob(),employee.getContact(),employee.getSalary());
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET empName=?,nic=?,dob=?,address=?,email=?,jobTitle=?,contact=?,salary=? WHERE empId=?";

        return CrudUtil.execute(sql,employee.getName(),employee.getNic(),employee.getDob(),employee.getAddress(),employee.getEmail(),employee.getJob(),employee.getContact(),employee.getSalary(),employee.getId());

    }

    public static ObservableList<EmployeeTM> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            obList.add(new EmployeeTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9)
            ));
        }
        return obList;
    }

    public static Employee findById(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE empId=?";

        ResultSet resultSet = CrudUtil.execute(sql,id);
        if(resultSet.next()){
            return (new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9)

            ));
        }
        return null;
    }

    public static List<String> loadEmpIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT empId FROM employee");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT empId FROM employee");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}

