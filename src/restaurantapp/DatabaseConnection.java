/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nabil
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/restaurant_db?allowPublicKeyRetrieval=true"; // URL database
    private static final String USER = "root"; // Username database (default MySQL root)
    private static final String PASSWORD = ""; // Password database (default MySQL kosong)

    // Method untuk mendapatkan koneksi
    public static Connection getConnection() {  
        try {
            // Menghubungkan ke database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException e) {
            // Menangani kesalahan
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
}
