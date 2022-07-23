/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;
import model.SubCategory;

/**
 *
 * @author Hoang Quang
 */
public class ProductCategoryDBContext extends DBContext {

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> listCategory = new ArrayList<>();
        try {
            String sql = "Select id, name from Category";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                ArrayList<SubCategory> listSubCategory = getListSubPostById(rs.getInt(1));
                category.setListSubCategory(listSubCategory);
                listCategory.add(category);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProductCategoryDBContext.class.getName()).log(Level.SEVERE, null, e);
        }
        return listCategory;
    }

    public ArrayList<SubCategory> getListSubPostById(int idCategory) {
        ArrayList<SubCategory> listSubCategory = new ArrayList<>();
        try {
            String sql = "Select * from SubCategory Where categoryId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, idCategory);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt(1));
                subCategory.setName(rs.getString(2));
                listSubCategory.add(subCategory);
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, e);
        }
        return listSubCategory;
    }

}
