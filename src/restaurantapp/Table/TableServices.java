/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

/**  
 *
 * @author LENOVO
 */
public class TableServices {
    
    public void main(String[] arg){
        
    }
    
    public Table getTable(Connection db, int id){
        Table table = new Table();
        try{
            String query = "SELECT * FROM tables WHERE table_id = ? limit 1";
            PreparedStatement st = db.prepareStatement(query);
            
            st.setString(1, String.valueOf(id));
            ResultSet result = st.executeQuery();
            if(result.next()){
               do{
                   int table_id = Integer.parseInt(result.getString("table_id"));
                   String status = result.getString("status");
                   int capacity = Integer.parseInt(result.getString("capacity"));
                   
                   table = new Table(table_id, status, capacity);
                }while(result.next());
            }else{
                table = new Table("Table Not Found");
            }
        }catch(SQLException err){
            table = new Table(err.getMessage());
        }
        return table;
    }
    
    public ArrayList<Table> getAll(Connection db){
        ArrayList<Table> tables = new ArrayList<Table>();
        try{
            String query = "SELECT * FROM tables";
            Statement st = db.createStatement();
            
            ResultSet results = st.executeQuery(query);
            
            if(results.next()){
                do{
                    int id = Integer.parseInt(results.getString("table_id"));
                    int capacity = Integer.parseInt(results.getString("capacity"));
                    String status = results.getString("status");
                    
                    
                    Table table = new Table(id ,status, capacity);
                    tables.add(table);
                }while(results.next());
            }
        }catch(SQLException err){
           return new ArrayList<>();
        }
        return tables;
    }
    
    public Boolean setStatus(Connection db, String status, String id){
        boolean condition = false;
        try{
            String query = "UPDATE tables SET status = ? WHERE table_id = ?";
            PreparedStatement st = db.prepareStatement(query);
            
            st.setString(1, status);
            st.setString(2, id);
            
            int result = st.executeUpdate();
            if(result > 0){
                condition = true;
            }
        }catch(SQLException err){
            condition = false;
        }
        return condition;
    }
    
    public int countReservations(Connection db,int id){
        int total = 0;
        try {
            String query = "SELECT count(*) AS total FROM reservations WHERE reservations.table_id = ?";
            PreparedStatement st = db.prepareStatement(query);
            
            st.setString(1, String.valueOf(id));
            
            ResultSet result = st.executeQuery();
            
            while(result.next()){
                total = result.getInt("total");
            }
            
        } catch (SQLException e) {
            total = 0;
        }
        return total;
    }
}
