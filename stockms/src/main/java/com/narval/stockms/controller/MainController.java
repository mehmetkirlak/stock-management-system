package com.narval.stockms.controller;

import com.narval.stockms.dal.Dao;
import com.narval.stockms.dal.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.narval.stockms.model.IncomingPurchase;
import com.narval.stockms.model.OutgoingOrder;
import com.narval.stockms.model.Product;
import com.narval.stockms.model.Supplier;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //PRODUCT TEXTFIELDS
    @FXML
    TextField fld_addProduct_name;
    @FXML
    TextField fld_addProduct_quantity;
    @FXML
    TextField fld_addProduct_minRequired;
    //PURCHASE TEXTFIELDS
    @FXML
    TextField fld_newPurchase_productID;
    @FXML
    TextField fld_newPurchase_supplierID;
    @FXML
    TextField fld_newPurchase_numberPurchased;
    //ORDER TEXTFIELDS
    @FXML
    TextField fld_newOrder_productID;
    @FXML
    TextField fld_newOrder_numberOrdered;
    //SUPPLIER TEXTFIELDS
    @FXML
    TextField fld_addSupplier_name;
    @FXML
    TextField fld_addSupplier_address;
    @FXML
    TextField fld_addSupplier_contactNumber;
    // BUTTONS
    @FXML
    Button btn_addItem;
    @FXML
    Button btn_purchase;
    @FXML
    Button btn_addSupplier;
    @FXML
    Button btn_deleteItem;
    @FXML
    Button btn_deletePurchase;
    @FXML
    Button btn_deleteOrder;
    @FXML
    Button btn_deleteSupplier;
    @FXML
    Button btn_ordered;
    //DATA TABLES
    @FXML
    TableView<Product> tbl_productList;
    @FXML
    private TableColumn<Product, Integer> product_id;
    @FXML
    private TableColumn<Product, String> product_name;
    @FXML
    private TableColumn<Product, Integer> product_quantity;
    @FXML
    private TableColumn<Product, Integer> product_minRequired;
    @FXML
    private TableView<Supplier> tbl_supplierList;
    @FXML
    private TableColumn<Supplier, Integer> supplier_id;
    @FXML
    private TableColumn<Supplier, String> supplier_name;
    @FXML
    private TableColumn<Supplier, String> supplier_address;
    @FXML
    private TableColumn<Supplier, Long> supplier_contact_number;
    @FXML
    private TableView<OutgoingOrder> tbl_orderList;
    @FXML
    private TableColumn<OutgoingOrder, Integer> order_id;
    @FXML
    private TableColumn<OutgoingOrder, Integer> order_product_id;
    @FXML
    private TableColumn<OutgoingOrder, Integer> order_number_ordered;
    @FXML
    private TableColumn<OutgoingOrder, String> order_order_date;
    @FXML
    private TableView<IncomingPurchase> tbl_purchaseList;
    @FXML
    private TableColumn<IncomingPurchase, Integer> purchase_id;
    @FXML
    private TableColumn<IncomingPurchase, Integer> purchase_supplier_id;
    @FXML
    private TableColumn<IncomingPurchase, Integer> purchase_product_id;
    @FXML
    private TableColumn<IncomingPurchase, Integer> purchase_number_received;
    @FXML
    private TableColumn<IncomingPurchase, String> purchase_purchase_date;

    public MainController() {}

    //fetching data from database and return it as a list
    public  List<Product> getProductList() throws SQLException {

        ArrayList<Product> list = new ArrayList <>();

        try (Connection connection = Dao.connDb()) {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM [tbl_product]");

            while (rs.next()) {
                Product obj = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("min_required"));
                list.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Supplier> getSupplierList() throws SQLException {
        ArrayList<Supplier> list = new ArrayList<>();
        Connection connection = Dao.connDb();

        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM [tbl_supplier]");

            while (rs.next()) {
                Supplier obj = new Supplier(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getLong("contact_number"));
                list.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return list;
    }

    public List<OutgoingOrder> getOutgoingOrderList() throws SQLException {
        ArrayList<OutgoingOrder> list = new ArrayList<>();
        Connection connection = Dao.connDb();

        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM [tbl_order]");

            while (rs.next()) {
                OutgoingOrder obj = new OutgoingOrder(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
                list.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();

        }
        return list;
    }

    public List<IncomingPurchase> getIncomingPurchaseList() throws SQLException {
        ArrayList<IncomingPurchase> list = new ArrayList<>();
        Connection connection = Dao.connDb();

        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM [tbl_purchase]");

            while (rs.next()) {
                IncomingPurchase obj = new IncomingPurchase(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
                list.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.close();
        }
        return list;
    }

    @FXML
    void addItem(ActionEvent event) {
        boolean existsFlag = true;
        boolean flag = true;
        //Control of textfields
        if (fld_addProduct_name.getText().equals("") || fld_addProduct_quantity.getText().equals("") || fld_addProduct_minRequired.getText().equals("")) {
            flag = false;
        }
        if (!flag) {
            Helper.error("Please fill the fields correctly.");
        } else {
            try {
                for (Product product:getProductList()){
                    if (product.getName().equals(fld_addProduct_name.getText())) {
                        existsFlag = false;
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

            if (!existsFlag){
                Helper.error("This product already exists.");
            }else{
                try {
                    Connection connection = Dao.connDb();

                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO [tbl_product] (name,quantity,min_required) VALUES" + "(?,?,?)");
                    ps.setString(1, fld_addProduct_name.getText());
                    ps.setInt(2, Integer.parseInt(fld_addProduct_quantity.getText()));
                    ps.setInt(3, Integer.parseInt(fld_addProduct_minRequired.getText()));

                    ps.executeUpdate();


                    Helper.confirmation("Successfully registered.");

                    reloadProductData();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @FXML
    void addSupplier(ActionEvent event) {

        boolean existsFlag = true;
        boolean flag = true;

        if (fld_addSupplier_name.getText().equals("") || fld_addSupplier_address.getText().equals("") || fld_addSupplier_contactNumber.getText().equals("")) {
            flag = false;
        }
        if (!flag) {
            Helper.error("Please fill the fields correctly.");
        } else {
            try {
                for (Supplier supplier:getSupplierList()){
                    if (supplier.getName().equals(fld_addSupplier_name.getText())) {
                        existsFlag = false;
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            if (!existsFlag){
                Helper.error("This supplier already exists.");
            }else {
                try {
                    Connection connection = Dao.connDb();

                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO [tbl_supplier] (name,address,contact_number) VALUES" + "(?,?,?)");
                    ps.setString(1, fld_addSupplier_name.getText());
                    ps.setString(2, fld_addSupplier_address.getText());
                    ps.setLong(3, Long.parseLong(fld_addSupplier_contactNumber.getText()));

                    ps.executeUpdate();

                    Helper.confirmation("Successfully registered.");

                    reloadSupplierData();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    void newPurchase(ActionEvent event) {

        boolean flag = true;

        if (fld_newPurchase_productID.getText().equals("")|| fld_newPurchase_supplierID.getText().equals("") ||fld_newPurchase_numberPurchased.getText().equals("")) {
            flag = false;
        }

        if (!flag) {
            Helper.error("Please fill the fields correctly.");
        } else {
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement psIns = connection.prepareStatement("INSERT INTO [tbl_purchase] (supplier_id,product_id,number_received) VALUES" + "(?,?,?)");
                psIns.setInt(1, Integer.parseInt(fld_newPurchase_supplierID.getText()));
                psIns.setInt(2, Integer.parseInt(fld_newPurchase_productID.getText()));
                psIns.setInt(3, Integer.parseInt(fld_newPurchase_numberPurchased.getText()));

                psIns.executeUpdate();
                reloadPurchaseData();

                //this SQL query takes the products quantity by id and then update it with the sum of the number purchased and its quantity...
                PreparedStatement psquantityIns = connection.prepareStatement("DECLARE @quantity AS int SELECT @quantity = [quantity] FROM tbl_product WHERE id="+fld_newPurchase_productID.getText()+" UPDATE [tbl_product] SET quantity=@quantity+"+fld_newPurchase_numberPurchased.getText()+" WHERE id="+fld_newPurchase_productID.getText());
                psquantityIns.executeUpdate();
                reloadProductData();

                Helper.confirmation("Successfully registered.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void newOrder() {

        boolean flag = true;

        if (fld_newOrder_productID.getText().equals("") || fld_newOrder_numberOrdered.getText().equals("")) {
            flag = false;
        }

        if (!flag) {
            Helper.error("Please fill the fields correctly.");

        } else {
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement psIns = connection.prepareStatement("INSERT INTO [tbl_order] (product_id,number_ordered) VALUES" + "(?,?)");

                psIns.setInt(1, Integer.parseInt(fld_newOrder_productID.getText()));
                psIns.setInt(2, Integer.parseInt(fld_newOrder_numberOrdered.getText()));

                psIns.executeUpdate();
                reloadOrderData();
                //this SQL query takes the products quantity by id and then update it with the substraction of the quantity and number ordered...
                PreparedStatement psQuantityIns = connection.prepareStatement("DECLARE @quantity AS int SELECT @quantity = [quantity] FROM tbl_product WHERE id="+fld_newOrder_productID.getText()+" UPDATE [tbl_product] SET quantity=@quantity-"+fld_newOrder_numberOrdered.getText()+" WHERE id="+fld_newOrder_productID.getText());
                psQuantityIns.executeUpdate();
                reloadProductData();

                Helper.confirmation("Successfully registered.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void deleteItem() throws SQLException {

        if (tbl_productList.getSelectionModel().getSelectedItem()==null){
            Helper.error("Select product from list to delete.");
        }else{
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("DELETE FROM [tbl_product] WHERE id='"+tbl_productList.getSelectionModel().getSelectedItem().getId()+"'");
                ps.executeUpdate();

                Helper.confirmation("Product has successfully deleted");


            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadProductData();
        }
    }

    @FXML
    void deleteOrder() throws SQLException {

        if (tbl_orderList.getSelectionModel().getSelectedItem()==null){
            Helper.error("Select order from list to delete.");
        }else{
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("DELETE FROM [tbl_order] WHERE id='"+tbl_orderList.getSelectionModel().getSelectedItem().getId()+"'");
                ps.executeUpdate();

                Helper.confirmation("Order has successfully deleted");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadOrderData();
        }
    }

    @FXML
    void deletePurchase() throws SQLException {

        if (tbl_purchaseList.getSelectionModel().getSelectedItem()==null){
            Helper.error("Select purchase from list to delete.");
        }else{
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("DELETE FROM [tbl_purchase] WHERE id='"+tbl_purchaseList.getSelectionModel().getSelectedItem().getId()+"'");
                ps.executeUpdate();

                Helper.confirmation("Purchase has successfully deleted");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadPurchaseData();
        }
    }

    @FXML
    void deleteSupplier() throws SQLException {

        if (tbl_supplierList.getSelectionModel().getSelectedItem()==null){
            Helper.error("Select supplier from list to delete.");
        }else{
            Connection connection = Dao.connDb();
            try {
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("DELETE FROM [tbl_supplier] WHERE id='"+tbl_supplierList.getSelectionModel().getSelectedItem().getId()+"'");
                ps.executeUpdate();

                Helper.confirmation("Supplier has successfully deleted");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            reloadSupplierData();
        }
    }

    void reloadProductData() throws SQLException {
        ObservableList<Product> productlist =  FXCollections.observableArrayList(getProductList());
        tbl_productList.setItems(productlist);
    }
    void reloadOrderData() throws SQLException {
        ObservableList<OutgoingOrder> orderlist = FXCollections.observableArrayList(getOutgoingOrderList());
        tbl_orderList.setItems(orderlist);
    }
    void reloadPurchaseData() throws SQLException {
        ObservableList<IncomingPurchase> purchaselist = FXCollections.observableArrayList(getIncomingPurchaseList());
        tbl_purchaseList.setItems(purchaselist);
    }
    void reloadSupplierData() throws SQLException {
        ObservableList<Supplier> supplierlist =  FXCollections.observableArrayList(getSupplierList());
        tbl_supplierList.setItems(supplierlist);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Product> productlist = null;
        ObservableList<Supplier> supplierlist = null;
        ObservableList<OutgoingOrder> orderlist = null;
        ObservableList<IncomingPurchase> purchaselist = null;
        try {
            productlist = FXCollections.observableArrayList(getProductList());
            supplierlist = FXCollections.observableArrayList(getSupplierList());
            orderlist = FXCollections.observableArrayList(getOutgoingOrderList());
            purchaselist = FXCollections.observableArrayList(getIncomingPurchaseList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        product_minRequired.setCellValueFactory(new PropertyValueFactory<>("minRequired"));

        tbl_productList.setItems(productlist);

        supplier_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        supplier_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplier_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        supplier_contact_number.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        tbl_supplierList.setItems(supplierlist);

        order_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        order_product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        order_number_ordered.setCellValueFactory(new PropertyValueFactory<>("number_ordered"));
        order_order_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        tbl_orderList.setItems(orderlist);

        purchase_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchase_supplier_id.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        purchase_product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        purchase_number_received.setCellValueFactory(new PropertyValueFactory<>("number_received"));
        purchase_purchase_date.setCellValueFactory(new PropertyValueFactory<>("purchase_date"));

        tbl_purchaseList.setItems(purchaselist);


        btn_addItem.setOnAction(this::addItem);
        btn_addSupplier.setOnAction(this::addSupplier);
        btn_purchase.setOnAction(this::newPurchase);

        // MAKING SOME OF THE FIELDS JUST FOR NUMERIC ENTRY
        fld_addProduct_quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_addProduct_quantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        fld_addProduct_minRequired.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_addProduct_minRequired.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        fld_newOrder_numberOrdered.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_newOrder_numberOrdered.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        fld_newOrder_productID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_newOrder_productID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        fld_newPurchase_numberPurchased.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_newPurchase_numberPurchased.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        fld_newPurchase_productID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_newPurchase_productID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        fld_newPurchase_supplierID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_newPurchase_supplierID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        fld_addSupplier_contactNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fld_addSupplier_contactNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

}
