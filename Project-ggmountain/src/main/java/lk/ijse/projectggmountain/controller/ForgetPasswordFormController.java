package lk.ijse.projectggmountain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.projectggmountain.dto.User;
import lk.ijse.projectggmountain.model.UserModel;
import lk.ijse.projectggmountain.util.OTPEmailController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgetPasswordFormController {

    @FXML
    private Button btnsendotp;

    @FXML
    private PasswordField confirmpasstxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private Group enteremailgroup;

    @FXML
    private Group newpasswordgroup;

    @FXML
    private PasswordField newpasswordtxt;

    @FXML
    private Button otpBtn;

    @FXML
    private TextField otpCode;

    @FXML
    private Group otptypegroup;

    @FXML
    private TextField usernametxt;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void clickOnAction(ActionEvent event) {

    }

    @FXML
    void clickOnActionOtpBtn(ActionEvent event) {
        if(otpCode.getText().equals(Integer. toString(randomnum))) {
            otptypegroup.setVisible(false);
            newpasswordgroup.setVisible(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Wrong OTP").show();
        }
    }

    public void sendclickOnAction(ActionEvent actionEvent) {
    }

    public void changepasswordOnAction(ActionEvent actionEvent) {
        if(newpasswordtxt.getText().equals(confirmpasstxt.getText())) {
            String username = usernametxt.getText();
            String newpassword = newpasswordtxt.getText();

            try {
                boolean isUpdated = UserModel.update(username, newpassword);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Password Changed").show();
                }
            } catch (SQLException e) {
                System.out.println(e);
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Passwords doesn't match").show();
        }
    }

    Random rand = new Random();
    int randomnum;
    public void clickOnActionSendOTP(ActionEvent actionEvent) throws SQLException, MessagingException {
        User user = UserModel.findbyusername(usernametxt.getText());

        System.out.println(usernametxt.getText());
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        if (emailtxt.getText().equals(user.getEmail())) {

            String email = emailtxt.getText();
            enteremailgroup.setVisible(false);
            otptypegroup.setVisible(true);

            randomnum = rand.nextInt(9000);
            randomnum += 1000;

            try {
                OTPEmailController.sendEmail(email, "Test Email", randomnum + "");
                System.out.println("Email sent successfully.");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address").show();
        }
    }

    public void onActionLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk.ijse.projectggmountain.view/login_form.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
