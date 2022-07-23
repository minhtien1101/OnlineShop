/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class CategoryPost {

    private int id;
    private String name;
    ArrayList<SubCategoryPost> listSubPost;

    public CategoryPost() {
    }

    public int getId() {
        return id;
    }

    public ArrayList<SubCategoryPost> getListSubPost() {
        return listSubPost;
    }

    public void setListSubPost(ArrayList<SubCategoryPost> listSubPost) {
        this.listSubPost = listSubPost;
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

}
