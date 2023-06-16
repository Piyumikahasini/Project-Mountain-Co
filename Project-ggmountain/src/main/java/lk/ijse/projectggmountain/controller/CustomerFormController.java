package lk.ijse.projectggmountain.controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.projectggmountain.dto.Customer;
import lk.ijse.projectggmountain.dto.tm.CustomerTM;
import lk.ijse.projectggmountain.model.CustomerModel;
import lk.ijse.projectggmountain.util.AlertController;
import lk.ijse.projectggmountain.util.ValidateField;

public class CustomerFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane adminChangingPane;

    @FXML
    private TextField custAddress;

    @FXML
    private TableColumn<?, ?> custColAddress;

    @FXML
    private TableColumn<?, ?> custColContact;

    @FXML
    private TableColumn<?, ?> custColEmail;

    @FXML
    private TableColumn<?, ?> custColId;

    @FXML
    private TableColumn<?, ?> custColName;

    @FXML
    private TextField custContact;

    @FXML
    private TextField custEmail;

    @FXML
    private TextField custId;

    @FXML
    private TextField custName;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ImageView searchIcon;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button updateBtn;

    @FXML
    private Label lblinvalidcontact;

    @FXML
    private Label lblinvalidcustomerid;

    @FXML
    private Label lblinvalidemail;

    @FXML
    void clickOnActionDelete(ActionEvent event) {
        String id = custId.getText();

        boolean result = AlertController.okconfirmmessage("Are you sure you want to remove this Customer?");
        if(result==true) {
            try {
                boolean isDeleted = CustomerModel.delete(id);
                if (isDeleted) {
                    AlertController.confirmmessage("Customer Deleted Successfully");
                    custId.setText("");
                    custName.setText("");
                    custAddress.setText("");
                    custEmail.setText("");
                    custContact.setText("");

                    getAll();
                }
            } catch (SQLException e) {
                System.out.println(e);
                AlertController.errormessage("something went wrong!");
            }
        }
    }

    @FXML
    void clickOnActionSave(ActionEvent event) {
        String id = custId.getText();
        String name = custName.getText();
        String address = custAddress.getText();
        String email = custEmail.getText();
        String contact = custContact.getText();

        if(id.isEmpty() || name.isEmpty() || contact.isEmpty() || address.isEmpty()){
            AlertController.errormessage("Customer not saved successfully.\nPlease make sure to fill out all the required fields.");
        }else{
            if(ValidateField.customerIdCheck(id)) {
                if (ValidateField.emailCheck(email) || ValidateField.contactCheck(contact)) {
                    if (ValidateField.emailCheck(email)) {
                        if (ValidateField.contactCheck(contact)) {
                            if (ValidateField.emailCheck(email) || ValidateField.contactCheck(contact)) {
                                if (ValidateField.contactCheck(contact)) {
                                    if (ValidateField.emailCheck(email)) {


                                        Customer customer = new Customer(id,name,address,email,contact);

                                        try {
                                            boolean isSaved = CustomerModel.save(customer);
                                            if (isSaved) {
                                                AlertController.confirmmessage("New customer added successfully");
                                                custId.setText(null);
                                                custName.setText(null);
                                                custAddress.setText(null);
                                                custEmail.setText(null);
                                                custContact.setText(null);
                                                getAll();
                                            }
                                        }catch(SQLIntegrityConstraintViolationException e){
                                            AlertController.errormessage("This Customer ID already exists.");
                                        } catch (SQLException e) {
                                            System.out.println(e);
                                            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                                        }

                                    } else {
                                        lblinvalidemail.setVisible(true);
                                    }
                                } else {
                                    lblinvalidcontact.setVisible(true);
                                }
                            } else {
                                lblinvalidemail.setVisible(true);
                                lblinvalidcontact.setVisible(true);
                            }
                        } else {
                            lblinvalidcontact.setVisible(true);
                        }
                    } else {
                        lblinvalidemail.setVisible(true);
                    }
                } else {
                    lblinvalidemail.setVisible(true);
                    lblinvalidcontact.setVisible(true);
                }
            }else{
                lblinvalidcustomerid.setVisible(true);
                lblinvalidcustomerid.setStyle("-fx-text-fill: red");
            }
        }
    }

    @FXML
    void clickOnActionUpdate(ActionEvent event) {
        String id = custId.getText();
        String name = custName.getText();
        String address = custAddress.getText();
        String email = custEmail.getText();
        String contact = custContact.getText();

        boolean result = AlertController.okconfirmmessage("Are you sure you want to update this Customer' details?");
        if(result==true) {

            if(id.isEmpty() || name.isEmpty() || contact.isEmpty() || address.isEmpty()){
                AlertController.errormessage("Customer not saved successfully.\nPlease make sure to fill out all the required fields.");
            }else {
                if (ValidateField.customerIdCheck(id)) {
                    if (ValidateField.emailCheck(email) || ValidateField.contactCheck(contact)) {
                        if (ValidateField.emailCheck(email)) {
                            if (ValidateField.contactCheck(contact)) {
                                if (ValidateField.emailCheck(email) || ValidateField.contactCheck(contact)) {
                                    if (ValidateField.contactCheck(contact)) {
                                        if (ValidateField.emailCheck(email)) {

                                            Customer customer = new Customer(id, name, address, email, contact);
                                            try {
                                                boolean isUpdated = CustomerModel.update(customer);
                                                if (isUpdated) {
                                                    AlertController.confirmmessage("Customer Details Updated");
                                                    custId.setText("");
                                                    custName.setText("");
                                                    custAddress.setText("");
                                                    custEmail.setText("");
                                                    custContact.setText("");

                                                    getAll();
                                                }
                                            } catch (SQLException e) {
                                                System.out.println(e);
                                                AlertController.errormessage("something went wrong!");
                                            }
                                        } else {
                                            lblinvalidemail.setVisible(true);
                                        }
                                    } else {
                                        lblinvalidcontact.setVisible(true);
                                    }
                                } else {
                                    lblinvalidemail.setVisible(true);
                                    lblinvalidcontact.setVisible(true);
                                }
                            } else {
                                lblinvalidcontact.setVisible(true);
                            }
                        } else {
                            lblinvalidemail.setVisible(true);
                        }
                    } else {
                        lblinvalidemail.setVisible(true);
                        lblinvalidcontact.setVisible(true);
                    }
                } else {
                    lblinvalidcustomerid.setVisible(true);
                    lblinvalidcustomerid.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    private void getAll(){
        ObservableList<CustomerTM> obList = null;
        try {
            obList = CustomerModel.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblCustomer.setItems(obList);
    }

    @FXML
    void searchIconOnMousePressed(MouseEvent event) {
        txtSearch.requestFocus();
        txtSearch.selectAll();
        txtSearch.fireEvent(new ActionEvent());
    }

    @FXML
    void txtSearchKeyTyped(KeyEvent event) throws SQLException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<CustomerTM> obList = CustomerModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<CustomerTM> filteredData = obList.filtered(new Predicate<CustomerTM>(){
                @Override
                public boolean test(CustomerTM customertm) {
                    return String.valueOf(customertm.getId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblCustomer.setItems(filteredData);
        } else {
            tblCustomer.setItems(obList);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtSearch.getText();

        custId.setText("");
        custName.setText("");
        custAddress.setText("");
        custEmail.setText("");
        custContact.setText("");


        Customer customer = CustomerModel.findById(id);
        if(customer!=null) {
            custId.setText(customer.getId());
            custName.setText(customer.getName());
            custAddress.setText(customer.getAddress());
            custEmail.setText(customer.getEmail());
            custContact.setText(customer.getContact());

            txtSearch.setText("");

        }else{
            AlertController.errormessage("Supplier ID Not Found");
        }
    }

    private void setCellValueFactory() {
        custColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        custColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        custColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        custColContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    void initialize() {
        custId.setText(null);
        custName.setText(null);
        custAddress.setText(null);
        custEmail.setText(null);
        custContact.setText(null);
        setCellValueFactory();
        getAll();
        lblinvalidemail.setVisible(false);
        lblinvalidcontact.setVisible(false);
        lblinvalidcustomerid.setVisible(false);
        assert adminChangingPane != null : "fx:id=\"adminChangingPane\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custAddress != null : "fx:id=\"custAddress\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custColAddress != null : "fx:id=\"custColAddress\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custColContact != null : "fx:id=\"custColContact\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custColEmail != null : "fx:id=\"custColEmail\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custColId != null : "fx:id=\"custColId\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custColName != null : "fx:id=\"custColName\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custContact != null : "fx:id=\"custContact\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custEmail != null : "fx:id=\"custEmail\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custId != null : "fx:id=\"custId\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert custName != null : "fx:id=\"custName\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert searchIcon != null : "fx:id=\"searchIcon\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert tblCustomer != null : "fx:id=\"tblCustomer\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'customer_form.fxml'.";
        assert updateBtn != null : "fx:id=\"updateBtn\" was not injected: check your FXML file 'customer_form.fxml'.";

    }
    public void custIdOnMousePressed(MouseEvent mouseEvent) {
        lblinvalidcustomerid.setVisible(false);
    }

    public void custContactOnMousePressed(KeyEvent keyEvent) {
        lblinvalidcontact.setVisible(false);
    }

    public void custEmailOnMousePressed(KeyEvent keyEvent) {
        lblinvalidemail.setVisible(false);
    }

}
