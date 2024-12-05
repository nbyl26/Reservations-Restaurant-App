import javax.swing.*;
import java.awt.*;
import org.mariadb.jdbc.Connection;
import restaurantapp.DatabaseConnection;
import restaurantapp.Reservation.Reservations;
import restaurantapp.Reservation.ReservationsServices;

public class StatusDetailsPage extends JFrame {

    private Connection db;
    private int reservationCode;

    public StatusDetailsPage(String reservationId) {
        this.reservationCode = Integer.valueOf(reservationId);
        db = (Connection) DatabaseConnection.getConnection();
        if (db == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        initComponents();
    }

    private void initComponents() {
        setTitle("Detail Reservasi");
        setSize(360, 640); // Dimensi layar 360x640
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Membuat window berada di tengah layar

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Warna latar belakang biru muda

        // Panel Tengah untuk menempatkan semua komponen di tengah
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Menyusun elemen ke tengah
        centerPanel.setBackground(Color.WHITE);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Detail Reservasi");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 139, 34)); // Hijau gelap
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Detail Reservasi
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailsPanel.setMaximumSize(new Dimension(320, 350)); // Batasan ukuran panel

        // Mendapatkan data reservasi dari database
        String[] reservationDetails = getReservationDetails(this.reservationCode);
        if (reservationDetails != null) {
            for (String detail : reservationDetails) {
                JLabel lblDetail = new JLabel(detail);
                lblDetail.setFont(new Font("Arial", Font.PLAIN, 16));
                lblDetail.setForeground(new Color(50, 50, 50)); // Warna teks abu-abu gelap
                lblDetail.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailsPanel.add(lblDetail);
                detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi vertikal
            }
        } else {
            JLabel lblError = new JLabel("Reservasi tidak ditemukan.");
            lblError.setFont(new Font("Arial", Font.PLAIN, 16));
            lblError.setForeground(Color.RED);
            lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsPanel.add(lblError);
        }

        // Tombol Kembali
        JButton btnBack = new JButton("Kembali");
        btnBack.setFont(new Font("Arial", Font.BOLD, 18));
        btnBack.setBackground(new Color(34, 139, 34)); // Hijau gelap
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setMaximumSize(new Dimension(300, 50));
        btnBack.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2)); // Border hijau
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor saat hover

        // Efek hover pada tombol
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(46, 139, 87)); // Warna hijau lebih gelap
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(34, 139, 34)); // Warna hijau gelap
            }
        });

        btnBack.addActionListener(e -> {
            dispose();
            new StatusReservationPage().setVisible(true);
        });

        // Footer
        JLabel lblFooter = new JLabel("© 2024 Restaurant Reservation App");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(160, 160, 160)); // Abu-abu terang
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Menambahkan komponen ke centerPanel
        centerPanel.add(lblTitle);
        centerPanel.add(detailsPanel);
        centerPanel.add(Box.createVerticalGlue()); // Spasi agar footer berada di bawah
        centerPanel.add(btnBack);
        centerPanel.add(lblFooter);

        // Menambahkan centerPanel ke panel utama
        panel.add(centerPanel, BorderLayout.CENTER);

        // Menambahkan panel ke JFrame
        add(panel);
    }

    private String[] getReservationDetails(int code) {
        ReservationsServices resSer = new ReservationsServices();
        Reservations res = resSer.getReservation(this.db, code);
        System.out.println(res.getError());
        if (res.getError() == null) {
            String id = "ID Reservasi: " + res.getId();
            String date = "Tanggal: " + res.getDate();
            String time = "Waktu: " + res.getTime();
            String userName = "Nama Pelanggan: " + res.getUser().getName();
            String tableId = "Meja: " + res.getTable().getId();
            String status = "Status: " + res.getStatus();

            return new String[]{id, date, time, userName, tableId, status};
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatusDetailsPage("8897").setVisible(true));
    }
}
