/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.BOFactory;
import business.BOType;
import business.custom.CustomerBO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ranjith-suranga
 */
public class ManageCustomerFormController implements Initializable {
    private CustomerBO customerbo = BOFactory.getInstance().getBO(BOType.CUSTOMER);
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        txtCustomerId.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);

        loadAllCustomers();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    txtCustomerId.clear();
                    txtCustomerName.clear();
                    txtCustomerAddress.clear();
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCustomerId.setText(selectedItem.getId());
                txtCustomerName.setText(selectedItem.getName());
                txtCustomerAddress.setText(selectedItem.getAddress());
            }
        });
    }

    private void loadAllCustomers() {
        try {
            tblCustomers.getItems().clear();
            List<CustomerTM> allCustomers = customerbo.getAllCustomers();
            ObservableList<CustomerTM> customers = FXCollections.observableList(allCustomers);
            tblCustomers.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @FXML
    private void btnSave_OnAction(ActionEvent event) {
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();

        // Validation
        if (name.trim().length() == 0 || address.trim().length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Customer Name, Address can't be empty", ButtonType.OK).show();
            return;
        }

        if (btnSave.getText().equals("Save")) {
            try {
                customerbo.saveCustomer(txtCustomerId.getText(),txtCustomerName.getText(),txtCustomerAddress.getText());
                btnAddNew_OnAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
            try {
                customerbo.updateCustomer(txtCustomerName.getText(), txtCustomerAddress.getText(), selectedItem.getId());
                tblCustomers.refresh();
                btnAddNew_OnAction(event);
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to update the customer",ButtonType.OK).show();
            }
        }
        loadAllCustomers();
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
            try {
                customerbo.deleteCustomer(selectedItem.getId());
                tblCustomers.getItems().remove(selectedItem);
                tblCustomers.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to Delete the customer",ButtonType.OK).show();
            }
        }
    }

    @FXML
    private void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        tblCustomers.getSelectionModel().clearSelection();
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCustomerName.requestFocus();
        btnSave.setDisable(false);

        // Generate a new id
        try {
            txtCustomerId.setText(customerbo.getNewCustomerId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}