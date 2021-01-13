/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.kasir.app.view;

import id.kasir.app.core.ConfigDb;
import id.kasir.app.core.MySQL;
import id.kasir.app.models.Productpost;
import id.kasir.app.core.ProductService;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ProductView {
//mendeklarasikan objek frame atau tampilan, button, label, textfield
    static JFrame frame = new JFrame();
    static JLabel lbJudul = new JLabel("Produk");
    static JLabel lbCode = new JLabel("Code");
    static JLabel lbName = new JLabel("Name");
    static JLabel lbPrice = new JLabel("Price");
    static JLabel lbStock = new JLabel("Stock");
    static JTable tabelbarang = new JTable();
    static JButton btnUpdate = new JButton("Update");
    static JButton btnDelete = new JButton("Delete");
    static JButton btnTambah = new JButton("Tambah");
    static JTextField txtCode = new JTextField();
    static JTextField txtName = new JTextField();
    static JTextField txtPrice = new JTextField();
    static JTextField txtStock = new JTextField();

    //membuat prosedur layout pada frame
    static void Frame() {
        //memberikan judul pada tampilan frame
        frame.setTitle("Produk");
        //menentukan ukuran menu tampilan
        frame.setSize(700, 700);
        //untuk membuat tampilan frame berada di tengah layar
        frame.setLocationRelativeTo(null);
        //untuk menampilkan tombol close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //untuk menampilkan tampilan frame agar muncul ke layar saat di jalankan 
        frame.setVisible(true);
        //untuk mengatur tampilan pada frame
        frame.setLayout(null);

        // menambahkan objek label judul,code,name,price,stock,tabel barang, update, delete, tambah pada frame tampilan
        frame.add(lbJudul);
        frame.add(lbCode);
        frame.add(lbName);
        frame.add(lbPrice);
        frame.add(lbStock);
        frame.add(tabelbarang);
        frame.add(btnUpdate);
        frame.add(btnDelete);
        frame.add(btnTambah);

         // menambahkan objek text code,name,price,stock pada frame tampilan
        frame.add(txtCode);
        frame.add(txtName);
        frame.add(txtPrice);
        frame.add(txtStock);

        // mengatur letak dan ukuran dari label judul,code, name,price, stock, update, delete, tambah
        lbJudul.setBounds(330, 25, 100, 40);
        lbJudul.setFont(new java.awt.Font("Tohama", 1, 20));
        lbCode.setBounds(60, 80, 160, 30);
        lbName.setBounds(60, 120, 160, 30);
        lbPrice.setBounds(60, 160, 160, 30);
        lbStock.setBounds(60, 200, 160, 30);
        tabelbarang.setBounds(120, 280, 450, 230);
        btnUpdate.setBounds(100, 550, 100, 20);
        btnDelete.setBounds(300, 550, 100, 20);
        btnTambah.setBounds(500, 550, 100, 20);

        // mengatur letak dan ukuran dari text code, name, price, stock
        txtCode.setBounds(240, 80, 160, 30);
        txtName.setBounds(240, 120, 160, 30);
        txtPrice.setBounds(240, 160, 160, 30);
        txtStock.setBounds(240, 200, 160, 30);

    }

    static void Table() {

        DefaultTableModel model = new DefaultTableModel();
        tabelbarang.setModel(model);
        Object[] columns = new Object[4];
        columns[0] = "Code";
        columns[1] = "Name";
        columns[2] = "Price";
        columns[3] = "Stock";
        model.setColumnIdentifiers(columns);
       
        ProductService service = new ProductService();
        List<Productpost> products = service.selectBarang();

        for (Productpost product : products) { 
            Object[] obj = new Object[4];
            obj[0] = product.getCode();
            obj[1] = product.getName();
            obj[2] = product.getPrice();
            obj[3] = product.getStock();
            model.addRow(obj);
            
        }
        tabelbarang.setModel(model);        
    }
    //untuk button update
    static void Update() {
        //melakukan eksekusi
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //menginput baris pada tabel
                int selectedRow = tabelbarang.getSelectedRow();
                String Code = (String) tabelbarang.getValueAt(selectedRow, 0);
                String Name = (String) tabelbarang.getValueAt(selectedRow, 1);
                String Price = tabelbarang.getValueAt(selectedRow, 2).toString();
                String Stock = tabelbarang.getValueAt(selectedRow, 3).toString();

                Productpost productpost = new Productpost();
                productpost.setCode(Code);
                productpost.setName(Name);
                productpost.setPrice(Integer.parseInt(Price));
                productpost.setStock(Integer.parseInt(Stock));
                
                ProductService service = new ProductService();
                boolean update = service.updateBarang(productpost);
                if (update) {
                    JOptionPane.showMessageDialog(null, "Berhasil diupdate");
                    Table();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak dapat diupdate");
                }

            }
        });
    }
    //untuk button delete
    static void Delete() {
        //melakukan eksekusi
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //menginput baris pada tabel
                int selectedRow = tabelbarang.getSelectedRow();
                String Code = (String) tabelbarang.getValueAt(selectedRow, 0);
                //memanggil fungsi set pada product post
                ProductService service = new ProductService();
                //memanggil delete barang pada product service
                Productpost productpost = new Productpost();
                productpost.setCode(Code);
                boolean delete = service.deleteBarang(productpost);

                if (delete) {
                    JOptionPane.showMessageDialog(null, "Berhasil dihapus");
                    Table();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak dapat dihapus");
                }

            }

        });
    }

    //untuk button tambah
    static void Tambah() {
        //melakukan eksekusi
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String Code = txtCode.getText().toString();

                //memanggil fungsi set pada product post
                Productpost Productpost = new Productpost();
                Productpost.setCode(txtCode.getText());
                Productpost.setName(txtName.getText());
                Productpost.setPrice(Integer.parseInt(txtPrice.getText()));
                Productpost.setStock(Integer.parseInt(txtStock.getText()));
                //memanggil insert barang pada product service
                ProductService service = new ProductService();
                boolean tambah = service.insertBarang(Productpost);

                if (tambah) {
                    JOptionPane.showMessageDialog(null, "Berhasil Ditambahkan ");
                    Table();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal ");
                }
            }
        });
    }
     public static void main(String[] args) {
        //mengeksekusi frame
        Frame();
        //mengeksekusi tabel
        Table();
        //mengeksekusi button update
        Update();
        //mengeksekusi button delete
        Delete();
        //mengeksekusi button tambah
        Tambah();
    }

}
