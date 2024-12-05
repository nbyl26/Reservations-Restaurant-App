
import javax.swing.*;
import java.awt.*;
import java.util.Random;

import restaurantapp.DatabaseConnection;
import restaurantapp.User.User;
import restaurantapp.User.UserServices;
import restaurantapp.Reservation.Reservations;
import restaurantapp.Reservation.ReservationsServices;
import org.mariadb.jdbc.Connection;

public class ConfirmationReservationPage extends JFrame {

    private String name;
    private String phone;
    private String date;
    private String time;
    private int tableIndex;
    private String reservationCode;

    // Konstruktor menerima data dari ReservationFormPage
    public ConfirmationReservationPage(String name, String phone, String date, String time, int tableIndex) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.tableIndex = tableIndex;

        generateReservationCode(); // Membuat kode reservasi unik
        initComponents();
    }

    // Metode untuk membuat kode reservasi unik
    private void generateReservationCode() {
        Random random = new Random();
        int randomCode = 1000 + random.nextInt(9000); 
        this.reservationCode = String.valueOf(randomCode);
    }

    private void initComponents() {
        setTitle("Konfirmasi Reservasi");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Judul Halaman
        JLabel lblTitle = new JLabel("Konfirmasi Reservasi");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        // Menampilkan Nama
        JLabel lblName = new JLabel("Nama: " + name);
        lblName.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(lblName, gbc);

        // Menampilkan No. Telepon
        JLabel lblPhone = new JLabel("No. Telepon: " + phone);
        lblPhone.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 2;
        panel.add(lblPhone, gbc);

        // Menampilkan Tanggal
        JLabel lblDate = new JLabel("Tanggal: " + date);
        lblDate.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 3;
        panel.add(lblDate, gbc);

        // Menampilkan Jam
        JLabel lblTime = new JLabel("Jam: " + time);
        lblTime.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 4;
        panel.add(lblTime, gbc);

        // Menampilkan Kode Reservasi
        JLabel lblCode = new JLabel("Kode Reservasi: " + reservationCode);
        lblCode.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblCode.setForeground(new Color(0, 128, 0)); // Warna hijau
        gbc.gridy = 5;
        panel.add(lblCode, gbc);

        // Tombol Kembali untuk mengedit data
        JButton btnEdit = new JButton("Edit Data");
        btnEdit.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnEdit.setBackground(Color.CYAN);
        btnEdit.addActionListener(e -> {
            new ReservationFormPage(this.tableIndex).setVisible(true);
            dispose();
        });

        // Tombol Konfirmasi untuk menyimpan data
        JButton btnConfirm = new JButton("Konfirmasi");
        btnConfirm.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnConfirm.setBackground(new Color(34, 139, 34));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.addActionListener(e -> {
            saveReservationToDatabase();
            new HomePage().setVisible(true);
            dispose();
        });

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnConfirm);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    // Simpan data reservasi ke database
    private void saveReservationToDatabase() {
        Connection db = (Connection) DatabaseConnection.getConnection();
        UserServices userSer = new UserServices();
        ReservationsServices resSer = new ReservationsServices();
        
        User user = CheckUser(userSer, db, this.name, this.phone);
        
        if(!(user.getError() == null)){
            JOptionPane.showMessageDialog(
                    this,
                    "User Error" + user.getName(),
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
        }
             
        Reservations res = resSer.createReservations(db, this.date, this.time, this.tableIndex, user.getId(), this.reservationCode);
        
        if(res.getError() != null){
            if(res.getError().equals("Same DateTime")){
            JOptionPane.showMessageDialog(
                    this,
                    "Meja sudah di reservasi di jam ini " + this.date + "  " + this.time,
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
        }else if(!(res.getError() == null)){
            JOptionPane.showMessageDialog(
                    this,
                    res.getError(),
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    this,
                    "Reservasi berhasil dikonfirmasi!\nKode Reservasi Anda: " + reservationCode,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
            );
         }
        }
    }

    
    private User CheckUser(UserServices ser, Connection db, String name, String phone) {
        User user = ser.getUser(db, phone);
        
        if(!(user.getError() == null)){
            user = ser.createUser(db, name, phone);
        }
        
        return user;
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmationReservationPage(
                "John Doe", "081234567890", "Nov 18, 2024", "19:00:00",1
        ).setVisible(true));
    }
}
