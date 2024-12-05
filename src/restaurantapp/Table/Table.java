/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantapp.Table;

/**
 *
 * @author LENOVO
 */
public class Table {
    private int id;
    private String status;
    private int capacity;
    private String err;
      
    
    public Table(){
        
    }
    
    public Table(String err){
        this.err = err;
    }
    
    public Table(int id, String status, int capacity){
        this.id = id;
        this.status = status;
        this.capacity = capacity;
    }
    
//    Setter and Getter
    public void setId(String id){
        this.id = Integer.parseInt(id);
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    
    public void setErr(String err){
        this.err = err;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getCapacity(){
        return this.capacity;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    
    @Override
    public String toString(){
        return "Id: " + this.id + "\nCapacity: "+ this.capacity +"\nStatus: "+ this.status +"\nError: " + err + "\n";
    }
}
