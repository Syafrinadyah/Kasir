package id.payment.app.view;

import id.payment.app.services.paymentService;
import id.payment.app.models.productModel;

import id.payment.app.models.Payment;
import java.sql.*;
import java.util.List;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class paymentView extends JFrame {

    static JScrollPane sp, checkoutSp;
    static JSeparator sep;
    static JLabel codeLabel, amountLabel, nameLabel, billLabel, payLabel;
    static JTextField codeText, amountText, nameText, billText, payText;
    static JButton confButton, payButton;
    static JTable jt, checkout;

    static List<Payment> payment = new ArrayList<>();

    private static void PaymentInterface(JPanel panel) {

        panel.setLayout(null);

        //SUMMON DATABASE
        paymentService paymentService = new paymentService();
        List<productModel> products = paymentService.getAll();

        //TABLE
        jt = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = new Object[4];
        columns[0] = "Id Barang";
        columns[1] = "Nama Barang";
        columns[2] = "Harga";
        columns[3] = "Stock";

        model.setColumnIdentifiers(columns);

        for (productModel product : products) {
            Object[] data = new Object[4];
            data[0] = product.getCode();
            data[1] = product.getName();
            data[2] = product.getPrice();
            data[3] = product.getStock();

            model.addRow(data);
        }

        jt.setModel(model);
        sp = new JScrollPane(jt);
        panel.add(sp, BorderLayout.CENTER);
        sp.setBounds(28, 60, 735, 240);
        
        //====
        
        checkout = new JTable();
        checkoutSp = new JScrollPane(checkout);
        panel.add(checkoutSp);
        checkoutSp.setBounds(545, 330, 220, 200);

        //=====================================================
        sep = new JSeparator();
        sep.setBounds(28, 310, 735, 1);
        panel.add(sep);
        //=====================================================

        // COMPONENT
        // LABEL DAN TEXTFIELD KODE BARANG
        codeLabel = new JLabel("Nama Barang");
        codeLabel.setFont(new Font("Constantina", Font.PLAIN, 17));
        codeLabel.setBounds(50, 330, 100, 30);
        panel.add(codeLabel);

        codeText = new JTextField(20);
        codeText.setBounds(155, 330, 175, 30);
        panel.add(codeText);

        // LABEL DAN TEXTFIELD JUMLAH BARANG
        amountLabel = new JLabel("Jumlah");
        amountLabel.setFont(new Font("Constantina", Font.PLAIN, 17));
        amountLabel.setBounds(50, 380, 100, 30);
        panel.add(amountLabel);

        amountText = new JTextField(20);
        amountText.setBounds(155, 380, 175, 30);
        panel.add(amountText);

        // LABEL DAN TEXTFIELD NAMA BARANG
        nameLabel = new JLabel("Barang");
        nameLabel.setFont(new Font("Constantina", Font.PLAIN, 17));
        nameLabel.setBounds(425, 330, 100, 30);
        panel.add(nameLabel);

//        nameText = new JTextField(20);
//        nameText.setBounds(545, 330, 175, 30);
//        panel.add(nameText);

        // LABEL DAN TEXTFIELD TAGIHAN
        billLabel = new JLabel("Tagihan");
        billLabel.setFont(new Font("Constantina", Font.PLAIN, 17));
        billLabel.setBounds(200, 550, 100, 30);
        panel.add(billLabel);

        billText = new JTextField(20);
        billText.setBounds(305, 550, 175, 30);
        panel.add(billText);

        // LABEL DAN TEXTFIELD PEMBAYARAN
        payLabel = new JLabel("Pembayaran");
        payLabel.setFont(new Font("Constantina", Font.PLAIN, 17));
        payLabel.setBounds(200, 600, 100, 30);
        panel.add(payLabel);

        payText = new JTextField(20);
        payText.setBounds(305, 600, 175, 30);
        panel.add(payText);

        //BUTTON 
        payButton = new JButton("Bayar");
        payButton.setBounds(420, 690, 100, 30);
        panel.add(payButton);

        confButton = new JButton("Konfirmasi");
        confButton.setBounds(260, 690, 100, 30);
        panel.add(confButton);

    }

    public static void main(String[] args) {
        //membuat frame
        JFrame f = new JFrame("KASIR");

        //program akan berhenti jika keluar
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //ukuran frame
        f.setSize(800, 800);

        //summon panel
        JPanel panel = new JPanel();
        f.add(panel);
        PaymentInterface(panel);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        handleConfirmButtonClick();
        handlePayButtonClick();
        refreshCheckoutTable();
    }
    
    public static void refreshCheckoutTable() {
        DefaultTableModel checkoutModel = new DefaultTableModel();
        Object[] checkoutColumns = new Object[2];
        checkoutColumns[0] = "Nama Barang";
        checkoutColumns[1] = "harga";

        checkoutModel.setColumnIdentifiers(checkoutColumns);

        for (Payment pay : payment) {
            Object[] data = new Object[2];
            data[0] = pay.getProduct().getName();
            data[1] = pay.Bill();

            checkoutModel.addRow(data);
        }

        checkout.setModel(checkoutModel);
    }

    public static void handleConfirmButtonClick() {
        confButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = codeText.getText().toString();
                String amount = amountText.getText().toString();

                paymentService service = new paymentService();
                productModel product = service.getByName(name);

                if (product != null) {
                    int price = product.getPrice();
                    Payment newPayment = new Payment();
                    newPayment.setAmount(Integer.parseInt(amount));
                    newPayment.setProduct(product);

                    payment.add(newPayment);
                    updateBill();
                    refreshCheckoutTable();
                    resetInput();
                }
            }
        });
    }
    
    public static void handlePayButtonClick() {
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bill = 0;
        
                for (Payment pay : payment) {
                    bill += pay.Bill();
                }
                
                JOptionPane.showMessageDialog(null, "Kembalian Anda senilai Rp " + (Integer.parseInt(payText.getText()) - bill));
                
                resetInput();
                payment = new ArrayList<>();
                updateBill();
                refreshCheckoutTable();
                payText.setText("");
            }
        });
    }
    
    public static void updateBill() {
        int bill = 0;
        
        for (Payment pay : payment) {
            bill += pay.Bill();
        }
        
        billText.setText(String.valueOf(bill));
    }
    
    public static void resetInput() {
        codeText.setText("");
        amountText.setText("");
    }
}
