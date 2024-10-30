package edu.ptithcm.model.Data;

import edu.ptithcm.model.MySql;

import java.sql.*;

public class OrderItem{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from OrderItems");
        )
        {
            while (r.next()){
                System.out.println(new OrderItem(r));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private int orderID;
    private int productID;
    private int quantity;

    /**
     * Gia cua 1 san pham
     */
    private double productPrice;

    public OrderItem(ResultSet r) throws  SQLException{
        this.orderID = r.getInt("order_id");
        this.productID = r.getInt("product_id");
        this.quantity = r.getInt("quantity");
        this.productPrice = r.getDouble("price");
    }

    public OrderItem(int orderID, int productID, int quantity, double productPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return Gia 1 san phan
     */
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return (price)*(quantity)
     */
    public double getTotalPrice(){
        return ((double) quantity) * productPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderID=" + orderID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                '}';
    }
}