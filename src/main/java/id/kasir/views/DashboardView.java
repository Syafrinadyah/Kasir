/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.kasir.app.view;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DashboardView extends JFrame {
    
    static JFrame frame = new JFrame();
    static JLabel lbJudul = new JLabel("Menu Utama");
    static JButton btAbsent = new JButton("Absen");
    static JButton btPayment = new JButton("Pembayaran");
    static JButton btInventori = new JButton("Inventori");
    
static void initView(){
   frame.setSize(500, 350);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setLocationRelativeTo(null);
   frame.setVisible(true);
   frame.setLayout(null);
   frame.setBackground(new Color(0, 80, 239));
   
   frame.add(lbJudul);
   frame.add(btAbsent);
   frame.add(btPayment);
   frame.add(btInventori);
   
   lbJudul.setBounds(140, 40, 250, 30);
   lbJudul.setFont(new java.awt.Font("Tohama", 1, 26));
   btAbsent.setBounds(80, 150, 120, 30);
   btPayment.setBounds(80, 100, 120, 30);
   btInventori.setBounds(80, 200, 120, 30);
}
    
public static void main (String []args){  
     initView();   

}
}
