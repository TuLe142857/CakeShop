package edu.ptithcm.model.Data;

import edu.ptithcm.model.MySql;

import java.sql.*;

/**
 * @author Le Ngoc Tu
 */
public class Category{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from Categories");
                )
        {
            while (r.next()){
                Category c = new Category(r);
                System.out.println(c.toString());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private int id;
    private String name;
    private String description;

    public Category(ResultSet r) throws SQLException{
        this.id = r.getInt("id");
        this.name = r.getString("name");
        this.description = r.getString("description");
    }
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}