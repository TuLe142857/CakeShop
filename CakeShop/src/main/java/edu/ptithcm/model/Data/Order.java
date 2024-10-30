package edu.ptithcm.model.Data;

import edu.ptithcm.model.MySql;

import java.time.LocalDateTime;
import java.sql.*;

public class Order{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from Orders");
        )
        {
            while (r.next()){
                System.out.println(new Order(r));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static enum ORDER_STATUS{
        PENDING("Pending"),
        CONFIRMED("Confirmed"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled");

        private String value;
        ORDER_STATUS(String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static ORDER_STATUS fromString(String status) {
            for (ORDER_STATUS s : ORDER_STATUS.values()) {
                if (s.value.compareTo(status) == 0) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Giá trị ORDER_STATUS không hợp lệ: " + status);
        }
    };

    public static enum PAYMENT_METHOD{
        CREDIT_CARD("Credit Card"),
        BANK_TRANSFER("Bank Transfer"),
        CASH("Cash"),
        PAYPAL("PayPal");

        private String value;
        PAYMENT_METHOD(String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static PAYMENT_METHOD fromString(String status) {
            for (PAYMENT_METHOD s : PAYMENT_METHOD.values()) {
                if (s.value.compareTo(status) == 0) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Giá trị PAYMENT_METHOD không hợp lệ: " + status);
        }
    }

    public static enum PAYMENT_STATUS{
        UNPAID("Unpaid"),
        PAID("Paid"),
        REFUNDED("Refunded")
        ;
        private String value;
        PAYMENT_STATUS(String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static PAYMENT_STATUS fromString(String method) {
            for (PAYMENT_STATUS s : PAYMENT_STATUS.values()) {
                if (s.value.compareTo(method) == 0) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Giá trị PAYMENT_STATUS không hợp lệ: " + method);
        }
    }

    private int id;
    private int userID;
    private double totalAmount;
    private ORDER_STATUS orderStatus;
    private PAYMENT_METHOD paymentMethod;
    private PAYMENT_STATUS paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(ResultSet r) throws  SQLException{
        this.id = r.getInt("id");
        this.userID = r.getInt("user_id");
        this.totalAmount = r.getDouble("total_amount");
        this.orderStatus = ORDER_STATUS.fromString(r.getString("status"));
        this.paymentMethod = PAYMENT_METHOD.fromString(r.getString("payment_method"));
        this.paymentStatus = PAYMENT_STATUS.fromString(r.getString("payment_status"));
        this.createdAt = r.getTimestamp("created_at").toLocalDateTime();
        this.updatedAt = r.getTimestamp("updated_at").toLocalDateTime();
    }

    public Order(int id, int userID, double totalAmount, ORDER_STATUS orderStatus, PAYMENT_METHOD paymentMethod, PAYMENT_STATUS paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ORDER_STATUS getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ORDER_STATUS orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PAYMENT_METHOD getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PAYMENT_METHOD paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PAYMENT_STATUS getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PAYMENT_STATUS paymentStatus) {
        this.paymentStatus = paymentStatus;
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
        return "Order{" +
                "id=" + id +
                ", userID=" + userID +
                ", totalAmount=" + totalAmount +
                ", orderStatus=" + orderStatus +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}