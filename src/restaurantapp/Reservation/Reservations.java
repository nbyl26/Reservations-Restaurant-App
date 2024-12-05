/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.Reservation;


import restaurantapp.Table.Table;
import restaurantapp.User.User;
/**
 *
 * @author LENOVO
 */
public class Reservations {
    private int userId;
    private User user;
    private String userS;
    private Table table;
    private int tableId;
    
    private int reservations_id;
    private String status;
    private String reservasion_date;
    private String reservasion_time;
    private String code;
    
    private String err;
    
//    Constructor
    public Reservations(){
        
    }
    
    public Reservations(int user, int table, int reservations_id ,String status, String code, String reservation_date, String reservation_time){
        this.userId = user;
        this.tableId = table;
        this.reservations_id = reservations_id;
        this.status = status;
        this.code = code;
        this.reservasion_date = reservation_date;
        this.reservasion_time = reservation_time;
    }
    
    public Reservations(User user, Table table, int reservations_id ,String status, String code, String reservation_date, String reservation_time){
        this.user = user;
        this.table = table;
        this.reservations_id = reservations_id;
        this.status = status;
        this.code = code;
        this.reservasion_date = reservation_date;
        this.reservasion_time = reservation_time;
    }
    
    public Reservations(String user, String date, String time){
        this.userS = user;
        this.reservasion_date = date;
        this.reservasion_time = time;
    }
    
    public Reservations(String err){
        this.err = err;
    }

//  Getter
    public User getUser() {
        return this.user;
    }
    
    public String getUserS() {
        return  this.userS;
    }
    
    public int getUserId(){
        return this.userId;
    }
    
    public int getTableId(){
        return this.tableId;
    }
    
    public Table getTable() {
        return this.table;
    }
    
    public int getId(){
        return this.reservations_id;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public String getDate(){
        return this.reservasion_date;
    }
    
    public String getTime(){
        return this.reservasion_time;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public String getError(){
        return this.err;
    }
    
    
    @Override
    public String toString(){
        return "Id: " + this.reservations_id + "\nStatus: " + this.status + "\nCode: " + this.code + "\n" + this.user + this.table +"Reservation Date: "+ this.reservasion_date + "\nReservation Time" + this.reservasion_time + "\nError: " + this.err + "\n";
    }
}
