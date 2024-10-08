package lk.ijse.projectggmountain.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.projectggmountain.dto.User;
import lk.ijse.projectggmountain.model.UserModel;
import lk.ijse.projectggmountain.util.ValidateField;

public class SignUpFormController {
    @FXML
    private TextField conpasswordtxt;

    @FXML
    private Button createAnAccBtn;

    @FXML
    private TextField emailtxt;

    @FXML
    private TextField jobtxt;

    @FXML
    private Hyperlink loginLbl;

    @FXML
    private TextField passwordtxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private Label passlabel;


    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void clickOnActionCreateAccount(ActionEvent event) {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        String job_title = jobtxt.getText();
        String email = emailtxt.getText();


        User user = new User(username,password,job_title,email);

        try {
            if(passwordtxt.getText().equals(conpasswordtxt.getText())) {
                boolean isSaved = UserModel.save(user);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Account Created").show();
                }
            }else{
               new Alert(Alert.AlertType.ERROR,"Passwords doesn't match").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"Something Wemt Wrong").show();
        }
    }

    @FXML
    void clickOnActionLoginBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk.ijse.projectggmountain.view/login_form.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(new Image("lk.ijse.projectggmountain.assets/Logo (1).png"));
        stage.show();
    }

    public void signTxt2OnKeyTyped(KeyEvent keyEvent) {
        String password = passwordtxt.getText();

        // Check if the password is valid
        List<String> messages = ValidateField.getValidationMessages(password);
        if (messages.isEmpty()) {
            passlabel.setStyle("-fx-text-fill: #27cb27; -fx-background-color: black; -fx-background-radius: 10; -fx-font-family: Offside; -fx-padding: 10");
            passlabel.setText("strong valid password");
            createAnAccBtn.setDisable(false);
        } else {
            String message = String.join(", ", messages);
            passlabel.setStyle("-fx-text-fill: red; -fx-background-color: black; -fx-background-radius: 10; -fx-font-family: Offside; -fx-padding: 10");
            passlabel.setText(message);
            createAnAccBtn.setDisable(true);
        }
        passlabel.setVisible(true);
    }


    @FXML
    void initialize() {
        usernametxt.getText();
        passwordtxt.getText();
        emailtxt.getText();
        jobtxt.getText();
    }

}
