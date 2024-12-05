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
import restaurantapp.DatabaseConnection;
import java.sql.Connection;

public class HomePage extends javax.swing.JFrame {
  
    private Connection db;

    public HomePage() {
        db = DatabaseConnection.getConnection();
        if (db == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        // Judul
        JLabel lblTitle = new JLabel("Restaurant Reservation");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0x004080)); // Biru tua
        lblTitle.setBounds(60, 40, 280, 30);

        // Subtitle
        JLabel lblSubtitle = new JLabel("Pilih menu untuk memulai.");
        lblSubtitle.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblSubtitle.setForeground(new Color(0x606060)); // Abu-abu gelap
        lblSubtitle.setBounds(90, 80, 180, 20);

        // Tombol Reservasi Meja
        JButton btnReservasiMeja = new JButton("Reservasi Meja");
        btnReservasiMeja.setBounds(40, 150, 280, 50);
        btnReservasiMeja.setBackground(new Color(0x007B3E)); // Hijau tua
        btnReservasiMeja.setForeground(Color.WHITE); // Teks putih
        btnReservasiMeja.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnReservasiMeja.addActionListener(e -> openReservasiPage());

        // Tombol Cek Status Reservasi
        JButton btnCekStatus = new JButton("Cek Status Reservasi");
        btnCekStatus.setBounds(40, 220, 280, 50);
        btnCekStatus.setBackground(new Color(0x004080)); // Biru tua
        btnCekStatus.setForeground(Color.WHITE); // Teks putih
        btnCekStatus.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCekStatus.addActionListener(e -> openStatusReservasiPage());

        // Footer
        JLabel lblFooter = new JLabel("© 2024 Restaurant Reservation App");
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(0xA0A0A0)); // Abu-abu terang
        lblFooter.setBounds(80, 600, 200, 20);

        // Tambahkan komponen ke JFrame
        add(lblTitle);
        add(lblSubtitle);
        add(btnReservasiMeja);
        add(btnCekStatus);
        add(lblFooter);

        // Konfigurasi JFrame
        setTitle("Home Page");
        setSize(360, 640); // Ukuran 360x640
        setResizable(false); // Ukuran tetap
        setLocationRelativeTo(null); // Pusatkan layar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Navigasi ke ReservasiPage
    private void openReservasiPage() {
        ReservationPage reservasiPage = new ReservationPage();
        reservasiPage.setVisible(true);
        this.dispose();
    }

    private void openStatusReservasiPage() {
        StatusReservationPage statusReservasiPage = new StatusReservationPage();
        statusReservasiPage.setVisible(true);
        this.dispose();
    }

    // Main method untuk menjalankan aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

}
