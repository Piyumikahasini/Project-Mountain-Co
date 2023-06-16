package lk.ijse.projectggmountain.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.projectggmountain.dto.User;
import lk.ijse.projectggmountain.model.UserModel;
import lk.ijse.projectggmountain.util.AlertController;

public class LoginFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    private Button loginBtn;

    @FXML
    private ComboBox loginComboBox;

    @FXML
    private Rectangle loginRec;

    @FXML
    private TextField loginTxt1;

    @FXML
    private PasswordField loginTxti2;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void clickOnActionForgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk.ijse.projectggmountain.view/forget_password_form.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(new Image("lk.ijse.projectggmountain.assets/Logo (1).png"));
        stage.show();
    }

    @FXML
    void clickOnActionLogin(ActionEvent actionEvent) throws IOException{
        String username = loginTxt1.getText();
        String password = loginTxti2.getText();
        String combo = String.valueOf(loginComboBox.getValue());

        try {
            User user = UserModel.findbyusername(username);

            if (user == null) {
                AlertController.errormessage("Username Not Found");
            } else {
                if(user.getPassword().equals(password) && user.getJobtitle().equalsIgnoreCase(combo) && combo.equals("Admin")) {
                    LoginMessageController.loginsuccessfulmsg();

                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        loginBtn.getScene().getWindow().hide();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk.ijse.projectggmountain.view/adminDashBoard_form.fxml"));
                        Parent root1 = null;
                        try {
                            root1 = fxmlLoader.load();
                        } catch (IOException e) {
                            System.out.println(e);
                            e.printStackTrace();
                        }
                        Stage stage = new Stage();
                        stage.setTitle("DashBoard");
                        stage.setScene(new Scene(root1));
                        stage.setResizable(false);
                        stage.getIcons().add(new Image("lk.ijse.projectggmountain.assets/Logo (1).png"));
                        stage.show();
                    }));
                    timeline.play();
                }else if(user.getPassword().equals(password) && user.getJobtitle().equalsIgnoreCase(combo) && combo.equals("Cashier")) {
                    LoginMessageController.loginsuccessfulmsg();

                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                        loginBtn.getScene().getWindow().hide();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk.ijse.projectggmountain.view/cashier_dashbord_form.fxml"));
                        Parent root1 = null;
                        try {
                            root1 = fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage stage = new Stage();
                        stage.setTitle("DashBoard");
                        stage.setScene(new Scene(root1));
                        stage.setResizable(false);
                        stage.getIcons().add(new Image("lk.ijse.projectggmountain.assets/Logo (1).png"));
                        stage.show();
                    }));
                    timeline.play();
                }else{
                    LoginMessageController.loginunsuccessfulmsg();
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    @FXML
    void clickOnActionSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk.ijse.projectggmountain.view/signUp_form.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(new Image("lk.ijse.projectggmountain.assets/Logo (1).png"));
        stage.show();
    }


    @FXML
    void initialize() {
        assert forgotPassword != null : "fx:id=\"forgotPassword\" was not injected: check your FXML file 'login_form.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'login_form.fxml'.";
        assert loginComboBox != null : "fx:id=\"loginComboBox\" was not injected: check your FXML file 'login_form.fxml'.";
        assert loginRec != null : "fx:id=\"loginRec\" was not injected: check your FXML file 'login_form.fxml'.";
        assert loginTxt1 != null : "fx:id=\"loginTxt1\" was not injected: check your FXML file 'login_form.fxml'.";
        assert loginTxti2 != null : "fx:id=\"loginTxti2\" was not injected: check your FXML file 'login_form.fxml'.";

        loginComboBox.getItems().addAll("Admin" , "Cashier");
    }
}
