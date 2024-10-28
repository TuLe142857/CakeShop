package edu.ptithcm.model.Data;

import java.time.LocalDateTime;

public class Product{
    private int id;
    private int categoryID;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int discount;
    private String image_url;
    private boolean inBussiness;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Product(int id, int categoryID, String name,
                   String description, double price, int quantity,
                   int discount, String image_url,
                   boolean inBussiness, LocalDateTime createAt,
                   LocalDateTime updateAt) {
        this.id = id;
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.image_url = image_url;
        this.inBussiness = inBussiness;
        this.createAt = createAt;
        this.updateAt = updateAt;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
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
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                '}';
    }
}