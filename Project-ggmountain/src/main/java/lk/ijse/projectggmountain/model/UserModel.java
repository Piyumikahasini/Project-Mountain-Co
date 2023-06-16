package lk.ijse.projectggmountain.model;

import lk.ijse.projectggmountain.dto.User;
import lk.ijse.projectggmountain.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean save(User user) throws SQLException {
        String sql = "INSERT INTO user(userName,password,jobTitle,email) " +
                "VALUES(?,?,?,?)";

        return CrudUtil.execute(sql,user.getUsername(),user.getPassword(),user.getJobtitle(),user.getEmail());
    }

    public static User findbyusername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE userName=?";

        ResultSet resultSet = CrudUtil.execute(sql,username);
        if(resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static boolean update(String username, String newpassword) throws SQLException {
        String sql = "UPDATE user SET password =? WHERE userName = ?";

        return CrudUtil.execute(sql,newpassword,username);
    }
}
