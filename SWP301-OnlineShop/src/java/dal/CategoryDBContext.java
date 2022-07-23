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
import model.SubCategory;

/**
 *
 * @author Admin
 */
public class CategoryDBContext extends DBContext {

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> categorys = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "  FROM [dbo].[Category]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                categorys.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorys;
    }

    public ArrayList<SubCategory> getSubCatgory(int category) {
        ArrayList<SubCategory> subCategorys = new ArrayList<>();

        try {
            String sql = "SELECT [SubCategory].[id]\n"
                    + "      ,[SubCategory].[name]\n"
                    + "      ,[categoryId]\n"
                    + "	  ,[Category].[name]\n"
                    + "  FROM [dbo].[SubCategory] join Category on Category.id = SubCategory.categoryId\n"
                    + "  WHERE Category.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt(1));
                subCategory.setName(rs.getString(2));
                Category c = new Category();
                c.setId(category);
                c.setName(rs.getString(4));
                subCategory.setCategory(c);
                subCategorys.add(subCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subCategorys;
    }

    

    public Category addCategory(String name) {
        Category category = new Category();
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[Category]\n"
                    + "           ([name])\n"
                    + "     VALUES\n"
                    + "           (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();

            int id = 0;
            String sql1 = "Select @@IDENTITY as CategoryId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            category.setId(id);
            category.setName(name);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return category;
    }

    public SubCategory addSubcategory(int categoryId, String subcategory) {
        SubCategory subCategory = new SubCategory();
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[SubCategory]\n"
                    + "           ([name]\n"
                    + "           ,[categoryId])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, subcategory);
            ps.setInt(2, categoryId);
            ps.executeUpdate();

            int id = 0;
            String sql1 = "Select @@IDENTITY as CategoryId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            subCategory.setId(id);
            subCategory.setName(subcategory);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return subCategory;
    }
    
    public static void main(String[] args) {
        CategoryDBContext bContext = new CategoryDBContext();
        bContext.addSubcategory(1, "Hat");
    }
   

}
