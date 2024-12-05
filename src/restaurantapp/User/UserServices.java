/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

/**
 *  
 * @author LENOVO
 */
public class UserServices {
    
    public static void main(String[] arg){
        
    }
    
    public User getUser(Connection db, String phone) {
       User user = new User();
       try{
//           Query  
            String query = "SELECT * FROM users WHERE phone = ? limit 1";
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, phone);
            
//            Execute Query
            ResultSet result = st.executeQuery();
            
            if(result.next()){
                do{
                    String name = result.getString("name");
                    int user_id = Integer.parseInt(result.getString("user_id"));
                    
                    user = new User(name, phone, user_id);
                }while(result.next());
            }else{
                user = new User("User Not Found");
            }     
            
       }catch(SQLException err){
           user = new User(err.getMessage());
       }
       return user;
    }
    
     public User getUser(Connection db, int id) {
       User user = new User();
       try{
           
//           Query
            String query = "SELECT * FROM users WHERE user_id = ? limit 1";
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, String.valueOf(id));
            
//            Execute Query
            ResultSet result = st.executeQuery();
            if(result.next()){
                while(result.next()){
                    String name = result.getString("name");
                    String phone = result.getString("phone");
                    
                    user = new User(name, phone, id);
                }
            }else{
                user = new User("User Not Found");
            }
       }catch(SQLException err){
           user = new User(err.getMessage());
       }
       return user;
    }
    
    public User createUser(Connection db, String name, String phone){
        User user;
        try{
//            Query
            String query = "INSERT INTO users (name,phone) VALUES(?,?)";
            PreparedStatement st = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, phone);
            
            int row = st.executeUpdate();
            if(row > 0){
                ResultSet result = st.getGeneratedKeys();
                if(result.next()){
                    int id = result.getInt(1);
                
                    user = new User(name,phone,id);
                }else{
                    user = new User("Not Found");
                }  
            }else{
                user = new User("Not Found");
            }
             
        }catch(SQLException err){            
            user = new User(err.getMessage());
        }
        return user;
    }
}
