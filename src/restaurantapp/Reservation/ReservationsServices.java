/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Duration;

import org.mariadb.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import org.mariadb.jdbc.Statement;
import restaurantapp.Table.Table;
import restaurantapp.User.User;

/**
 *
 * @author LENOVO
 */
public class ReservationsServices {

    public static void main(String[] arg) {

    }

    public Reservations getReservation(Connection db, int code) {
        Reservations res = new Reservations();
        try {

//           Sementara Pakai ID karena Code belum di buat di database
            String query = " SELECT "
                    + "r.reservation_id, "
                    + "r.status, "
                    + "r.code,"
                    + " u.user_id,"
                    + " u.name,"
                    + " u.phone,"
                    + " t.table_id,"
                    + " t.status,"
                    + " t.capacity,"
                    + " r.reservation_date,"
                    + " r.reservation_time"
                    + " FROM reservations AS r"
                    + " INNER JOIN"
                    + " users AS u ON r.user_id = u.user_id"
                    + " INNER JOIN "
                    + "tables AS t ON r.table_id = t.table_id "
                    + "WHERE r.code = ? "
                    + "limit 1";
            PreparedStatement st = db.prepareStatement(query);

            st.setString(1, String.valueOf(code));

            ResultSet result = st.executeQuery();
            if (result.next()) {
                do {
                    int user_id = Integer.parseInt(result.getString("u.user_id"));
                    String name = result.getString("u.name");
                    String phone = result.getString("u.phone");

                    User user = new User(name, phone, user_id);

                    int table_id = Integer.parseInt(result.getString("t.table_id"));
                    int capacity = Integer.parseInt(result.getString("t.capacity"));
                    String status_table = result.getString("t.status");

                    Table table = new Table(table_id, status_table, capacity);

                    int id = Integer.parseInt(result.getString("r.reservation_id"));
                    String status_reservation = result.getString("r.status");
                    String reservation_date = result.getString("r.reservation_date");
                    String reservation_time = result.getString("r.reservation_time");

                    res = new Reservations(user, table, id, status_reservation, String.valueOf(code), reservation_date, reservation_time);

                } while (result.next());
            } else {
                res = new Reservations("Reservation Not Found");
            }
        } catch (SQLException err) {
            res = new Reservations(err.getMessage());
        }

        return res;
    }

    public Reservations createReservations(Connection db, String date, String time, int tableId, int userId, String code) {
        Reservations res;
        DateTimeFormatter formatterInput;
        DateTimeFormatter formatterDb = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        System.out.println("Reservasi: " + date + " " +date.length());

        // Tentukan formatter berdasarkan panjang string `date`
        if (date.length() == 11) { // Format dengan satu digit tanggal (e.g., Dec 3, 2024)
            formatterInput = DateTimeFormatter.ofPattern("MMM d, yyyy");
        } else if (date.length() == 12) { // Format dengan dua digit tanggal (e.g., Dec 03, 2024)
            formatterInput = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        } else {
            throw new IllegalArgumentException("Date format is invalid. Expected format: 'MMM reservasi, yyyy' or 'MMM dd, yyyy'.");
        }

        if (checkReservations(db, date, time, tableId)) {
            LocalDate dateInput = LocalDate.parse(date, formatterInput);
            date = dateInput.format(formatterDb);
            try {
                String query = "INSERT INTO reservations (user_id, table_id, reservation_date, reservation_time, status, code) VALUES (?,?,?,?,?,?)";
                PreparedStatement st = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                st.setInt(1, userId);
                st.setInt(2, tableId);
                st.setString(3, date);
                st.setString(4, time);
                st.setString(5, "confirmed");
                st.setString(6, code);

                int row = st.executeUpdate();

                if (row > 0) {
                    ResultSet result = st.getGeneratedKeys();
                    if (result.next()) {
                        int reservationsId = result.getInt(1);

                        res = new Reservations(userId, tableId, reservationsId, "confirmed", code, date, time);
                    } else {
                        res = new Reservations("Not Found");
                    }

                } else {
                    res = new Reservations("Not Found");
                }

            } catch (Exception e) {
                res = new Reservations(e.getMessage());
            }
        } else {
            res = new Reservations("Same DateTime");
        }

        return res;
    }

    private boolean checkReservations(Connection db, String date, String time, int tableId) {
        boolean condition = true;
        DateTimeFormatter formatterInput;
        
        System.out.println("checkReservations: " + date + " " + date.length());
        
        // Tentukan formatter berdasarkan panjang string `date`
        if (date.length() == 11) { // Format dengan satu digit tanggal (e.g., Dec 3, 2024)    
            formatterInput = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss");
        } else if (date.length() == 12) { // Format dengan dua digit tanggal (e.g., Dec 03, 2024)
            formatterInput = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
        } else {
            throw new IllegalArgumentException("Date format is invalid. Expected format: 'MMM checkRESERVATIONS, yyyy' or 'MMM dd, yyyy'.");
        }

        DateTimeFormatter formatterDb = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(date + " " + time);
        ZonedDateTime current = LocalDateTime.parse(date + " " + time, formatterInput).atZone(ZoneId.systemDefault());

        try {
            String query = "SELECT reservation_time AS time, reservation_date AS date FROM reservations WHERE table_id = ?";
            PreparedStatement st = db.prepareStatement(query);

            st.setInt(1, tableId);

            ResultSet result = st.executeQuery();
            while (result.next()) {
                String dbTime = result.getString("time");
                String dbDate = result.getString("date");

                ZonedDateTime dbDateTime = LocalDateTime.parse(dbDate + " " + dbTime, formatterDb).atZone(ZoneId.systemDefault());
                long difference = Duration.between(dbDateTime, current).toHours();

                if (difference < 2) {
                    condition = false;
                }
            }
        } catch (SQLException e) {
            condition = false;
        }

        return condition;
    }

    public ArrayList<Reservations> getReservationsTable(Connection db, int id) {
        ArrayList<Reservations> reservations = new ArrayList<Reservations>();
        try {
            String query = "SELECT users.name , r.reservation_date , r.reservation_time FROM reservations AS r INNER JOIN users ON r.user_id = users.user_id WHERE r.table_id = ?";
            PreparedStatement st = db.prepareStatement(query);
            st.setInt(1, id);

            ResultSet result = st.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String date = result.getString("reservation_date");
                String time = result.getString("reservation_time");

                reservations.add(new Reservations(name, date, time));
            }

        } catch (SQLException e) {
            reservations.add(new Reservations(e.getMessage()));
        }
        return reservations;
    }
}
