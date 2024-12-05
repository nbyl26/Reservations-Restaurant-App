/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nabil
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import restaurantapp.DatabaseConnection;
import restaurantapp.Reservation.Reservations;
import restaurantapp.Reservation.ReservationsServices;
import org.mariadb.jdbc.Connection;

    

public class DetailReservationPage extends JFrame {
    
    private Connection db;
    private int tableIndex;
//    private List<String> reservations = new ArrayList<>();
    private ReservationsServices resSer;
    private ArrayList<Reservations> reservations;

    public DetailReservationPage(int tableIndex) {
        this.db = (Connection) DatabaseConnection.getConnection();
        this.resSer = new ReservationsServices();
        this.reservations = resSer.getReservationsTable(db, tableIndex+1);
        
        if (db == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        this.tableIndex = tableIndex + 1;
        initComponents();
    }

//    private void loadReservations() {
//        // Data reservasi dummy, bisa diganti dengan data dari database
//        reservations.add("2024-11-19 12:00: John Doe");
//        reservations.add("2024-11-19 14:00: Jane Doe");
//        reservations.add("2024-11-19 16:00: Bob Smith");
//    }

    private void initComponents() {
        setTitle("Detail Reservasi Meja " + (tableIndex + 1));
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Detail Reservasi Meja " + (tableIndex + 1));
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        // Daftar reservasi
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (Reservations r : reservations) {
            String text = r.getDate() + " " + r.getTime() + " " + r.getUserS();
            textArea.append(text + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(scrollPane, gbc);

        // Tombol Kembali
        JButton btnBack = new JButton("Kembali");
        btnBack.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnBack.setBackground(Color.CYAN);
        btnBack.addActionListener(e -> {
            new ReservationPage().setVisible(true);
            dispose();
        });

        // Tombol Reservasi Sekarang
        JButton btnReserveNow = new JButton("Reservasi Sekarang");
        btnReserveNow.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnReserveNow.setBackground(Color.GREEN);
        btnReserveNow.addActionListener(e -> {
            new ReservationFormPage(tableIndex).setVisible(true);
            dispose();
        });

        // Panel untuk tombol-tombol
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnBack);
        bottomPanel.add(btnReserveNow);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(bottomPanel, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { 
            
//            DetailReservationPage detailReservation = new DetailReservationPage();
//            detailReseration.setVisible(true);
            @Override
            public void run() {
                new DetailReservationPage(0).setVisible(true);
            }
        });
    }
}
