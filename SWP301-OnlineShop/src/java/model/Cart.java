/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Hoang Quang
 */
public class Cart {

    private int id;
    private User customer;
    private ArrayList<Cart_Product> cart_Products = new ArrayList<>();
    private Date dateUpdated;
    private int statusId;
    
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public ArrayList<Cart_Product> getCart_Products() {
        return cart_Products;
    }

    public void setCart_Products(ArrayList<Cart_Product> cart_Products) {
        this.cart_Products = cart_Products;
    }

   
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
     @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", customer=" + customer + ", cart_Products=" +'}';
    }

}

