import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurantapp.DatabaseConnection;
import java.sql.*;

public class StatusReservationPage extends JFrame {

    private Connection db;

    public StatusReservationPage() {
        db = DatabaseConnection.getConnection();
        if (db == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        initComponents();
    }

    private void initComponents() {
        setTitle("Status Reservasi");
        setSize(360, 640); // Dimensi layar 360x640
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan layout BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Tombol Kembali 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        
        JButton btnBack = new JButton(" Kembali");
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(70, 130, 180));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new HomePage().setVisible(true);
                dispose();
            }
        });
        
        topPanel.add(btnBack);
        add(topPanel, BorderLayout.NORTH);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Status Reservasi");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(34, 139, 34)); // Hijau gelap
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Input Code Reservasi
        JLabel lblReservationCode = new JLabel("Masukkan Code Reservasi:");
        lblReservationCode.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblReservationCode.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField reservationCodeField = new JTextField(15);
        reservationCodeField.setMaximumSize(new Dimension(300, 30));
        reservationCodeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        reservationCodeField.setHorizontalAlignment(JTextField.CENTER); // Set input teks agar berada di tengah

        // Tombol Cek Status
        JButton btnCheckStatus = new JButton("Cek Status");
        btnCheckStatus.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCheckStatus.setBackground(new Color(34, 139, 34)); // Hijau gelap
        btnCheckStatus.setForeground(Color.WHITE);
        btnCheckStatus.setFocusPainted(false);
        btnCheckStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCheckStatus.setMaximumSize(new Dimension(300, 50));

        // Tambahkan aksi pada tombol
        btnCheckStatus.addActionListener(e -> {  
            String reservationCode = reservationCodeField.getText();
            if (reservationCode.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Code Reservasi tidak boleh kosong!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                // Periksa Code reservasi dalam database
                boolean isFound = checkReservationStatus(reservationCode);
                if (isFound) {
                    // Arahkan ke StatusDetailPage jika Code ditemukan
                    new StatusDetailsPage(reservationCode).setVisible(true);
                    dispose();
                } else {
                    // Tampilkan pesan jika Code tidak ditemukan
                    JOptionPane.showMessageDialog(
                            this,
                            "Code Reservasi tidak ditemukan. Pastikan Anda memasukkan Code yang benar.",
                            "Peringatan",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        // Footer
        JLabel lblFooter = new JLabel("© 2024 Restaurant Reservation App");
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(160, 160, 160)); // Abu-abu terang
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Susun komponen ke dalam panel
        panel.add(lblTitle);
        panel.add(lblReservationCode);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi vertikal
        panel.add(reservationCodeField);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spasi vertikal
        panel.add(btnCheckStatus);
        panel.add(Box.createVerticalGlue()); // Spasi agar footer berada di bawah
        panel.add(lblFooter);

        // Tambahkan panel ke JFrame
        add(panel);
    }

    private boolean checkReservationStatus(String reservationCode) {
        try {
            String query = "SELECT * FROM reservations WHERE code = ?";
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, reservationCode);
            ResultSet rs = stmt.executeQuery();

            // Jika ada data yang ditemukan, kembalikan true
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memeriksa status reservasi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatusReservationPage().setVisible(true));
    }
}
