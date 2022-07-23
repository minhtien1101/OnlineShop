/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.LinkedHashMap;

/**
 *
 * @author Admin
 *
 */
public class Role {

    private int id;
    private String name;
    private boolean active;
    private LinkedHashMap<Feature, Boolean> allowFeatures = null;
    private boolean issuperadmin;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(int id) {
        this.id = id;
    }

    public Role() {
    }

    public boolean isIssuperadmin() {
        return issuperadmin;
    }

    public void setIssuperadmin(boolean issuperadmin) {
        this.issuperadmin = issuperadmin;
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

    public boolean isActive() {
        return active;
    }

    public void setStatus(boolean active) {
        this.active = active;
    }

    public LinkedHashMap<Feature, Boolean> getAllowFeatures() {
        return allowFeatures;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAllowFeatures(LinkedHashMap<Feature, Boolean> allowFeatures) {
        this.allowFeatures = allowFeatures;
    }
}
