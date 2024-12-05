import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import restaurantapp.DatabaseConnection;
import restaurantapp.Table.Table;
import restaurantapp.Table.TableServices;
import org.mariadb.jdbc.Connection;
import java.util.ArrayList;

public class ReservationPage extends JFrame {

    private Connection db = (Connection) DatabaseConnection.getConnection();
    private JPanel panel;
    private JButton[] tableButtons = new JButton[10];
    private HashMap<Integer, Boolean> tableStatus; // Menyimpan status ketersediaan meja
    private TableServices tableSer = new TableServices();
    private ArrayList<Table> tables = tableSer.getAll(db);
    
    public ReservationPage() {
        initComponents();
        
        if (db == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void initComponents() {
        // Layouting  
        setTitle("Denah Restoran");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inisialisasi status meja (true = tersedia, false = digunakan)
        tableStatus = new HashMap<>();
        for (int i = 0; i < tables.size(); i++) {
            boolean status = true;
            if(tables.get(i).getStatus().equals("maintenance")){
                status = false;
            }
            tableStatus.put(i, status); // Default semua meja tersedia
        }
 
        // Tombol Kembali 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        
        JButton btnBack = new JButton(" Kembali");;
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
        
        // Panel utama untuk denah meja
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Reservation Restaurant");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        // Pintu masuk dan kasir
        JLabel lblEntrance = new JLabel("Pintu Masuk");
        lblEntrance.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblEntrance, gbc);

        JLabel lblCashier = new JLabel("Meja Kasir");
        lblCashier.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(lblCashier, gbc);

        // Menampilkan meja dan tombol reservasi
        for (int i = 0; i < tables.size(); i++) {
            int total = tableSer.countReservations(db, tables.get(i).getId());
            tableButtons[i] = new JButton("Meja " + (i + 1) + " (" + total + ")");
            tableButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            tableButtons[i].setPreferredSize(new Dimension(110, 40));

            // Atur warna berdasarkan status meja
            updateButtonStatus(i);

            final int tableIndex = i;
            tableButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tableStatus.get(tableIndex)) {
                        new DetailReservationPage(tableIndex).setVisible(true);
                        dispose();
                    }
                }
            });

            gbc.gridx = i % 2 == 0 ? 0 : 1;
            gbc.gridy = 2 + (i / 2);
            panel.add(tableButtons[i], gbc);
        }

        add(panel, BorderLayout.CENTER);
    }

    // Metode untuk memperbarui warna tombol berdasarkan status
    private void updateButtonStatus(int tableIndex) {
        if (tableStatus.get(tableIndex)) {
            tableButtons[tableIndex].setBackground(new Color(34, 139, 34)); // Tersedia
            tableButtons[tableIndex].setEnabled(true); // Bisa diklik
        } else {
            tableButtons[tableIndex].setBackground(Color.BLACK); // Digunakan
            tableButtons[tableIndex].setEnabled(false); // Tidak bisa diklik
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationPage reservationPage = new ReservationPage();
            reservationPage.setVisible(true);
        });
    }
}
