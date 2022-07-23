/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private long price;
    private int discount;
    private long priceDiscount;
    private User user;
    private boolean status;
    private long quantity;
    private boolean featured;
    private String thumbnail;
    private ArrayList<Image> image = new ArrayList<>();
    private Date date;
    private SubCategory subCategory;
    private String categoryname;
    

    public long getPriceDiscount() {
        return price - ((price * discount) / 100);
    }

    public void setPriceDiscount(long priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public long getUnitPrice() {
        return (price - ((price * discount) / 100)) * quantity;
    }

    public void setUnitPrice(long priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", discount=" + discount + ", priceDiscount=" + priceDiscount + ", user=" + user + ", status=" + status + ", quantity=" + quantity + ", featured=" + featured + ", thumbnail=" + thumbnail + ", image=" + image.get(0).getImage() + ", date=" + date + ", subCategory=" + subCategory + '}';
    }

}
