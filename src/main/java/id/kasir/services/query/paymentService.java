/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.payment.app.services;



/**
 *
 * @author baros
 */
import id.payment.app.core.configDatabase;
import id.payment.app.models.productModel;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class paymentService extends configDatabase {

    private boolean status;
    private String errorMessage;
    
    public List<productModel> getAll() {
        paymentService.createConnection();
        List<productModel> products = new ArrayList<>();

        String sql = "SELECT * from barang";

        try {
            Statement stmt = paymentService.con.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                productModel product = new productModel();
                product.setCode(result.getString("idBarang"));
                product.setName(result.getString("name"));
                product.setPrice(result.getInt("price"));
                product.setStock(result.getInt("Stock"));


                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.status = false;
            this.errorMessage = e.getMessage();
        }

        return products;
    }
    
    public productModel getByName(String name) {
        paymentService.createConnection();
        productModel product = null;

        String sql = "SELECT * from barang WHERE name = '%s'";
        sql = String.format(sql, name);

        try {
            Statement stmt = paymentService.con.createStatement();
            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                product = new productModel();
                product.setCode(result.getString("idBarang"));
                product.setName(result.getString("name"));
                product.setPrice(result.getInt("price"));
                product.setStock(result.getInt("Stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.status = false;
            this.errorMessage = e.getMessage();
        }

        return product;
    }
}
