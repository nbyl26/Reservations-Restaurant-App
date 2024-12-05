/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Connection;
/**
 *
 * @author LENOVO
 */
public class User {
    private int id;
    private String name;  
    private String phone;
    private String err;
    
//    Constructor
    public User(String name, String phone, int id){
        this.name = name;
        this.phone = phone;
        this.id = id;
    }
    
    public User(String err){
        this.err = err;
    }
    
    public User() {
    
    }
    
//    Setter and Getter
     public int getId(){
         return this.id;
     }
     
     public String getName(){
        return this.name;
     }
     
     public String getPhone(){
           return this.phone;
     }
     
     public String getError(){
         return this.err;
     }
    @Override
    public String toString(){
        return "id : "+ this.id + "\nName: " + this.name + "\nPhone: " + this.phone + "\nError: " + this.err + "\n";
    }
        
}

