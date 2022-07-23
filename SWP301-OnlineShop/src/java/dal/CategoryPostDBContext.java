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
import model.CategoryPost;
import model.SubCategoryPost;

/**
 *
 * @author DELL
 */
public class CategoryPostDBContext extends DBContext {

    public ArrayList<CategoryPost> getAllCategoryPost() {
        ArrayList<CategoryPost> listCategory = new ArrayList<>();
        try {
            String sql = "Select * from CategoryPost";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryPost category = new CategoryPost();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                ArrayList<SubCategoryPost> listSubCategory = getListSubCategoryById(rs.getInt(1));
                category.setListSubPost(listSubCategory);
                listCategory.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryPostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCategory;
    }

    public ArrayList<SubCategoryPost> getListSubCategoryById(int idCategory) {
        ArrayList<SubCategoryPost> listSubCategoryPost = new ArrayList<>();
        try {
            String sql = "Select * from SubCategoryPost \n"
                    + "Where idcategory = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, idCategory);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryPost subCategory = new SubCategoryPost();
                subCategory.setId(rs.getInt(1));
                subCategory.setName(rs.getString(2));
                CategoryPost category = new CategoryPost();
                category.setId(rs.getInt(3));
                subCategory.setCategory(category);
                listSubCategoryPost.add(subCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryPostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSubCategoryPost;
    }

    public void addSubCategory(int idCategory, String nameSubCategory) {
        try {
            String sql = "INSERT INTO [dbo].[SubCategoryPost]\n"
                    + "           ([name]\n"
                    + "           ,[idcategory])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, nameSubCategory);
            stm.setInt(2, idCategory);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryPostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CategoryPost addCategoryPost(String nameCategory, String nameSubCategory) {
        try {
            String sql = "INSERT INTO [dbo].[CategoryPost]\n"
                    + "           ([name])\n"
                    + "     VALUES\n"
                    + "           (?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, nameCategory);
            stm.executeUpdate();
            String sqlSelectIdCategory = "SELECT @@IDENTITY AS id";
            PreparedStatement stm2 = connection.prepareStatement(sqlSelectIdCategory);
            ResultSet rs = stm2.executeQuery();
            int idCategory = 0;
            if(rs.next()) {
                idCategory = rs.getInt(1);
            }
            String sql3 = "INSERT INTO [dbo].[SubCategoryPost]\n"
                    + "           ([name]\n"
                    + "           ,[idcategory])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setString(1, nameSubCategory);
            stm3.setInt(2, idCategory);
            stm3.executeUpdate();
            CategoryPost categoryPost = new CategoryPost();
            categoryPost.setId(idCategory);
            categoryPost.setName(nameCategory);
            return categoryPost;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryPostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
