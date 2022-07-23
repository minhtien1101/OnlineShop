/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configs;

import model.Product;

/**
 *
 * @author Admin
 */
public class KeyValuePair {

    private Product key;
    private int value;

    public KeyValuePair(Product key, int value) {
        this.key = key;
        this.value = value;
    }

    public Product getKey() {
        return key;
    }

    public void setKey(Product key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    

   
}
