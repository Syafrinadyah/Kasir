package view;

import common.AbsenService;
import common.ConfigDatabase;
import controller.ValidAbsent;
import model.Absent;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AbsenFrame extends JFrame implements AbsenService {

    private JLabel jlName = new JLabel("Masukan Nama Anda");
    private JLabel jlPhone = new JLabel("Masukan telepon Anda");
    private JTextField jtfName = new JTextField(30);
    private JTextField jtfPhone = new JTextField(10);
    private JButton jbAbsent = new JButton("Absen");
    private JButton jbLeft = new JButton("left");

    private JLabel jlStatusAbsent = new JLabel("");

    private String name;
    private long phone;

    User user;
    Absent absent;
    ValidAbsent validAbsent;

    public AbsenFrame() {
        super("Absent Kasir");
        initView();
        doAbsent();
        doLeft();
    }

    private void initView() {
        JPanel base = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        base.add(jlName, constraints);

        constraints.gridx = 1;
        base.add(jtfName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        base.add(jlPhone, constraints);

        constraints.gridx = 1;
        base.add(jtfPhone, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        base.add(jlStatusAbsent, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        base.add(jbAbsent, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        base.add(jbLeft, constraints);

        base.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Absent Panel"));

        add(base);

        pack();
        setLocationRelativeTo(null);
    }

    private void doAbsent() {
        jbAbsent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = jtfName.getText().toString();
                phone = Long.parseLong(jtfPhone.getText().toString());

                if (!name.equals("") && !jtfPhone.getText().equals("")) {
                    user = new User();
                    user.setNama(name);
                    user.setTelfon(phone);

                    absent = new Absent(user);
                    absent.setAbsenin();

                    validAbsent = new ValidAbsent(absent);

                    try {
                        boolean process = insertAbsent(absent);

                        if (process) {
                            JOptionPane.showMessageDialog(null, "Absensi berhasil");
                            jlStatusAbsent.setText("status:" + validAbsent.showAbsent()
                                    + "name: " + user.getNama()
                                    + "phone: " + user.getTelfon()
                                    + "start class " + absent.getAbsenin()
                                    + "end class" + absent.getAbsenout());
                            jbAbsent.setVisible(false);
                            jlName.setVisible(false);
                            jlPhone.setVisible(false);
                            jtfPhone.setVisible(false);
                            jtfName.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Maaf, nama dan nomor telepon tidak terdaftar.");
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    jlStatusAbsent.setText("empty");
                }
            }
        });
    }

    private void doLeft() {
        jbLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                this.setVisible(false);
//                LoginView.visible(true);
            }
        });
    }

    @Override
    public boolean insertAbsent(Absent absent) throws Exception {
        User user = getUserByNameAndPhone(absent.getUser().getNama(), absent.getUser().getTelfon());

        if (user == null) {
            return false;
        }

        String qAbsent = " insert into absen (id_user, absenin, absenout) values (?, ?, ?)";
        Connection connection = new ConfigDatabase().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(qAbsent);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setLong(2, absent.getAbsenin());
        preparedStatement.setInt(3, 0);

        preparedStatement.execute();

        connection.close();
        return true;
    }

    public User getUserByNameAndPhone(String name, long phone) throws Exception {
        String query = "select * from user where nama = ? and telfon = ?";
        Connection connection = new ConfigDatabase().getConnection();
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, name);
        preparedStmt.setLong(2, phone);
        ResultSet result = preparedStmt.executeQuery();

        if (result.next()) {
            User user = new User();
            user.setId(result.getInt("id"));
            user.setNama(result.getString("nama"));
            user.setAlamat(result.getString("alamat"));
            user.setTelfon(result.getLong("telfon"));

            return user;
        }

        return null;
    }
}
