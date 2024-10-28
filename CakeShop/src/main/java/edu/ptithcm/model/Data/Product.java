package edu.ptithcm.model.Data;

import edu.ptithcm.model.MySql;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author Le Ngoc Tu
 */
public class Product{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from products");
                )
        {
            while(r.next()){
                Product p = new Product(r);
                System.out.println(p.toString());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private int id;
    private int categoryID;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int discount;
    private String image_url;
    private boolean inBussiness;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(ResultSet r) throws SQLException {
        this.id = r.getInt("id");
        this.categoryID = r.getInt("category_id");
        this.name = r.getString("name");
        this.description = r.getString("description");
        this.price = r.getDouble("price");
        this.quantity = r.getInt("quantity");
        this.discount = r.getInt("discount");
        this.image_url = r.getString("image_url");
        this.inBussiness = r.getBoolean("status");
        this.createdAt = r.getTimestamp("created_at").toLocalDateTime();
        this.updatedAt = r.getTimestamp("updated_at").toLocalDateTime();
    }

    public Product(int id, int categoryID, String name,
                   String description, double price, int quantity,
                   int discount, String image_url,
                   boolean inBussiness, LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.image_url = image_url;
        this.inBussiness = inBussiness;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isInBussiness() {
        return inBussiness;
    }

    public void setInBussiness(boolean inBussiness) {
        this.inBussiness = inBussiness;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryID=" + categoryID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", image_url='" + image_url + '\'' +
                ", inBussiness=" + inBussiness +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}