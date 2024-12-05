/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp;


import java.util.ArrayList;
import org.mariadb.jdbc.Connection;
import restaurantapp.Reservation.Reservations;
import restaurantapp.Reservation.ReservationsServices;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// Table
  
import restaurantapp.Table.TableServices;
import restaurantapp.Table.Table;

// User
import restaurantapp.User.User;
import restaurantapp.User.UserServices;
/**
 *
 * @author nabil
 */
public class TestConnection {

    public static void main(String[] args) {
        // Mencoba koneksi
        Connection connection = (Connection) DatabaseConnection.getConnection();
        
        if (connection != null) {
            System.out.println("Koneksi berhasil!");
        } else {
            System.out.println("Koneksi gagal.");
        }
        
        
//        User Service
//        UserServices userSer = new UserServices();
        
//        User user = userSer.getUser(connection, "089751637121");
//        User user = userSer.createUser(connection, "Nabil", "89182649812");
//        System.out.print(user.getId());
       
//        Table Service
//          TableServices tableSer = new TableServices();
          
//          Table table = tableSer.getTable(connection, 1);
//          boolean table = tableSer.setStatus(connection, "maintenance", "1");
//          
//          System.out.println(table);

//            int tables = tableSer.countReservations(connection, 1);
//            
//            System.out.println(tables);

//Reservations Services
           ReservationsServices resSer = new ReservationsServices();
//           boolean res = resSer.checkReservations(connection, "25 Nov 2024", "20:00:00", 1);
           Reservations res = resSer.getReservation(connection, 8897);
//            Reservations res = resSer.createReservations(connection, "25 Nov 2024", "20:00:00", 1, 1, "8897");
//           
           System.out.println(res);

//             ArrayList<Reservations> res = resSer.getReservationsTable(connection, 1);
//             
//             for (Reservations r : res){
//                 System.out.println(r.getDate() + " " + r.getTime() + " " + r.getUserS());
//             }
    }
}
