package lk.ijse.projectggmountain.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.projectggmountain.dto.NewDelivery;
import lk.ijse.projectggmountain.model.EmployeeModel;
import lk.ijse.projectggmountain.model.OrderModel;

public class AddNewDeliveryFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField TxtDeliveryId;

    @FXML
    private Button btnAddNewDelivery;

    @FXML
    private ComboBox<String> comEmpId;

    @FXML
    private Label lblOrderId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtLocation;

    @FXML
    private AnchorPane newDeliveryAncPane;

    NewDelivery newDelivery;

    @FXML
    void addNewDeliveryOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String delId = TxtDeliveryId.getText();
        String location = txtLocation.getText();
        String empId = comEmpId.getValue();
        String dueDate = txtDate.getText();

        newDelivery = new NewDelivery(orderId,delId,location,empId,dueDate);

        OrderModel.sendObject(newDelivery);

        newDeliveryAncPane.getScene().getWindow().hide();
    }

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            lblOrderId.setText(id);
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Exception!").show();
        }
    }

    private void loadEmployeeIds() throws SQLException {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = EmployeeModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            comEmpId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void initialize() throws SQLException {
        generateNextOrderId();
        loadEmployeeIds();
        assert TxtDeliveryId != null : "fx:id=\"TxtDeliveryId\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";
        assert btnAddNewDelivery != null : "fx:id=\"btnAddNewDelivery\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";
        assert comEmpId != null : "fx:id=\"comEmpId\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";
        assert lblOrderId != null : "fx:id=\"lblOrderId\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";
        assert txtDate != null : "fx:id=\"txtDate\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";
        assert txtLocation != null : "fx:id=\"txtLocation\" was not injected: check your FXML file 'add_new_delivery_form.fxml'.";

    }

}
