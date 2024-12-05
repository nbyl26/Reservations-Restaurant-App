
import com.toedter.calendar.JDateChooser;
import lu.tudor.santec.jtimechooser.JTimeChooser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.swing.*;
import java.awt.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationFormPage extends JFrame {

    private int tableIndex;

    public ReservationFormPage(int tableIndex) {
        this.tableIndex = tableIndex;
        initComponents();
    }

    private void initComponents() {
        setTitle("Formulir Reservasi");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Formulir Reservasi");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        // Input Nama
        JLabel lblName = new JLabel("Nama:");
        lblName.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField txtName = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(lblName, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(txtName, gbc);

        // Input No. Telepon
        JLabel lblPhone = new JLabel("No. Telepon:");
        lblPhone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField txtPhone = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(lblPhone, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(txtPhone, gbc);

        // Input Tanggal Reservasi
        JLabel lblDate = new JLabel("Tanggal:");
        lblDate.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(lblDate, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(dateChooser, gbc);

        // Input Jam
        JLabel lblTime = new JLabel("Jam:");
        lblTime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTimeChooser timeChooser = new JTimeChooser();
//        JTextField txtTime = new JTextField(10);
        timeChooser.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(lblTime, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(timeChooser, gbc);

        // Tombol Kembali     
        JButton btnBack = new JButton("Kembali");
        btnBack.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnBack.setBackground(Color.CYAN);
        btnBack.addActionListener(e -> {
            new ReservationPage().setVisible(true);
            dispose();
        });

        // Tombol Kirim Reservasi
        JButton btnSubmit = new JButton("Kirim Reservasi");
        btnSubmit.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnSubmit.setBackground(new Color(34, 139, 34));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.addActionListener(e -> {
            boolean valid = true;
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String date = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
            String time = timeChooser.getTimeField().getText();

            if (name.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                valid = false;
            } else {
                //Validasi Waktu
                DateTimeFormatter formatter;
                System.out.println("ReservationFormPage" + date + " " + date.length());

                // Tentukan formatter berdasarkan panjang string `date`
                if (date.length() == 11) {  
                    formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss");
                } else if (date.length() == 12) {
                    formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
                } else {
                    System.out.println(date.length());
                    throw new IllegalArgumentException("Date format is invalid. Expected format: 'MMM RESERVATIONFORMPAGE, yyyy HH:mm:ss' or 'MMM dd, yyyy HH:mm:ss'.");
                }

                ZonedDateTime parsedTime = LocalDateTime.parse(date + " " + time, formatter).atZone(ZoneId.systemDefault());
                ZonedDateTime currentTime = ZonedDateTime.now();

                if (!validatePhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(this, "Nomor Telepon tidak valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    valid = false;
                } else if (parsedTime.isBefore(currentTime)) {
                    JOptionPane.showMessageDialog(this, "Tanggal dan Jam yang diinputkan tidak valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    valid = false;
                }
            }

            if (valid) {
                new ConfirmationReservationPage(name, phone, date, time, tableIndex).setVisible(true);
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnBack);
        bottomPanel.add(btnSubmit);

        panel.add(bottomPanel, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReservationFormPage(1).setVisible(true));
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        Pattern patern = Pattern.compile("^[0-9]+$");
        Matcher hasil = patern.matcher(phoneNumber);

        return hasil.matches();
    }
}
